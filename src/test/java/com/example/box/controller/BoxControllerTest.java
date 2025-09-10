package com.example.box.controller;

import com.example.box.entity.Box;
import com.example.box.entity.BoxState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BoxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Box box;

    @BeforeEach
    public void setup() {
        box = new Box();
        box.setTxref("TEST-BOX");
        box.setWeightLimit(500);
        box.setBatteryCapacity(90);
        box.setState(BoxState.IDLE);
    }

    @Test
    public void testCreateBox() throws Exception {
        mockMvc.perform(post("/api/boxes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(box)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("00")))
                .andExpect(jsonPath("$.responseMsg", is("Success")))
                .andExpect(jsonPath("$.data.txref", is("TEST-BOX")));
    }

    @Test
    public void testGetAvailable() throws Exception {
        mockMvc.perform(get("/api/boxes/available")).andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is("00")));
    }
}
