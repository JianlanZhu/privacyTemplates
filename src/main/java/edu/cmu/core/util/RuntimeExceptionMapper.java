package edu.cmu.core.util;

import edu.cmu.resources.views.error.ErrorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    final static Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException exception) {

        Response response500 = Response
                .serverError()
                .entity(new ErrorView("500.mustache"))
                .build();

        // Assuming that any WebApplicationException thrown in application are deliberate and already logged
        if (exception instanceof WebApplicationException) {
            return handleWebApplicationException((WebApplicationException) exception, response500);
        }

        LOGGER.error("Uncaught exception in application", exception);

        return response500;
    }

    private Response handleWebApplicationException(WebApplicationException webAppException, Response response500) {

        int statusCode = webAppException.getResponse().getStatus();

        switch (statusCode) {
            case 401:
                return Response.seeOther(URI.create("/login")).build();
            case 403:
                return Response.status(statusCode).entity(new ErrorView("403.mustache")).build();
            case 404:
                return Response.status(statusCode).entity(new ErrorView("404.mustache")).build();
        }

        LOGGER.info("No template for WebApplicationException status " + statusCode);

        return response500;
    }
}
