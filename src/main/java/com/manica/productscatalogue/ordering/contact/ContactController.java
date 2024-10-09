package com.manica.productscatalogue.ordering.contact;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/delivery-contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    
    @PostMapping
    public ResponseEntity<Contact> add(
            Principal principal,
            @RequestBody @Valid ContactRequest request
    ) {

        return new ResponseEntity<>(contactService.add(request), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Contact>> get(
    ) {
        return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
    }
    
}
