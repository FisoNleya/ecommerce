package com.messaging;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RegEmailDto(@NotEmpty(message = "First Name address is required")
                               String firstname,
                          @NotEmpty(message = "Last Name  is required")
                               String lastname,
                          @NotEmpty(message = "Email address is required")
                                String emailAddress,

                          String code){ }
