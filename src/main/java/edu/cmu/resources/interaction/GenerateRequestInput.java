package edu.cmu.resources.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class GenerateRequestInput {
    private long id;

    @Length(max = 3)
    private String content;

    public GenerateRequestInput() {
        // Jackson deserialization
    }

    public GenerateRequestInput(long id, String content) {
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

