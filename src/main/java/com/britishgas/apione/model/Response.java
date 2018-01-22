package com.britishgas.apione.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kundan Sharma.
 */
@Data
public class Response {
    List<String> messages = new ArrayList<>();

    public void addResponseMessage(String newMessage) {
        messages.add(newMessage);
    }
}
