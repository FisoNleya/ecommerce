package com.manica.productscatalogue.subscriptions.user;

import com.messaging.SystemFeature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "feature")
@NoArgsConstructor
@AllArgsConstructor
public class Feature {

    @Id
    private Long featureId;
    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private SystemFeature systemFeature;

    private LocalDateTime createdAt;

}
