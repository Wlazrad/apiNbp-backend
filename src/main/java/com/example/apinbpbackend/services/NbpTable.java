package com.example.apinbpbackend.services;

import com.example.apinbpbackend.dto.NbpTableRate;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NbpTable {
    private String table;
    private String no;
    private String effectiveDate;
    private String tradingDate;
    private List<NbpTableRate> rates = new ArrayList<>();
}
