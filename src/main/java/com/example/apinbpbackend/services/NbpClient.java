package com.example.apinbpbackend.services;

import com.example.apinbpbackend.dto.NbpTableRate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NbpClient {

    private final RestTemplate restTemplate;

    public void sendRequest(TableType table, LocalDate date, int limit) {
        StringBuilder url;

        url = new StringBuilder("http://api.nbp.pl/api/exchangerates/tables/");
        url.append(table).append("/");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<NbpTable[]> responseEntity = null;

        try {
            responseEntity = restTemplate.getForEntity(url.toString(), NbpTable[].class, entity);
        } catch (ResourceAccessException resourceAccessException) {
            if (limit == 3) {
                throw new NbpException(resourceAccessException.getMessage(), resourceAccessException);
            } else {
                sendRequest(table, date, limit + 1);
            }
        }

        List<NbpTableRate> rates = Collections.unmodifiableList(getNbpTableRates(responseEntity));

        printTable(date, rates);
    }

    private List<NbpTableRate> getNbpTableRates(ResponseEntity<NbpTable[]> responseEntity) {
        Optional<NbpTable> first = Arrays.stream(Objects.requireNonNull(responseEntity.getBody())).findFirst();
        List<NbpTableRate> rates = first.get().getRates();
        return rates;
    }

    private void printTable(LocalDate date, List<NbpTableRate> rates) {
        System.out.println("Kursy na dzień " + date.toString() + "\n" + "nazwa waluty, kupno, sprzedaż");
        rates.forEach(x -> System.out.println(x.getCurrency() + " " + x.getBid() + " " + x.getAsk()));
    }

    @PostConstruct
    public void setUp() {
        sendRequest(TableType.C, LocalDate.of(2017, 11, 9), 3);
    }
}
