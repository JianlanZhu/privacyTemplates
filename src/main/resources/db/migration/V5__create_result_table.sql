CREATE TABLE result (
  resultID    INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (resultID),
  SMEUserID   INT,
  FOREIGN KEY (SMEUserID) REFERENCES user (userId),
  retentionID INT,
  FOREIGN KEY (retentionID) REFERENCES retentiontype (retentionID),
  requestID   INT
);