package edu.cmu.resources;

import edu.cmu.db.dao.ConversationDAO;
import edu.cmu.db.dao.MessageDAO;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.dao.ResultDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Result;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.RequestState;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
        final int id = 1;
        User user = mock(User.class);

        Request request = new Request();
        request.setRequestID(id);
        request.setStatus(RequestState.PENDING.name());
        when(requestDAO.findById(1)).thenReturn(Optional.of(request));

        FormDataBodyPart comment = mock(FormDataBodyPart.class);

        FormDataBodyPart fileField = mock(FormDataBodyPart.class);
        InputStream is = this.getClass().getResourceAsStream("/SomeonesData.zip");
        when(fileField.getValueAs(InputStream.class)).thenReturn(is);

        FormDataBodyPart requestId = mock(FormDataBodyPart.class);
        when(requestId.getValueAs(Integer.class)).thenReturn(id);

        Result result = new Result();
        result.setRequest(request);
        when(resultDAO.persistNewResult(any())).thenReturn(result);

        socialMediaResource.uploadData(user, fileField, requestId, comment);

        verify(resultDAO).persistNewResult(any());
        verify(requestDAO).updateStatus(id, RequestState.ANSWERED);
    }

    @Test
    public void uploadDataForAnsweredRequest() {
        final int id = 1;
        FormDataBodyPart comment = mock(FormDataBodyPart.class);

        User user = mock(User.class);

        Request request = new Request();
        request.setRequestID(id);
        request.setStatus(RequestState.ANSWERED.name());

        InputStream is = this.getClass().getResourceAsStream("/SomeonesData.zip");

        FormDataBodyPart fileField = mock(FormDataBodyPart.class);
        when(fileField.getValueAs(InputStream.class)).thenReturn(is);

        FormDataBodyPart requestId = mock(FormDataBodyPart.class);
        when(requestId.getValueAs(Integer.class)).thenReturn(id);

        when(requestDAO.findById(id)).thenReturn(Optional.of(request));

        Throwable thrown = catchThrowable(() -> socialMediaResource.uploadData(user, fileField, requestId, comment));

        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).contains("already been dealt");
    }

    @Test
    public void uploadDataForNonexistentRequest() {
        final int id = 3;
        FormDataBodyPart comment = mock(FormDataBodyPart.class);

        FormDataBodyPart fileField = mock(FormDataBodyPart.class);
        InputStream is = this.getClass().getResourceAsStream("/SomeonesData.zip");
        when(fileField.getValueAs(InputStream.class)).thenReturn(is);

        FormDataBodyPart requestId = mock(FormDataBodyPart.class);
        when(requestId.getValueAs(Integer.class)).thenReturn(id);

        when(requestDAO.findById(id)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> socialMediaResource.uploadData(null, fileField, requestId, comment));

        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).contains("ID invalid");
    }

    @Test
    public void uploadNonZipFile() {
        FormDataBodyPart fileField = mock(FormDataBodyPart.class);

        InputStream is = this.getClass().getResourceAsStream("/textFile.txt");

        when(fileField.getValueAs(InputStream.class)).thenReturn(is);

        Throwable thrown = catchThrowable(() -> socialMediaResource.uploadData(null, fileField, null, null));
        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).contains("not a zip");

        thrown = catchThrowable(() -> socialMediaResource.uploadData(null, null, null, null));
        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).contains("no data");
    }

    @Test
    public void getUploadForm() {
    }
}