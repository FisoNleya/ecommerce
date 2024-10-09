package com.messaging;



import java.io.Serializable;
import java.time.LocalDateTime;


public record FeatureDto(Long featureId,
                         Boolean isEnabled,
                         SystemFeature systemFeature,
                         LocalDateTime createdAt)
        implements Serializable {
}