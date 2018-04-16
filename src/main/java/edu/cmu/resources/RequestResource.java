package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.io.ByteStreams;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.CaseType;
import edu.cmu.db.enums.RequestState;
import edu.cmu.resources.interaction.GenerateRequestInput;
import edu.cmu.resources.views.GenerateRequestView;
import edu.cmu.resources.views.ListAllRequestsForLeoView;
import edu.cmu.resources.views.ListAllRequestsForSmeView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * This class is used for registering endpoints regarding requests.
 */
@Path("/request")
public class RequestResource {

    /**
     * Responsible for accessing the database.
     */
    private RequestDAO requestDAO;

    public RequestResource(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    /**
     * Endpoint for creating a new request.
     *
     * @param generateRequestInput Parameters for the generated request
     * @param fileField            The warrant file
     * @return the generated request including generated fields like ID.
     */
    @POST
    @Path("/requestForm")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @UnitOfWork
    @Timed
    public Request generateRequest(@Auth User user,
                                   @FormDataParam("warrantFile") final FormDataBodyPart fileField,
                                   @FormDataParam("requestInformation") FormDataBodyPart generateRequestInput) {
        generateRequestInput.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        GenerateRequestInput parsedInput = generateRequestInput.getValueAs(GenerateRequestInput.class);

        checkInputValidity(parsedInput);

        Blob warrantBlob = null;
        if (fileField != null) {
            InputStream warrantFileInputStream = fileField.getValueAs(InputStream.class);

            try {
                byte[] warrantBytes = ByteStreams.toByteArray(warrantFileInputStream);
                warrantBlob = new SerialBlob(warrantBytes);
            } catch (IOException e) {
                throw new BadRequestException("File not readable.");
            } catch (SQLException e) {
                throw new InternalServerErrorException("Failed to handle uploaded warrant.");
            }
        }

        try {
            Request request = new Request(user.getUserID(), parsedInput.getCaseID(), parsedInput.getCaseType(), parsedInput.getSuspectUserName(), parsedInput.getLastName(), parsedInput.getFirstName(), parsedInput.getMiddleName(), parsedInput.getEmail(), parsedInput.getPhoneNumber(), parsedInput.getRequestedDataStartDate(), parsedInput.getRequestedDataEndDate(), parsedInput.isContactInformationRequested(), parsedInput.isMiniFeedRequested(), parsedInput.isStatusHistoryRequested(), parsedInput.isSharesRequested(), parsedInput.isNotesRequested(), parsedInput.isWallPostingsRequested(), parsedInput.isFriendListRequested(), parsedInput.isVideosRequested(), parsedInput.isGroupsRequested(), parsedInput.isPastEventsRequested(), parsedInput.isFutureEventsRequested(), parsedInput.isPhotosRequested(), parsedInput.isPrivateMessagesRequested(), parsedInput.isGroupInfoRequested(), parsedInput.isIPLogRequested(), null, null, parsedInput.getCommunicantsUserNames(), parsedInput.getKeywords(), parsedInput.getKeywordCategories(), parsedInput.getLocationZipCode(), warrantBlob);
            request = requestDAO.persistNewRequest(request);
            return request;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Malformed Request");
        }
    }

    /**
     * Respsonsible for backend mediation. Should check fo data formats etc.. NOT for business logic!
     *
     * @param input the incoming request.
     * @return true if data format is valid, false otherwise.
     */
    private static void checkInputValidity(GenerateRequestInput input) {
        if (input.getSuspectUserName() == null) {
            throw new BadRequestException("Invalid user name.");
        }

        if (input.getCaseID() <= 0) {
            // TODO maybe replace with actual check whether case is present in data base?
            throw new BadRequestException("Invalid case ID.");
        }

        try {
            // for now, case type can either be null, or oe of the values specified in the enum
            if (input.getCaseType() != null) {
                // will throw exception if case type is invalid
                CaseType.valueOf(input.getCaseType());
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid Case Type.");
        }

        //TODO additional checks
    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @Path("/requestForm")
    public View showGenerateRequestView() {
        return new GenerateRequestView();
    }

    @GET
    @RolesAllowed("LAW_ENFORCEMENT_OFFICER")
    @Path("/all")
    @UnitOfWork
    public View listAllRequestsForLeo(@Auth User user){
        return new ListAllRequestsForLeoView(requestDAO.findAllForUser(user.getUserID()));
    }
}

