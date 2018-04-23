package edu.cmu.resources;

import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.RequestDAO;
import org.junit.Before;
import org.junit.Test;

public class RequestResourceTest {

    private RequestResource requestResource;
    private RequestDAO requestDAO;
    private ConversationDAO conversationDAO;


    @Before
    public void setup() {
        requestResource = new RequestResource(requestDAO);
    }

    @Test
    public void generateRequest() {

    }

    @Test
    public void showGenerateRequestView() {
    }

    @Test
    public void getRequestDetails() {
    }

    @Test
    public void getfilteredConversationInfo() {
    }
}