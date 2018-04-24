package edu.cmu.resources;

import com.google.common.collect.Lists;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Conversation;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.Result;
import edu.cmu.db.entities.User;
import edu.cmu.resources.interaction.GenerateRequestInput;
import edu.cmu.resources.views.NotAnsweredView;
import edu.cmu.resources.views.RequestDetailsView;
import io.dropwizard.views.View;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class RequestResourceTest {

    private RequestResource requestResource;
    private RequestDAO requestDAO;


    @Before
    public void setup() {
        requestDAO = mock(RequestDAO.class);
        requestResource = new RequestResource(requestDAO);
    }

    @Test
    public void generateRequest() {
        User user = new User();
        GenerateRequestInput generateRequestInput = new GenerateRequestInput(3, "someSuspect", "FELONY", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "04/01/2018", "04/30/2018");

        requestResource.generateRequest(user, generateRequestInput);

        verify(requestDAO).persistNewRequest(any());
    }

    @Test
    public void generateRequestWithInvalidCaseId() {
        User user = new User();
        GenerateRequestInput generateRequestInput = new GenerateRequestInput(-1, "someSuspect", "FELONY", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        Throwable thrown = catchThrowable(() -> requestResource.generateRequest(user, generateRequestInput));

        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).contains("Invalid case ID.");
    }

    @Test
    public void generateRequestWithInvalidCaseType() {
        User user = new User();
        GenerateRequestInput generateRequestInput = new GenerateRequestInput(3, "someSuspect", "invalidtype", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        Throwable thrown = catchThrowable(() -> requestResource.generateRequest(user, generateRequestInput));

        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).contains("Invalid case Type.");
    }

    @Test
    public void generateRequestWithoutSufficientSuspectInformation() {
        User user = new User();
        GenerateRequestInput generateRequestInput = new GenerateRequestInput(3, null, "FELONY", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        Throwable thrown = catchThrowable(() -> requestResource.generateRequest(user, generateRequestInput));

        assertThat(thrown).isInstanceOf(BadRequestException.class);
        assertThat(thrown.getMessage()).contains("at least one of");
    }

    @Test
    public void getRequestDetailsForUnansweredRequest() {
        final int unansweredId = 5;
        final Request unansweredRequest = new Request();
        unansweredRequest.setResult(null);
        when(requestDAO.findById(unansweredId)).thenReturn(Optional.of(unansweredRequest));

        View view = requestResource.getRequestDetails(unansweredId, null);

        assertThat(view).isInstanceOf(NotAnsweredView.class);
    }

    @Test
    public void getRequestDetailsInvalidId() {
        final int requestId = 5;

        when(requestDAO.findById(requestId)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> requestResource.getRequestDetails(requestId, null));

        assertThat(thrown).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getRequestDetailsForAnsweredRequest() {
        final int requestId = 5;
        final Result result = new Result();
        final Request request = new Request();
        request.setResult(result);

        when(requestDAO.findById(requestId)).thenReturn(Optional.of(request));

        View view = requestResource.getRequestDetails(requestId, null);

        assertThat(view).isInstanceOf(RequestDetailsView.class);
    }

    @Test
    public void getRequestDetailsForAnsweredRequestWithSenderFilter() {
        final int requestId = 5;
        final Result result = new Result();
        final Request request = new Request();
        request.setResult(result);

        Conversation conversation1 = new Conversation();
        conversation1.setParticipants("User1, User2");

        Conversation conversation2 = new Conversation();
        conversation2.setParticipants("User1, User3");

        List<Conversation> conversations = Lists.newArrayList(conversation1, conversation2);
        result.setConversations(conversations);

        when(requestDAO.findById(requestId)).thenReturn(Optional.of(request));

        View view = requestResource.getRequestDetails(requestId, "User2");

        assertThat(view).isInstanceOf(RequestDetailsView.class);
        assertThat(((RequestDetailsView) view).getConversations()).contains(conversation1);
        assertThat(((RequestDetailsView) view).getConversations()).doesNotContain(conversation2);
    }
}