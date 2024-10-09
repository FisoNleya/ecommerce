package com.manica.productscatalogue.ordering.order.notifications;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDto implements Serializable {

    private static final Long serialVersionUID = 343412L;

    @NotEmpty(message = "First Name address is required")
    private String firstname;

    @NotEmpty(message = "Last Name  is required")
    private String lastname;

    @NotEmpty(message = "Email address is required")
    private String emailAddress;

}
