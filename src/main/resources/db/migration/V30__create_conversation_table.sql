CREATE TABLE conversation (
  conversationID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (conversationID),
  participants   VARCHAR(1024),
  resultID INT,
  FOREIGN KEY (resultID) REFERENCES result (resultID)
);