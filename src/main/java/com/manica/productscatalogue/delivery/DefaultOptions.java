package com.manica.productscatalogue.delivery;


import lombok.Getter;

@Getter
public enum DefaultOptions {

    COURIER_GUY("The Courier Guy"),
    SEND_R("Send R"),;


    private String tradeName;

    private DefaultOptions(String tradeName){
        this.tradeName = tradeName;
    }

}
