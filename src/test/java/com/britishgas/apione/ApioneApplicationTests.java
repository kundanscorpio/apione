package com.britishgas.apione;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.britishgas.apione.controller.ApioneController;
import com.britishgas.apione.model.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApioneApplicationTests {
    @Value("${anotherapiURL}")
    private String anotherapiURL;

    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    @InjectMocks
    private ApioneController controller;

    @Before
    public void setup() {
        Response message = new Response();
        message.addResponseMessage("From apitwo: I got your call.");
        Mockito.when(restTemplate.getForObject(anotherapiURL, Response.class)).thenReturn(message);
        this.mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void callByAnotherApiReturnsOk() throws Exception {
        mockMvc.perform(get("/callByAnotherApi"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.messages").value("From apione: I got your call."));
    }

    @Test
    public void callReturnsAfterCallingAnotherApi() throws Exception {
        mockMvc.perform(get("/callAnotherApi"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.messages[0]").value("From apitwo: I got your call."))
                .andExpect(jsonPath("$.messages[1]").value("Initial call received by apione, other api is called and returning now"));
    }
}