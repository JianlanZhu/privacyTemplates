CREATE TABLE message (
  messageID      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (messageID),
  messageContent VARCHAR(4096),
  startingTime   DATETIME,
  messageSender  VARCHAR(256),
  conversationID INT,
  FOREIGN KEY (conversationID) REFERENCES conversation (conversationID)
);