package com.kn.contact.contacts.controller;


import com.kn.contact.contacts.ContactsApplicationTests;
import com.kn.contact.contacts.entity.ContactInfo;
import com.kn.contact.contacts.error.CustomErrorResponse;
import com.kn.contact.contacts.service.ContactsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactsControllerTest extends ContactsApplicationTests {

    @MockBean
    ContactsService contactsService;

    @Test
    public void whenGetContacts_validArguments_shouldReturnOk() throws Exception {
        List<ContactInfo> contacts = new ArrayList<>();
        contacts.add(ContactInfo.builder().id(1L).name("test").imageUrl("https://images.app.goo.gl/rGN2pBvg4DRD83J57").build());
        contacts.add(ContactInfo.builder().id(2L).name("ram").imageUrl("https://images.app.goo.gl/rGN2pBvg4DRD83788").build());

        Pageable pageable = PageRequest.of(0,20);
        Page<ContactInfo> result = new PageImpl<>(contacts,pageable,1);
        String expectedApiResponseJson = objectWriter.writeValueAsString(result);

        Mockito.when(contactsService.getAllContacts(Mockito.any(Pageable.class))).thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts?page=0&size=20")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andDo(print())
                        .andExpect(jsonPath("$", notNullValue()))
                        .andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(contactsService, Mockito.times(1)).getAllContacts(pageable);
    }

    @Test
    public void whenGetContacts_InValidArguments_shouldThrowException() throws Exception {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse("ERROR", "getContacts.page: must be greater than or equal to 0");
        String expectedApiResponseJson = objectWriter.writeValueAsString(customErrorResponse);

        Pageable pageable = PageRequest.of(0,20);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts?page=-9&size=20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(contactsService, Mockito.times(0)).getAllContacts(pageable);
    }

    @Test
    public void whenGetContactsByName_validArguments_shouldReturnOk() throws Exception {
        List<ContactInfo> contacts = new ArrayList<>();
        contacts.add(ContactInfo.builder().id(1L).name("test").imageUrl("https://images.app.goo.gl/rGN2pBvg4DRD83J57").build());

        Pageable pageable = PageRequest.of(0,20);
        Page<ContactInfo> result = new PageImpl<>(contacts,pageable,1);
        String expectedApiResponseJson = objectWriter.writeValueAsString(result);

        Mockito.when(contactsService.getAllContactsByName("test", pageable)).thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts/search?searchTerm=test&page=0&size=20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(contactsService, Mockito.times(1)).getAllContactsByName("test", pageable);
    }

    @Test
    public void whenGetContactsByName_InValidArguments_shouldThrowException() throws Exception {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse("ERROR", "getContactsByName.searchTerm: search term is required.");
        String expectedApiResponseJson = objectWriter.writeValueAsString(customErrorResponse);

        Pageable pageable = PageRequest.of(0,20);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchTerm", "");
        params.add("page", "0");
        params.add("size", "20");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts/search")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(contactsService, Mockito.times(0)).getAllContactsByName("test", pageable);
    }
}
