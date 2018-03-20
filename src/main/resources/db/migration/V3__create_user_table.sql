CREATE TABLE user (
  userId           INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (userId),
  userTypeId       INT,
  FOREIGN KEY (userTypeId) REFERENCES userType(userTypeId),
  lastName         VARCHAR(256),
  firstName        VARCHAR(256),
  userName         VARCHAR(256),
  password         VARCHAR(256),
  accountEnabled   BIT,
  unserCreatedDate TIMESTAMP
);