package edu.cmu.db.dao;

import com.google.common.io.Files;

import java.io.File;

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
            // get
        }
    }
}
