CREATE TABLE image (
  imageID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (imageID),
  rawImage BLOB,
  uploadDate DATETIME,
  imageSender VARCHAR(256),
  imageSize INT,
  resultID       INT,
  FOREIGN KEY (resultID) REFERENCES result (resultID)
);