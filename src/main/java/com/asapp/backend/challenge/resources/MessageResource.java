package com.asapp.backend.challenge.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder @Getter @Setter
public class MessageResource {

    private Integer id;

    private Integer sender;

    private Integer recipient;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date timestamp;

    private MessageContentResource content;
}
