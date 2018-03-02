package edu.cmu.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.resources.interaction.SayingOutput;
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
        SayingOutput expectedSayingOutput = new SayingOutput(1, "Hello, dropwizard!");

        ObjectMapper objectMapper = new ObjectMapper();

        // Hit the endpoint and get the raw json string
        String resp = resources.client()
                .target("/hello-world")
                .queryParam("name", "dropwizard")
                .request()
                .get(String.class);

        assertThat(resp).isEqualTo(expectedJson);

        SayingOutput parsedSayingOutput = objectMapper.readValue(resp, SayingOutput.class);
        assertThat(parsedSayingOutput.getId())
                .isEqualTo(expectedSayingOutput.getId())
                .isEqualTo(1);
        assertThat(parsedSayingOutput.getContent())
                .isEqualTo(expectedSayingOutput.getContent())
                .isEqualTo("Hello, dropwizard!");
    }

    @Test
    public void helloWorldAbsentName() {
        SayingOutput parsedSayingOutput = resources.client()
                .target("/hello-world")
                .request()
                .get(SayingOutput.class);

        SayingOutput expectedSayingOutput = new SayingOutput(1, "Hello, Stranger!");
        assertThat(parsedSayingOutput.getId()).isEqualTo(expectedSayingOutput.getId()).isEqualTo(1);
        assertThat(parsedSayingOutput.getContent()).isEqualTo(expectedSayingOutput.getContent()).isEqualTo("Hello, Stranger!");
    }

}