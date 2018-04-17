package edu.cmu.resources;

import com.codahale.metrics.annotation.Timed;
import edu.cmu.db.dao.RequestDAO;
import edu.cmu.db.entities.Request;
import edu.cmu.db.entities.User;
import edu.cmu.db.enums.RequestState;
import edu.cmu.resources.views.ListAllRequestsForSmeView;
import edu.cmu.resources.views.UploadDataFormView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipInputStream;

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
    @UnitOfWork
    @Timed
    public void uploadData(@Auth User user,
                           @FormDataParam("data") final FormDataBodyPart fileField,
                           @FormDataParam("caseID") FormDataBodyPart requestId,
                           @FormDataParam("comment") FormDataBodyPart comment) {

        if (fileField != null) {
            InputStream warrantFileInputStream = new BufferedInputStream(fileField.getValueAs(InputStream.class));
            try {
                // getNextEntry returns null if the InputStream is not a zip file
                if (new ZipInputStream(warrantFileInputStream).getNextEntry() == null) {
                    throw new BadRequestException("Uploaded file was not a zip file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        requestId.setMediaType(MediaType.TEXT_PLAIN_TYPE);
        int requestIdNumber = Integer.parseInt(requestId.getValue());
        comment.setMediaType(MediaType.TEXT_PLAIN_TYPE);


        Optional<Request> requestOptional = requestDAO.findById(requestIdNumber);
        if (requestOptional.isPresent()) {
            Request request = requestOptional.get();
            if (request.getStatus() == null || request.getStatus().equals(RequestState.PENDING.name())) {
                boolean success = requestDAO.updateStatus(request.getRequestID(), RequestState.ANSWERED);
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

    @GET
    @Path("DataUploadForm")
    @UnitOfWork
    @Timed
    public UploadDataFormView getUploadForm() {
        return new UploadDataFormView();
    }

}
