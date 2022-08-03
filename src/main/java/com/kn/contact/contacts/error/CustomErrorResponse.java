package com.kn.contact.contacts.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomErrorResponse {

    private String statusType;
    private String message;
}