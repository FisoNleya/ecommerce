package com.manica.productscatalogue.wardrope;

import com.manica.productscatalogue.auth.dtos.AuthenticationUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api/v1/ward-rope")
@RequiredArgsConstructor
public class WardRopeController {

    private final WardRopeService wardRopeService;

//    @PutMapping("mine/variants")
//    public ResponseEntity<WardRope> add(
//            Principal principal,
//            @RequestBody @Valid WardRopeRequest request
//    ) {
//
//        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//        return new ResponseEntity<>(wardRopeService.addToMyWardRope(user, request), HttpStatus.OK);
//    }
//
//
//    @DeleteMapping("mine/variants")
//    public ResponseEntity<WardRope> remove(
//            Principal principal,
//            @RequestBody @Valid WardRopeRequest request
//    ) {
//
//        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//        return new ResponseEntity<>(wardRopeService.removeFromMyWardRope(user, request), HttpStatus.OK);
//    }


    @GetMapping("mine")
    public ResponseEntity<WardRope> getMyWardRope(
            Principal principal
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(wardRopeService.findMyWardRope(user), HttpStatus.OK);
    }



}
