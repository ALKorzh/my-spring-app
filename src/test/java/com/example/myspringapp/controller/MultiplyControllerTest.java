package com.example.myspringapp.controller;

import com.example.myspringapp.config.TestSecurityConfig;
import com.example.myspringapp.service.MultiplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MultiplyController.class)
@Import(TestSecurityConfig.class)
class MultiplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MultiplyService multiplyService;

    @Test
    void postValidNumber_shouldShowResultPage() throws Exception {
        when(multiplyService.parseAndMultiply("4")).thenReturn(20);

        mockMvc.perform(post("/result").param("number", "4"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("number", 4))
                .andExpect(model().attribute("result", 20));
    }

    @Test
    void postInvalidNumber_shouldRedirectWithError() throws Exception {
        when(multiplyService.parseAndMultiply("abc")).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/result").param("number", "abc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/multiply"));
    }
}
