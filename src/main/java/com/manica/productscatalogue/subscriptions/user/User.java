package com.manica.productscatalogue.subscriptions.user;

import com.messaging.FeatureDto;
import com.messaging.Role;
import com.messaging.UserRightDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Builder
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", unique = true)
    private String email;

    @Column(name = "perm_user_id")
    private Long userId;

    private String firstName;

    private String lastName;
    private String phoneNumber;
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    private List<UserRight> userRights = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Feature> features;


}
