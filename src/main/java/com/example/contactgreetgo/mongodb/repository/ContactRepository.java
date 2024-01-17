package com.example.contactgreetgo.mongodb.repository;

import com.example.contactgreetgo.mongodb.document.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("mongoDbContactRepository")
public interface ContactRepository extends MongoRepository<Contact, String> {

    Optional<Contact> findByPhoneNumber(String phoneNumber);
}
