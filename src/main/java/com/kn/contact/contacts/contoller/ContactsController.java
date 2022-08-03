package com.kn.contact.contacts.contoller;

import com.kn.contact.contacts.entity.ContactInfo;
import com.kn.contact.contacts.service.ContactsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/contacts")
@Slf4j
@Validated
public class ContactsController {

    @Autowired
    ContactsService contactsService;

    @Operation(summary = "Get all contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all contacts",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactInfo.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Client call",
                    content = @Content)})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ContactInfo> getContacts(@RequestParam @Min(0) int page,
                                         @RequestParam int size) {
        log.info("****** get all contacts *******");
        Pageable pageable = PageRequest.of(page,size);
        return contactsService.getAllContacts(pageable);
    }

    @Operation(summary = "Contacts by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all contacts by name search",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactInfo.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Client call",
                    content = @Content)})
    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ContactInfo> getContactsByName(@RequestParam @NotBlank(message = "search term is required.") String searchTerm,
                                               @RequestParam @Min(0) int page,
                                               @RequestParam int size) {
        log.info("****** search by name {} *******", searchTerm);
        Pageable pageable = PageRequest.of(page,size);
        return contactsService.getAllContactsByName(searchTerm, pageable);
    }

}
