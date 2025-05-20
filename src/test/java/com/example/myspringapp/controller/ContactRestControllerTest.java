package com.example.myspringapp.controller;

import com.example.myspringapp.config.TestSecurityConfig;
import com.example.myspringapp.model.Contact;
import com.example.myspringapp.service.ContactService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactRestController.class)
@Import(TestSecurityConfig.class)
class ContactRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    void getAllContacts_shouldReturnListOfContacts() throws Exception {
        Contact contact1 = new Contact(1L, "Alice", "111");
        Contact contact2 = new Contact(2L, "Bob", "222");

        when(contactService.findAll()).thenReturn(List.of(contact1, contact2));

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].lastName", is("Alice")))
                .andExpect(jsonPath("$[0].phoneNumber", is("111")))
                .andExpect(jsonPath("$[1].lastName", is("Bob")))
                .andExpect(jsonPath("$[1].phoneNumber", is("222")));
    }

    @Test
    void addContact_shouldReturnSavedContact() throws Exception {
        Contact contact = new Contact(1L, "Charlie", "333");

        when(contactService.save(Mockito.any(Contact.class))).thenReturn(contact);

        String json = """
                {
                  "lastName": "Charlie",
                  "phoneNumber": "333"
                }
                """;

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Charlie")))
                .andExpect(jsonPath("$.phoneNumber", is("333")));
    }
}
