CREATE TABLE message (
  messageID      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (messageID),
  messageContent VARCHAR(4096) CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci,
  startingTime   DATETIME,
  messageSender  VARCHAR(256),
  conversationID INT,
  FOREIGN KEY (conversationID) REFERENCES conversation (conversationID)
);