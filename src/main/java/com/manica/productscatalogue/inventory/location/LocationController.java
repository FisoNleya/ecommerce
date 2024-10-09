package com.manica.productscatalogue.inventory.location;

import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> add(
            @RequestBody @Valid LocationRequest request,
            @RequestParam(required = false) Long vendorId,
            Principal principal
            ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(locationService.add(request, vendorId, user), HttpStatus.CREATED);
    }


    @GetMapping("/mine")
    public ResponseEntity<List<Location>> get(
            Principal principal
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(locationService.getMyLocations(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(example = "21") long id,
            Principal principal

    ) {

        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
