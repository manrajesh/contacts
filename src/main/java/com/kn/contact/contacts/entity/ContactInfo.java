package com.kn.contact.contacts.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String imageUrl;

}
