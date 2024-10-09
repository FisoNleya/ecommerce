package com.manica.productscatalogue.subscriptions.user;

import com.manica.productscatalogue.auth.dtos.ValidUser;
import com.messaging.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @Operation(
            description = "Test endpoint to check users",
            summary = "This end point will be removed",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @PostMapping
    public ResponseEntity<User> add(
            Principal principal,
            @RequestBody @Valid UserDto request
    ) {

        return new ResponseEntity<>(userService.add(request), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<User>> get(
    ) {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }


}
