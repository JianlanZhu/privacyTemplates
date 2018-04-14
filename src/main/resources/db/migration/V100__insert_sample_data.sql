INSERT INTO User(userType, userName, password, accountEnabled) VALUES ("LAW_ENFORCEMENT_OFFICER", "SaSm", "SS", 1);
INSERT INTO User(userType, userName, password, accountEnabled) VALUES ("SOCIAL_MEDIA_EMPLOYEE", "HaHa", "HH", 1);
# just for testing
# DELETE * FROM message;
# DELETE * FROM conversation;
# DELETE * FROM result;

INSERT INTO retentiontype(retentionID, retentionDays) VALUES (1, 30);
INSERT INTO Request(createdByID, caseID) VALUES (1, 1);