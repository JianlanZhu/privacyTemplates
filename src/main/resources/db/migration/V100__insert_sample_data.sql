INSERT INTO User(userType, userName, password, accountEnabled) VALUES ("LAW_ENFORCEMENT_OFFICER", "SaSm", "SS", 1);
INSERT INTO User(userType, userName, password, accountEnabled) VALUES ("LAW_ENFORCEMENT_OFFICER", "AlAn", "AA", 1);
INSERT INTO User(userType, userName, password, accountEnabled) VALUES ("SOCIAL_MEDIA_EMPLOYEE", "HaHa", "HH", 1);

INSERT INTO Request(caseID, suspectUserName, caseType, createdByID) VALUES (1, "Suspect 1", "MISDEMEANEOR", 2);
INSERT INTO Request(caseID, suspectUserName, caseType, createdByID) VALUES (112, "Suspect 2", "FELONY", 2);
INSERT INTO Request(caseID, suspectUserName, caseType, createdByID) VALUES (13, "Suspect 3", "FELONY", 1);