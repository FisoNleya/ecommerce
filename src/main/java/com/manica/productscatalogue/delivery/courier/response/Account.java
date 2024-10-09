
package com.manica.productscatalogue.delivery.courier.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonPropertyOrder({
    "id",
    "provider_id",
    "branch_id",
    "branch_name",
    "name",
    "account_code",
    "account_type",
    "credit_limit",
    "payment_terms",
    "invoice_frequency",
    "invoice_frequency_time",
    "balance",
    "credit_allocation_balance",
    "is_on_hold",
    "disable_auto_hold",
    "temp_disable_auto_hold",
    "charge_discrepancy_threshold_override",
    "invoice_allocation_method",
    "created_by",
    "time_created",
    "time_modified",
    "default_collection_address_id",
    "custom_fields",
    "force_show_billing_transactions",
    "invoice_trigger_shipment_status",
    "status",
    "trading_start_date",
    "current_rate_group_id",
    "current_rate_group_name",
    "pod_method",
    "pod_method_visible_on_shipment",
    "include_unencrypted_pod_pin",
    "disable_out_for_delivery_sms",
    "disable_out_for_delivery_email",
    "disable_driver_app_notification_sms",
    "disable_driver_app_notification_email",
    "disable_account_in_arrears_checks",
    "override_tracking_prefixes"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("provider_id")
    public Integer providerId;
    @JsonProperty("branch_id")
    public Integer branchId;
    @JsonProperty("branch_name")
    public Object branchName;
    @JsonProperty("name")
    public String name;
    @JsonProperty("account_code")
    public String accountCode;
    @JsonProperty("account_type")
    public String accountType;
    @JsonProperty("credit_limit")
    public Integer creditLimit;
    @JsonProperty("payment_terms")
    public Integer paymentTerms;
    @JsonProperty("invoice_frequency")
    public String invoiceFrequency;
    @JsonProperty("invoice_frequency_time")
    public Integer invoiceFrequencyTime;
    @JsonProperty("balance")
    public Integer balance;
    @JsonProperty("credit_allocation_balance")
    public Integer creditAllocationBalance;
    @JsonProperty("is_on_hold")
    public Boolean isOnHold;
    @JsonProperty("disable_auto_hold")
    public Boolean disableAutoHold;
    @JsonProperty("temp_disable_auto_hold")
    public Boolean tempDisableAutoHold;
    @JsonProperty("charge_discrepancy_threshold_override")
    public Integer chargeDiscrepancyThresholdOverride;
    @JsonProperty("invoice_allocation_method")
    public String invoiceAllocationMethod;
    @JsonProperty("created_by")
    public String createdBy;
    @JsonProperty("time_created")
    public String timeCreated;
    @JsonProperty("time_modified")
    public String timeModified;
    @JsonProperty("default_collection_address_id")
    public Integer defaultCollectionAddressId;
    @JsonProperty("custom_fields")
    public Object customFields;
    @JsonProperty("force_show_billing_transactions")
    public Boolean forceShowBillingTransactions;
    @JsonProperty("invoice_trigger_shipment_status")
    public Object invoiceTriggerShipmentStatus;
    @JsonProperty("status")
    public String status;
    @JsonProperty("trading_start_date")
    public Object tradingStartDate;
    @JsonProperty("current_rate_group_id")
    public Integer currentRateGroupId;
    @JsonProperty("current_rate_group_name")
    public String currentRateGroupName;
    @JsonProperty("pod_method")
    public String podMethod;
    @JsonProperty("pod_method_visible_on_shipment")
    public Boolean podMethodVisibleOnShipment;
    @JsonProperty("include_unencrypted_pod_pin")
    public Boolean includeUnencryptedPodPin;
    @JsonProperty("disable_out_for_delivery_sms")
    public Boolean disableOutForDeliverySms;
    @JsonProperty("disable_out_for_delivery_email")
    public Boolean disableOutForDeliveryEmail;
    @JsonProperty("disable_driver_app_notification_sms")
    public Boolean disableDriverAppNotificationSms;
    @JsonProperty("disable_driver_app_notification_email")
    public Boolean disableDriverAppNotificationEmail;
    @JsonProperty("disable_account_in_arrears_checks")
    public Boolean disableAccountInArrearsChecks;
    @JsonProperty("override_tracking_prefixes")
    public Boolean overrideTrackingPrefixes;

}
