package com.soliduslabs.homework.Problem1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {

    @JsonProperty("message")
    private String message;
}
