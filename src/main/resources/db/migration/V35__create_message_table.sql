CREATE TABLE message (
  messageID      LONG NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (messageID),
  messageContent VARCHAR(256),
  startingTime   DATETIME,
  messageSender  VARCHAR(256),
  conversationID INT,
  FOREIGN KEY (conversationID) REFERENCES conversation (conversationID)
);