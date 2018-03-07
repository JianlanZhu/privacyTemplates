CREATE TABLE userrequests (
  userID INT,
  FOREIGN KEY (userID) REFERENCES user(userId),
  requestID   INT,
  FOREIGN KEY (requestID) REFERENCES request(requestID)
);