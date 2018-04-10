package edu.cmu.db.dao;

import com.google.common.io.Files;
import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

public class Parser {
    private static final String MESSAGE_PATH = "src/main/resources/SomeonesData";
    private static final String HTML = "html";
    private Random conversationIDGen = new Random();
    private Random messageIDGen = new Random();
    private SessionFactory sessionFactory; // just for testing
    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;

    /**
     * parseProfile is used to parse message files under a certain directory.
     *
     * @param path Directory where messages files are.
     */
    public void parseProfile(String path, SessionFactory sessionFactory, ConversationDAO conversationDAO,
                             MessageDAO messageDAO) {
        this.conversationDAO = conversationDAO;
        this.messageDAO = messageDAO;
        this.sessionFactory = sessionFactory;
        parseMessageFiles(MESSAGE_PATH); // should take path here
    }

    /**
     * parseMessageFiles is used to parse all message files under message directory.
     *
     * @param path Directory where messages files are.
     */
    private void parseMessageFiles(String path) {
        File directory = new File(path);
        File[] messageFiles = directory.listFiles();
        if (messageFiles != null) {
            for (File file : messageFiles) {
                int conversationID = conversationIDGen.nextInt();
                while (conversationDAO.findById(conversationID) != null) {
                    conversationID++;
                }
                System.out.println("*****************************" + file.getName() + "******************************");
                // parse each message file
                parseOneMessageFile(file, conversationID);
            }
        }
    }

    /**
     * parseOneMessageFile is used to parse a single message html file.
     *
     * @param file
     */
    private void parseOneMessageFile(File file, int conversationID) {
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
            Elements conversation = doc.body().select("div.thread");
            Elements currentLevelTags = conversation.first().children();
            // message class stored to db
            Message thisMessage = new Message();
            // elements
            String sender = null; // message sender
            Timestamp sentTime = null; // message sent time
            String content = null; // message content
            int count = 0; // count the number of valid tags


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
                    // set one messageID for the message
                    long messageID = messageIDGen.nextLong();
                    while (messageDAO.findById(messageID) != null) {
                        messageID++;
                    }
                    // update message
                    thisMessage.setMessageSender(sender);
                    thisMessage.setStartingTime(sentTime);
                    thisMessage.setConversationID(conversationID);
                    thisMessage.setMessageID(messageID);
                    //change states
                    count = 1;
                } else if (e.tag().getName().equals("p")) {
                    // get message content
                    if (e.hasText()) {
                        content = e.text();
                        thisMessage.setMessageContent(content);
                        // change state
                        count = 0;

                        // print the result for checking
                        System.out.println(thisMessage.getMessageSender());
                        System.out.println(thisMessage.getStartingTime());
                        System.out.println(thisMessage.getMessageContent() + "\n");
                        // TODO: store the new record to db

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
     * getConvertedTime is used to convert time in message file to the form that can be
     * converted to Timestamp class in Java.
     *
     * @param originalTime time represented in message file
     * @return String time representation that can be converted to timestamp in Java
     */
    private String getConvertedTime(String originalTime) {
        // original time: Monday, 21 December 2015 at 16:37 EST
        // converted to time like: 1985-04-12T23:20:50.52
        String[] weekDay = originalTime.split(", ");
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

    public static void main(String[] args) {
        // for testing
        String path = "src/main/resources/SomeonesData/messages/297.html";
        String directory = "src/main/resources/SomeonesData/messages";
        Parser parser = new Parser();
        /*
        File file = new File(path);
        // test parseOneMessageFile
        parseOneMessageFile(file);
        */
        parser.parseMessageFiles(directory);
    }
}
