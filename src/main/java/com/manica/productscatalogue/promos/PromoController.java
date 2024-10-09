package com.manica.productscatalogue.promos;

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
@RequestMapping("/api/v1/promo")
@RequiredArgsConstructor
public class PromoController {



    private final PromoService promoService;


    @PostMapping("/variant/{variantId}")
    public ResponseEntity<Promo> add(
            @PathVariable @Parameter(example = "21") long variantId,
            Principal principal,
            @RequestBody @Valid PromoRequest request
    ) {

        var user = (AuthenticationUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return new ResponseEntity<>(promoService.add(request, user, variantId), HttpStatus.CREATED);
    }


    @GetMapping("/all/variant/{id}")
    public ResponseEntity<List<Promo>> get(
            @PathVariable @Parameter(example = "21") long id
    ) {
        return new ResponseEntity<>(promoService.findVariantPromos(id), HttpStatus.OK);
    }



    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deletePromo(
            @PathVariable @Parameter(example = "21") long id
    ) {
        promoService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
