CREATE TABLE requeststate (
  requestStateID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (requestStateID),
  requestStateName   VARCHAR(256),
  requestStateDescription VARCHAR(256)
);