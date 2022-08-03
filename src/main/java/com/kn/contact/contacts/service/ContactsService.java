package com.kn.contact.contacts.service;

import com.kn.contact.contacts.entity.ContactInfo;
import com.kn.contact.contacts.repository.ContactsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactsService {

    @Autowired
    ContactsRepository contactsRepository;

    public Page<ContactInfo> getAllContacts(Pageable pageable){
        return contactsRepository.findAllByOrderByNameAsc(pageable);
    }

    public Page<ContactInfo> getAllContactsByName(String searchName, Pageable pageable){
        return contactsRepository.findByNameIgnoreCaseContainingOrderByNameAsc(searchName, pageable);
    }
}
