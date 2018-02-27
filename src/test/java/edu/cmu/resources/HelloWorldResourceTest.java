package edu.cmu.resources;


import com.google.common.base.Optional;
import edu.cmu.api.Saying;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldResourceTest {

    private HelloWorldResource helloWorldResource;

    @Before
    public void setUp() {
        helloWorldResource = new HelloWorldResource("Hello, %s!", "TestUser");
    }

    @Test
    public void sayHelloUsesDefaultIfNameAbsent() {
        Saying result = helloWorldResource.sayHello(Optional.absent());
        assertThat(result.getContent()).contains("TestUser");
    }

    @Test
    public void sayHelloUsesPassedName() {
        Saying result = helloWorldResource.sayHello(Optional.of("PresentUser"));
        assertThat(result.getContent()).contains("PresentUser");
    }

}