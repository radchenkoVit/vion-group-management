package com.vion.backend.web.contoller.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationDto {
    private String email;
    private String activationCodeLink;
}
