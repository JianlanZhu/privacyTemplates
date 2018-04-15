CREATE TABLE user (
  userId          INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (userId),
  userType        VARCHAR(256) CHECK (
    userType = 'LAW_ENFORCEMENT_OFFICER' OR
    userType = 'SOCIAL_MEDIA_EMPLOYEE' OR
    userType = 'SYS_ADMIN' OR
    userType = 'DATA_ANALYST'),
  lastName        VARCHAR(256),
  firstName       VARCHAR(256),
  userName        VARCHAR(256) UNIQUE,
  password        VARCHAR(256),
  accountEnabled  BIT,
  userCreatedDate TIMESTAMP
);