package com.asapp.backend.challenge.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter @Setter
public class UserResource {

    private Integer id;

    private String username;

    private String password;
}
