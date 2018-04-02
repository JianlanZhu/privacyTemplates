CREATE TABLE video (
  videoID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (videoID),
  rawVideo LONGBLOB,
  uploadDate DATETIME,
  videoSender VARCHAR(256),
  videoSize INT,
  resultID       INT,
  FOREIGN KEY (resultID) REFERENCES result (resultID)
);