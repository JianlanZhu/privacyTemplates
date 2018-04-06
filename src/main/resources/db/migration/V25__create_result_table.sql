CREATE TABLE result (
  resultID    INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (resultID),
  SMEUserID   INT,
  rawResult  BLOB,
  retentionID INT,
  FOREIGN KEY (retentionID) REFERENCES retentiontype (retentionID)
);