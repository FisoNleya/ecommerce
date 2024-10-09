
package com.manica.productscatalogue.delivery.courier.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "time_created",
    "invoice_counter",
    "credit_note_counter",
    "interhub_transfer_counter",
    "hik_vision_last_tracking_event_id"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    @JsonProperty("name")
    public Object name;
    @JsonProperty("time_created")
    public Object timeCreated;
    @JsonProperty("invoice_counter")
    public Integer invoiceCounter;
    @JsonProperty("credit_note_counter")
    public Integer creditNoteCounter;
    @JsonProperty("interhub_transfer_counter")
    public Integer interhubTransferCounter;
    @JsonProperty("hik_vision_last_tracking_event_id")
    public Integer hikVisionLastTrackingEventId;

}
