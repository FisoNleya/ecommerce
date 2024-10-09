package com.manica.productscatalogue.ordering.order.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SockNotification {

    private List<String> targets;

    private String topic;

    private Boolean isPublic;

    private String content;

}
