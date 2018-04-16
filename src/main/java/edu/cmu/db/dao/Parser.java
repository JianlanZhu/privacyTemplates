package edu.cmu.db.dao;

import com.google.common.io.Files;
import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Parser {
    private static final String HTML = "html";
    private static final int BUFFER_SIZE = 4096;
    private static final String DESTINATION_PATH = "src/main/resources/data";

    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;
    private String topDirectory;
    private int resultId;

    public Parser() {
    }

    public Parser(ConversationDAO conversationDAO, MessageDAO messageDAO, int resultId) {
        this.conversationDAO = conversationDAO;
        this.messageDAO = messageDAO;
        this.resultId = resultId;
    }

    /**
     * parseProfile is used to parse message files under a certain directory.
     *
     */
    public void parseProfile(InputStream inputStream) throws IOException {
        unzip(inputStream);
        parseMessageFiles(DESTINATION_PATH + "/" + topDirectory + "messages"); // should take path here
    }

    /**
     * parseMessageFiles is used to parse all message files under message directory.
     */
    private void parseMessageFiles(String path) {
        // store in conversation table
        File directory = new File(path);
        File[] messageFiles = directory.listFiles();
        if (messageFiles != null) {
            for (File file : messageFiles) {
                // find a conversation ID
                Conversation conversation = new Conversation();
                conversation.setResultID(resultId);
                conversation = conversationDAO.persistNewConversation(conversation);
                // parse each message file
                parseOneMessageFile(file, conversation);
            }
        }
    }

    /**
     * parseOneMessageFile is used to parse a single message html file.
     *
     * @param file file to parse
     * @param conversation which conversation the message belongs to
     */
    private void parseOneMessageFile(File file, Conversation conversation) {
        if (file != null && Files.getFileExtension(file.getName()).equals(HTML)) {
            // parse html file
            Document doc;
            try {
                doc = Jsoup.parse(file, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // get the main conversation part
            Elements conv = doc.body().select("div.thread");
            Elements currentLevelTags = conv.first().children();
            // message class stored to db
            Message thisMessage = new Message();
            // elements
            String sender; // message sender
            Timestamp sentTime; // message sent time
            String content; // message content
            String participants; // message participants
            // get participants
            String[] strs = conv.first().ownText().split(":");
            participants = strs[1].trim();
            conversation.setParticipants(participants);
            // update participants information
            conversation = conversationDAO.persistNewConversation(conversation);
            int count = 0; // count the number of valid tags

            // find all participants
            Set<String> par = new HashSet<>();
            for (Element e : currentLevelTags) {
                if (e.hasClass("message")) {
                    // new message record
                    thisMessage = new Message();
                    // get info from this message_header
                    Element messageHeader = e.select("div.message_header").first();
                    // get sender
                    sender = messageHeader.select("span.user").first().text();
                    // get message sent time
                    String originalTime = messageHeader.select("span.meta").first().text();
                    String convertedTime = getConvertedTime(originalTime);
                    sentTime = Timestamp.valueOf(convertedTime);
                    // update participants
                    if (!par.contains(sender)) {
                        par.add(sender);
                    }
                    thisMessage.setMessageSender(sender);
                    thisMessage.setStartingTime(sentTime);
                    thisMessage.setConversationID(conversation.getConversationID());
                    //change states
                    count = 1;
                } else if (e.tag().getName().equals("p")) {
                    // get message content
                    if (e.hasText()) {
                        content = e.text();
                        thisMessage.setMessageContent(content);
                        // change state
                        count = 0;
                        // store this message
                        try {
                            thisMessage = messageDAO.persistNewMessage(thisMessage);
                        } catch (Exception ee) {
                            System.out.println("Error long text: " + thisMessage.getMessageContent());
                            ee.printStackTrace();
                        }
                    } else if (count == 1) {
                        count++;
                    } else if (count == 2) {
                        // contain pictures
                        count++;
                        // TODO: store pictures, for right now just ignore it
                        continue;
                    } else {
                        count = 0;
                    }
                }
            }
        }
    }

    /**
     * unzip is used to unzip a zip file.
     *
     * @param in zip file input stream
     * @throws IOException io exception
     */
    private void unzip(InputStream in) throws IOException {
        boolean isTopLevel = true;
        File destDir = new File(DESTINATION_PATH);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(in);
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = DESTINATION_PATH + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                if (isTopLevel) {
                    topDirectory = entry.getName();
                    isTopLevel = false;
                }
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * extractFile is used to unzip a single file.
     *
     * @param zipIn input stream of zip file
     * @param filePath path of file needed to be unzipped
     * @throws IOException io exception
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    /**
     * getConvertedTime is used to convert time in message file to the form that can be
     * converted to Timestamp class in Java.
     *
     * @param originalTime time represented in message file
     * @return String time representation that can be converted to timestamp in Java
     */
    private String getConvertedTime(String originalTime) {
        // original time: Monday, 21 December 2015 at 16:37 EST
        // converted to time like: 1985-04-12T23:20:50.52
        String[] weekDay = originalTime.split(",");
        String[] timeElements = weekDay[1].trim().split(" ");
        String convertedTime = timeElements[2] + "-" + getMonth(timeElements[1]) + "-" + timeElements[0] +
                " " + timeElements[4] + ":00";
        return convertedTime;
    }

    /**
     * getMonth is used to convert month expression into integer.
     *
     * @param month month represented in string
     * @return int month represented in integer
     */
    private int getMonth(String month) {
        switch (month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
        }
    }

    public static String getDestinationPath() {
        return DESTINATION_PATH;
    }

    /**
     * deleteFileOrFolder is used to delte a file or an empty folder.
     * @param path file path
     * @throws IOException io exception
     */
    public static void deleteFileOrFolder(Path path) throws IOException {
        java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                    throws IOException {
                java.nio.file.Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                return handleException(e);
            }

            private FileVisitResult handleException(final IOException e) {
                e.printStackTrace(); // replace with more robust error handling
                return FileVisitResult.TERMINATE;
            }

            @Override public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                    throws IOException {
                if(e!=null)return handleException(e);
                java.nio.file.Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
