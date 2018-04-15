CREATE TABLE contenttyperequests (
  contentTypeID INT,
  FOREIGN KEY (contentTypeID) REFERENCES contenttype (contentTypeID),
  requestID     INT,
  FOREIGN KEY (requestID) REFERENCES request (requestId)

);