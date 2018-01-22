package com.britishgas.apione.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.britishgas.apione.model.Response;


/**
 * Created by Kundan Sharma.
 */
@RestController
@RequestMapping("/")
public class ApioneController {
    private static final Logger log = LoggerFactory.getLogger(ApioneController.class);

    @Value("${anotherapiURL}")
    private String anotherapiURL;
    private RestTemplate restTemplate;

    public ApioneController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/callAnotherApi", method = RequestMethod.GET)
    public Response callAnotherApi() {
        Response message = restTemplate.getForObject(anotherapiURL, Response.class);
        message.addResponseMessage("Initial call received by apione, other api is called and returning now");
        log.info(message.toString());
        return message;
    }

    @RequestMapping(value = "/callByAnotherApi", method = RequestMethod.GET)
    public Response callByAnotherApi() {
        Response message = new Response();
        message.addResponseMessage("From apione: I got your call.");
        log.info(message.toString());
        return message;
    }
}
