package edu.cmu.resources;

import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.ResultDAO;
import edu.cmu.db.entities.User;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class SocialMediaResourceTest {

    private SocialMediaResource socialMediaResource;
    private RequestDAO requestDAO;
    private ResultDAO resultDAO;
    private MessageDAO messageDAO;
    private ConversationDAO conversationDAO;

    @Before
    public void setup() {
        requestDAO = mock(RequestDAO.class);
        resultDAO = mock(ResultDAO.class);
        messageDAO = mock(MessageDAO.class);
        conversationDAO = mock(ConversationDAO.class);
        socialMediaResource = new SocialMediaResource(requestDAO, resultDAO, conversationDAO, messageDAO);
    }

    @Test
    public void listAllUnansweredRequests() {
    }

    @Test
    public void uploadData() {
        User user = mock(User.class);
        FormDataBodyPart fileField = mock(FormDataBodyPart.class);
        FormDataBodyPart requestId = mock(FormDataBodyPart.class);
        FormDataBodyPart comment = mock(FormDataBodyPart.class);


        socialMediaResource.uploadData(user, fileField, requestId, comment);

    }

    @Test
    public void getUploadForm() {
    }
}