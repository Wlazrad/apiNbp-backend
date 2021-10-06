package com.example.apinbpbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NbpTableRate {
    private String currency;
    private String code;
    private BigDecimal mid;
    private BigDecimal bid;
    private BigDecimal ask;
}
