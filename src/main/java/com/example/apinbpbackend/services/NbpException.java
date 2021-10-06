package com.example.apinbpbackend.services;

public class NbpException extends RuntimeException {
    NbpException(String response, Exception e) {
        super(response, e);
    }
}
