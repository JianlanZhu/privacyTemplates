package edu.cmu.core.util;

import com.google.common.io.Files;
import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This class is responsible for parsing a zip file containing a user's data as gathered by Facebook.
 */
public class Parser {
    private static final int BUFFER_SIZE = 4096;
    private static final String DESTINATION_PATH = "src/main/resources/data";
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);

    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;
    private String topDirectory;
    private Result result;
    private Request request;

    public Parser(ConversationDAO conversationDAO, MessageDAO messageDAO, Result result) {
        this.conversationDAO = conversationDAO;
        this.messageDAO = messageDAO;
        this.result = result;
        this.request = result.getRequest();
    }

    /**
     * parseProfile is currently only used to parse message files under a certain directory.
     * @param inputStream representing the zip file
     * @throws IOException if unzipping fails
     */
    public void parseProfile(InputStream inputStream) throws IOException {
        unzip(inputStream);
        parseMessageFiles(DESTINATION_PATH + "/" + topDirectory + "messages"); // should take path here
        // delete raw data
        try {
            deleteFileOrFolder(Paths.get(DESTINATION_PATH));
        } catch (IOException e) {
            LOG.warn("Error while deleting data.");
            e.printStackTrace();
        }
    }

    /**
     * Parses all message files under message directory.
     * @param path location of messages
     */
    private void parseMessageFiles(String path) {
        File directory = new File(path);
        File[] messageFiles = directory.listFiles();
        if (messageFiles != null) {
            for (File file : messageFiles) {
                Conversation conversation = new Conversation();
                conversation.setResult(result);
                // parse each message file
                parseOneConversationFile(file, conversation);

            }
        }
    }

    /**
     * Parses a single conversation html file. Dependent on the concrete Facebook format.
     *
     * @param file         file to parse
     * @param conversation which conversation the message belongs to
     */
    private void parseOneConversationFile(File file, Conversation conversation) {
        if (file != null && Files.getFileExtension(file.getName()).equals("html")) {
            // parse html file
            Document doc;
            try {
                doc = Jsoup.parse(file, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Elements conversationElement = doc.body().select("div.thread");
            Elements currentLevelTags = conversationElement.first().children();

            String participants = conversationElement.first().ownText().split(":")[1].trim();
            conversation.setParticipants(participants);

            if (participants.length() <= 0) {
                return;
            }
            conversation = conversationDAO.persistNewConversation(conversation);

            int count = 0; // count the number of valid tags

            Message currentMessage = new Message();
            for (Element e : currentLevelTags) {
                if (e.hasClass("message")) {
                    // new message record
                    currentMessage = new Message();
                    // get info from this message_header
                    Element messageHeader = e.select("div.message_header").first();
                    String sender = messageHeader.select("span.user").first().text();
                    // get message sent time
                    String originalTime = messageHeader.select("span.meta").first().text();
                    String convertedTime = getConvertedTime(originalTime);
                    Date sentTime = Date.valueOf(convertedTime);

                    currentMessage.setMessageSender(sender);
                    currentMessage.setStartingTime(sentTime);
                    currentMessage.setConversation(conversation);
                    //change states
                    count = 1;
                } else if (e.tag().getName().equals("p")) {
                    // get message content
                    if (e.hasText()) {
                        currentMessage.setMessageContent(e.text());
                        count = 0;
                        // Skip messages of they fall outside the requested time frame
                        Date startDate = request.getRequestedDataStartDate();
                        Date endDate = request.getRequestedDataEndDate();
                        if (startDate != null && currentMessage.getStartingTime().compareTo(startDate) < 0) {
                            continue;
                        }
                        if (endDate != null && currentMessage.getStartingTime().compareTo(endDate) > 0) {
                            continue;
                        }
                        try {
                            conversation.getMessages().add(currentMessage);
                            currentMessage = messageDAO.persistNewMessage(currentMessage);
                        } catch (Exception ee) {
                            LOG.warn("Error long text: " + currentMessage.getMessageContent());
                            ee.printStackTrace();
                        }
                    } else if (count == 1) {
                        count++;
                    } else if (count == 2) {
                        // contain pictures
                        count++;
                        // store pictures, for right now just ignore it
                        continue;
                    } else {
                        count = 0;
                    }
                }
            }

            conversation = conversationDAO.persistNewConversation(conversation);
            result.getConversations().add(conversation);
        } else {
            LOG.warn(String.format("Parsing failed: File %s is no HTML file.", file != null ? file.getName() : "null"));
        }
    }

    /**
     * Unzips a zip file. Sets the topDirectory variable.
     *
     * @param in zip file input stream
     * @throws IOException io exception
     */
    private void unzip(InputStream in) throws IOException {
        boolean isTopLevel = true;
        File destDir = new File(DESTINATION_PATH);
        if (!destDir.exists()) {
            if (!destDir.mkdir()) {
                throw new IOException("Could not create directory " + destDir.getName());
            }
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
                if (!dir.mkdir()) {
                    throw new IOException("Could not create directory " + dir.getName());
                }
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Unzips a single file.
     *
     * @param zipIn    input stream of zip file
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
        String[] timeElements = originalTime.split(",")[1].trim().split(" ");
        return timeElements[2] + "-" + getMonth(timeElements[1]) + "-" + timeElements[0];
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

    /**
     * Deletes a file or an empty folder.
     *
     * @param path file path
     * @throws IOException io exception
     */
    private void deleteFileOrFolder(Path path) throws IOException {
        java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                    throws IOException {
                java.nio.file.Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                return handleException(e);
            }

            private FileVisitResult handleException(final IOException e) {
                e.printStackTrace();
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                    throws IOException {
                if (e != null) return handleException(e);
                java.nio.file.Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
