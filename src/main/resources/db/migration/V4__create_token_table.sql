CREATE TABLE token (
  tokenId    INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (tokenId),
  userId     INT,
  FOREIGN KEY (userId) REFERENCES user (userId),
  token      VARCHAR(300) UNIQUE,
  validUntil TIMESTAMP
);