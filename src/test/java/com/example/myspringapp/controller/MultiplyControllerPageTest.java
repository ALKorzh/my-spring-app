package com.example.myspringapp.controller;

import com.example.myspringapp.config.TestSecurityConfig;
import com.example.myspringapp.service.MultiplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MultiplyController.class)
@Import(TestSecurityConfig.class)
public class MultiplyControllerPageTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MultiplyService multiplyService;

    @Test
    void getMultiplyForm_shouldReturnInputFormPage() throws Exception {
        mockMvc.perform(get("/multiply"))
                .andExpect(status().isOk())
                .andExpect(view().name("inputForm"));
    }

    @Test
    void getResultDirect_shouldRedirectToMultiply() throws Exception {
        mockMvc.perform(get("/result"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/multiply"));
    }
}
