package edu.cmu.core.util;

import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Message;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Result;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;


public class ParserTest {
    private ConversationDAO conversationDAO;
    private MessageDAO messageDAO;
    private Result result;
    private InputStream inputStream;
    private Parser parser;
    private Request request;

    @Before
    public void setUp() {
        this.conversationDAO = mock(ConversationDAO.class);
        this.messageDAO = mock(MessageDAO.class);
        try {
            inputStream = new FileInputStream("src/test/resources/SomeonesData.zip");
        } catch (FileNotFoundException e) {
            System.out.println("Read zip file error");
            e.printStackTrace();
        }

        // create new request
        this.request = new Request();
        request.setRequestID(1);
        // create result
        this.result = new Result();
        result.setRetentionID(1);
        result.setRequest(request);
        request.setResult(result);
        // parser
        this.parser = new Parser(conversationDAO, messageDAO, result);

        when(conversationDAO.persistNewConversation(any(Conversation.class))).thenAnswer(new Answer<Conversation>() {
            @Override
            public Conversation answer(InvocationOnMock invocationOnMock) throws Throwable {
                Conversation conversation = (Conversation) invocationOnMock.getArguments()[0];
                conversation.setResult(result);
                return conversation;
            }
        });
    }

    @Test
    public void parseProfile() {
        try {
            // unzip and parse
            parser.parseProfile(inputStream);
        } catch (IOException e) {
            // if parse failed
            e.printStackTrace();
        }
        int count = 0;
        boolean messageExist = false;
        for (Conversation c : result.getConversations()) {
            if (c.getParticipants().equals("Javi Cicleta")) {
                for (Message m : c.getMessages()) {
                    if (m.getMessageContent().contains("danke bob")) {
                        messageExist = true;
                    }
                }
            }
            if (c.getParticipants().contains("Yunus Calis")) {
                count++;
            }
        }
        assertThat(count).isEqualTo(4);
        assertThat(messageExist).isTrue();
    }
}