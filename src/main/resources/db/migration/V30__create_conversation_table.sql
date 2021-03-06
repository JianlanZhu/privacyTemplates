CREATE TABLE conversation (
  conversationID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (conversationID),
  startingTime   DATETIME,
  participants   VARCHAR(4096),
  resultID       INT,
  FOREIGN KEY (resultID) REFERENCES result (resultID)
);