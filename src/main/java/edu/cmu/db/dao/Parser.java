package edu.cmu.db.dao;

import com.google.common.io.Files;
import edu.cmu.db.entities.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class Parser {
    private static final String MESSAGE_PATH = "src/main/resources/SomeonesData";
    private static final String HTML = "html";

    public static void parseProfile(String path) {
        parseMessageFiles(MESSAGE_PATH);
    }

    private static void parseMessageFiles(String path) {
        File directory = new File(path);
        File[] messageFiles = directory.listFiles();
        if (messageFiles != null) {
            for (File file : messageFiles) {
                parseOneMessageFile(file);
            }
        }
    }

    private static void parseOneMessageFile(File file) {
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
                    // update message
                    thisMessage.setMessageSender(sender);
                    thisMessage.setStartingTime(sentTime);
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
                        System.out.println(thisMessage.getMessageContent());
                        // TODO: store the new record to the cb

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

//            for (int i = 1; i <= conversation.size(); i++) {
//                // choose nth header
//                Element messageHeader = conversation.select("div.message:nth-child(" + (i + 1) + ")").first();
//                // get sender
//                String sender = messageHeader.select("span.user").first().text();
//                // get message sent time
//                String originalTime = messageHeader.select("span.meta").first().text();
//                String convertedTime = getConvertedTime(originalTime);
//                Timestamp sentTime = Timestamp.valueOf(convertedTime);
//                // choose nth message
//
//            }


        }
    }

    private static String getConvertedTime(String originalTime) {
        // original time: Monday, 21 December 2015 at 16:37 EST
        // converted to time like: 1985-04-12T23:20:50.52
        String[] weekDay = originalTime.split(", ");
        String[] timeElements = weekDay[1].trim().split(" ");
        String convertedTime = timeElements[2] + "-" + getMonth(timeElements[1]) + "-" + timeElements[0] +
                " " + timeElements[4] + ":00";
        return convertedTime;
    }

    private static int getMonth(String month) {
        switch (month) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        // for testing
        String path = "src/main/resources/SomeonesData/messages/297.html";
        File file = new File(path);
        // test parseOneMessageFile
        parseOneMessageFile(file);
    }
}
