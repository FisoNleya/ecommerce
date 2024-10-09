
package com.manica.productscatalogue.delivery.courier.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.manica.productscatalogue.delivery.courier.request.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
    "id",
    "provider_id",
    "provider",
    "account_id",
    "account",
    "short_tracking_reference",
    "customer_reference",
    "latest_tracking_event_time",
    "time_created",
    "time_modified",
    "modified_by",
    "created_by",
    "deleted_date",
    "source",
    "collection_min_date",
    "collection_after",
    "collection_before",
    "delivery_min_date",
    "delivery_after",
    "delivery_before",
    "collection_request_id",
    "status",
    "delivery_agent_id",
    "collection_agent_id",
    "original_delivery_agent_id",
    "original_collection_agent_id",
    "collection_agent_zone",
    "collection_agent_routing_number",
    "delivery_agent_zone",
    "delivery_agent_routing_number",
    "delivery_read_by_driver_time",
    "collection_read_by_driver_time",
    "collection_address_id",
    "collection_address",
    "collection_address_book_id",
    "collection_contact_id",
    "collection_contact",
    "delivery_address_id",
    "delivery_address",
    "delivery_address_book_id",
    "delivery_contact_id",
    "delivery_contact",
    "responsible_contact_id",
    "parcels",
    "service_level_code",
    "service_level_name",
    "service_level_id",
    "rate",
    "original_rate",
    "previous_rate",
    "charged_weight",
    "actual_weight",
    "volumetric_weight",
    "declared_value",
    "rates",
    "special_instructions_collection",
    "special_instructions_delivery",
    "proof_of_delivery_pin_hashed",
    "collected_date",
    "original_collected_date",
    "delivered_date",
    "is_return",
    "estimated_collection",
    "original_estimated_collection",
    "estimated_delivery_from",
    "estimated_delivery_to",
    "original_estimated_delivery_from",
    "is_pending",
    "current_branch_id",
    "current_branch_name",
    "collection_branch_id",
    "collection_branch_name",
    "delivery_branch_id",
    "delivery_branch_name",
    "account_branch_id",
    "transient_branch_ids",
    "service_level_cut_off_time",
    "collection_cutoff_override",
    "all_charges_reversed",
    "total_note_count",
    "external_note_count",
    "disable_new_charges",
    "mute_notifications",
    "payment_intervention_status",
    "pod_method",
    "has_pod_files",
    "has_been_invoiced",
    "tracking_events",
    "collection_rescheduled",
    "delivery_rescheduled",
    "rating_reference",
    "has_rating"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipResponse {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("provider_id")
    public Integer providerId;
    @JsonProperty("provider")
    public Provider provider;
    @JsonProperty("account_id")
    public Integer accountId;
    @JsonProperty("account")
    public Account account;
    @JsonProperty("short_tracking_reference")
    public String shortTrackingReference;
    @JsonProperty("customer_reference")
    public String customerReference;
    @JsonProperty("latest_tracking_event_time")
    public String latestTrackingEventTime;
    @JsonProperty("time_created")
    public String timeCreated;
    @JsonProperty("time_modified")
    public String timeModified;
    @JsonProperty("modified_by")
    public String modifiedBy;
    @JsonProperty("created_by")
    public String createdBy;
    @JsonProperty("deleted_date")
    public String deletedDate;
    @JsonProperty("source")
    public String source;
    @JsonProperty("collection_min_date")
    public String collectionMinDate;
    @JsonProperty("collection_after")
    public String collectionAfter;
    @JsonProperty("collection_before")
    public String collectionBefore;
    @JsonProperty("delivery_min_date")
    public String deliveryMinDate;
    @JsonProperty("delivery_after")
    public String deliveryAfter;
    @JsonProperty("delivery_before")
    public String deliveryBefore;
    @JsonProperty("collection_request_id")
    public Integer collectionRequestId;
    @JsonProperty("status")
    public String status;
    @JsonProperty("delivery_agent_id")
    public String deliveryAgentId;
    @JsonProperty("collection_agent_id")
    public String collectionAgentId;
    @JsonProperty("original_delivery_agent_id")
    public String originalDeliveryAgentId;
    @JsonProperty("original_collection_agent_id")
    public String originalCollectionAgentId;
    @JsonProperty("collection_agent_zone")
    public String collectionAgentZone;
    @JsonProperty("collection_agent_routing_number")
    public String collectionAgentRoutingNumber;
    @JsonProperty("delivery_agent_zone")
    public String deliveryAgentZone;
    @JsonProperty("delivery_agent_routing_number")
    public String deliveryAgentRoutingNumber;
    @JsonProperty("delivery_read_by_driver_time")
    public Object deliveryReadByDriverTime;
    @JsonProperty("collection_read_by_driver_time")
    public Object collectionReadByDriverTime;
    @JsonProperty("collection_address_id")
    public Integer collectionAddressId;
    @JsonProperty("collection_address")
    public CollectionAddress collectionAddress;
    @JsonProperty("collection_address_book_id")
    public Integer collectionAddressBookId;
    @JsonProperty("collection_contact_id")
    public Integer collectionContactId;
    @JsonProperty("collection_contact")
    public CollectionContact collectionContact;
    @JsonProperty("delivery_address_id")
    public Integer deliveryAddressId;
    @JsonProperty("delivery_address")
    public DeliveryAddress deliveryAddress;
    @JsonProperty("delivery_address_book_id")
    public Integer deliveryAddressBookId;
    @JsonProperty("delivery_contact_id")
    public Integer deliveryContactId;
    @JsonProperty("delivery_contact")
    public DeliveryContact deliveryContact;
    @JsonProperty("responsible_contact_id")
    public Integer responsibleContactId;
    @JsonProperty("parcels")
    public List<Parcel> parcels;
    @JsonProperty("service_level_code")
    public String serviceLevelCode;
    @JsonProperty("service_level_name")
    public String serviceLevelName;
    @JsonProperty("service_level_id")
    public Integer serviceLevelId;
    @JsonProperty("rate")
    public Integer rate;
    @JsonProperty("original_rate")
    public Integer originalRate;
    @JsonProperty("previous_rate")
    public Integer previousRate;
    @JsonProperty("charged_weight")
    public Integer chargedWeight;
    @JsonProperty("actual_weight")
    public Integer actualWeight;
    @JsonProperty("volumetric_weight")
    public Integer volumetricWeight;
    @JsonProperty("declared_value")
    public Integer declaredValue;
    @JsonProperty("rates")
    public List<Rate> rates;
    @JsonProperty("special_instructions_collection")
    public String specialInstructionsCollection;
    @JsonProperty("special_instructions_delivery")
    public String specialInstructionsDelivery;
    @JsonProperty("proof_of_delivery_pin_hashed")
    public String proofOfDeliveryPinHashed;
    @JsonProperty("collected_date")
    public Object collectedDate;
    @JsonProperty("original_collected_date")
    public Object originalCollectedDate;
    @JsonProperty("delivered_date")
    public Object deliveredDate;
    @JsonProperty("is_return")
    public Boolean isReturn;
    @JsonProperty("estimated_collection")
    public String estimatedCollection;
    @JsonProperty("original_estimated_collection")
    public String originalEstimatedCollection;
    @JsonProperty("estimated_delivery_from")
    public String estimatedDeliveryFrom;
    @JsonProperty("estimated_delivery_to")
    public String estimatedDeliveryTo;
    @JsonProperty("original_estimated_delivery_from")
    public String originalEstimatedDeliveryFrom;
    @JsonProperty("is_pending")
    public Boolean isPending;
    @JsonProperty("current_branch_id")
    public Integer currentBranchId;
    @JsonProperty("current_branch_name")
    public String currentBranchName;
    @JsonProperty("collection_branch_id")
    public Integer collectionBranchId;
    @JsonProperty("collection_branch_name")
    public String collectionBranchName;
    @JsonProperty("delivery_branch_id")
    public Integer deliveryBranchId;
    @JsonProperty("delivery_branch_name")
    public String deliveryBranchName;
    @JsonProperty("account_branch_id")
    public Integer accountBranchId;
    @JsonProperty("transient_branch_ids")
    public Object transientBranchIds;
    @JsonProperty("service_level_cut_off_time")
    public String serviceLevelCutOffTime;
    @JsonProperty("collection_cutoff_override")
    public Boolean collectionCutoffOverride;
    @JsonProperty("all_charges_reversed")
    public Boolean allChargesReversed;
    @JsonProperty("total_note_count")
    public Integer totalNoteCount;
    @JsonProperty("external_note_count")
    public Integer externalNoteCount;
    @JsonProperty("disable_new_charges")
    public Boolean disableNewCharges;
    @JsonProperty("mute_notifications")
    public Boolean muteNotifications;
    @JsonProperty("payment_intervention_status")
    public String paymentInterventionStatus;
    @JsonProperty("pod_method")
    public String podMethod;
    @JsonProperty("has_pod_files")
    public Boolean hasPodFiles;
    @JsonProperty("has_been_invoiced")
    public Boolean hasBeenInvoiced;
    @JsonProperty("tracking_events")
    public Object trackingEvents;
    @JsonProperty("collection_rescheduled")
    public Boolean collectionRescheduled;
    @JsonProperty("delivery_rescheduled")
    public Boolean deliveryRescheduled;
    @JsonProperty("rating_reference")
    public String ratingReference;
    @JsonProperty("has_rating")
    public Boolean hasRating;

}
