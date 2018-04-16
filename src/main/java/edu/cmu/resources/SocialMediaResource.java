package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.User;
import edu.cmu.resources.interaction.DataUploadInput;
import edu.cmu.resources.views.UnansweredRequestsView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/socialMedia")
@RolesAllowed("SOCIAL_MEDIA_EMPLOYEE")
public class SocialMediaResource {

    private RequestDAO requestDAO;

    public SocialMediaResource(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    @GET
    @Path("/unansweredRequests")
    public View listAllUnansweredRequests() {
        return new UnansweredRequestsView();
    }

    @POST
    @Path("DataUploadForm")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("SOCIAL_MEDIA_EMPLOYEE")
    @UnitOfWork
    @Timed
    public void uploadData(@Auth User user,
                           @FormDataParam("data") final FormDataBodyPart fileField,
                           @FormDataParam("information") FormDataBodyPart generateRequestInput) {

        generateRequestInput.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        DataUploadInput parsedInput = generateRequestInput.getValueAs(DataUploadInput.class);

        Optional<Request> requestOptional = requestDAO.findById(parsedInput.getRequestId());
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            if (request.getStatus() == null || request.getStatus().equals("FILED")) {
                boolean success = requestDAO.updateStatus(request.getRequestID(), "ANSWERED");
                if (!success) {
                    throw new NotFoundException();
                }
                // TODO handle uploaded data
            } else {
                throw new BadRequestException("Request has already been answered or rejected.");
            }
        } else {
            throw new BadRequestException("Request ID invalid");
        }

    }

}
