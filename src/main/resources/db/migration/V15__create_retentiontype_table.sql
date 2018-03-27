CREATE TABLE retentiontype (
  retentionID          INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (retentionID),
  retentionDays        INT CHECK (
    retentionDays = 180 OR retentionDays = 360 or retentionDays = 720
  )
);