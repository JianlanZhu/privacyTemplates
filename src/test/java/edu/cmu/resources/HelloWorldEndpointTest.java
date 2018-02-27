package edu.cmu.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.api.Saying;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldEndpointTest {

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HelloWorldResource("Hello, %s!", "Stranger"))
            .build();

    @Test
    public void helloWorldDropwizard() throws IOException {
        String expectedJson = "{\"id\":1,\"content\":\"Hello, dropwizard!\"}";
        Saying expectedSaying = new Saying(1, "Hello, dropwizard!");

        ObjectMapper objectMapper = new ObjectMapper();

        // Hit the endpoint and get the raw json string
        String resp = resources.client()
                .target("/hello-world")
                .queryParam("name", "dropwizard")
                .request()
                .get(String.class);

        assertThat(resp).isEqualTo(expectedJson);

        Saying parsedSaying = objectMapper.readValue(resp, Saying.class);
        assertThat(parsedSaying.getId())
                .isEqualTo(expectedSaying.getId())
                .isEqualTo(1);
        assertThat(parsedSaying.getContent())
                .isEqualTo(expectedSaying.getContent())
                .isEqualTo("Hello, dropwizard!");
    }

    @Test
    public void helloWorldAbsentName() {
        Saying parsedSaying = resources.client()
                .target("/hello-world")
                .request()
                .get(Saying.class);

        Saying expectedSaying = new Saying(1, "Hello, Stranger!");
        assertThat(parsedSaying.getId()).isEqualTo(expectedSaying.getId()).isEqualTo(1);
        assertThat(parsedSaying.getContent()).isEqualTo(expectedSaying.getContent()).isEqualTo("Hello, Stranger!");
    }

}