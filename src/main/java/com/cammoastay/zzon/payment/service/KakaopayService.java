package com.cammoastay.zzon.payment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Slf4j
@Service
public class KakaopayService {



    public <T> T tokakaoServer(String secretkey, String url, Object reqBody, Class<T> resp) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://open-api.kakaopay.com")
                .path(url)
                .build()
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "SECRET_KEY " + secretkey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestsMessage = new HttpEntity<>(reqBody, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> tResponseEntity = restTemplate.postForEntity(uri, requestsMessage, resp);
        if(!tResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("서버간 통신 에러 발생 = " + tResponseEntity);
        }
        return tResponseEntity.getBody();
    }
}
