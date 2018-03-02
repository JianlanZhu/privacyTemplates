package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class SayingOutput {
    private long id;

    @Length(max = 3)
    private String content;

    public SayingOutput() {
        // Jackson deserialization
    }

    public SayingOutput(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}
