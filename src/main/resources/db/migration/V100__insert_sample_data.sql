INSERT INTO User (userType, userName, password, accountEnabled, salt) VALUES
  ("LAW_ENFORCEMENT_OFFICER", "SaSm", "32C6636677E3634D64DD5B73EB4D3AB78FD8AB4D19C2497176A2D6D752AF1C98", 1,
   "SamsSalt");
INSERT INTO User (userType, userName, password, accountEnabled, salt) VALUES
  ("LAW_ENFORCEMENT_OFFICER", "AlAn", "E3D2DFE19FD55BF5B7DC29C24D07CA295A5C6545A657C7D769358387E6DED845", 1,
   "AlansSalt");
INSERT INTO User (userType, userName, password, accountEnabled, salt) VALUES
  ("SOCIAL_MEDIA_EMPLOYEE", "HaHa", "43E12EE3BAF37F13892364B0D4F9623124F2BB937D997944FFFEABCF0D902E86", 1, "HalysSalt");

INSERT INTO Request (caseID, suspectUserName, caseType, createdByID, status, requestedDataStartDate, requestedDataEndDate)
VALUES (1, "Suspect 1", "MISDEMEANEOR", 2, "PENDING", "2013-10-1", "2017-12-20");
INSERT INTO Request (caseID, suspectUserName, caseType, createdByID, status)
VALUES (112, "Suspect 2", "FELONY", 2, "ANSWERED");
INSERT INTO Request (caseID, suspectUserName, caseType, createdByID, status)
VALUES (13, "Suspect 3", "FELONY", 1, "PENDING");

INSERT INTO retentiontype (retentionID, retentionDays) VALUES (1, 180);
INSERT INTO retentiontype (retentionID, retentionDays) VALUES (2, 360);