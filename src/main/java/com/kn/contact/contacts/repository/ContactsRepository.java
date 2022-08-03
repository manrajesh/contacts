package com.kn.contact.contacts.repository;

import com.kn.contact.contacts.entity.ContactInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends PagingAndSortingRepository<ContactInfo, Long> {
    Page<ContactInfo> findAllByOrderByNameAsc(Pageable pageable);
    Page<ContactInfo> findByNameIgnoreCaseContainingOrderByNameAsc(String name, Pageable pageable);
}
