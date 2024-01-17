package com.example.contactgreetgo.mongodb.controller;

import com.example.contactgreetgo.mongodb.dto.ContactCreateDto;
import com.example.contactgreetgo.mongodb.dto.ContactFilter;
import com.example.contactgreetgo.mongodb.dto.ContactReadDto;
import com.example.contactgreetgo.mongodb.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController("mongoDbContactController")
@RequestMapping("/contact/mongodb")
public class ContactController {

    @Autowired
    @Qualifier("mongoDbContactService")
    private ContactService contactService;

    @GetMapping
    public List<ContactReadDto> findAll(ContactFilter filter) {
        return contactService.findAll(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactReadDto> findById(@PathVariable("id") String id) {
        ContactReadDto contact = contactService.findById(id);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<ContactReadDto> findByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        ContactReadDto contact = contactService.findByNumber(phoneNumber);
        return ResponseEntity.ok(contact);
    }

    @PostMapping
    public ResponseEntity<ContactReadDto> create(@RequestBody ContactCreateDto contact) {
        ContactReadDto newContact = contactService.create(contact);
        return new ResponseEntity<>(newContact, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactReadDto> updateById(@PathVariable("id") String id,
                                                     @RequestBody ContactCreateDto contact) {
        ContactReadDto updatedContact = contactService.updateById(id, contact);
        return ResponseEntity.ok(updatedContact);
    }

    @PutMapping("/phone/{phoneNumber}")
    public ResponseEntity<ContactReadDto> updateByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber,
                                                              @RequestBody ContactCreateDto contact) {
        ContactReadDto updatedContact = contactService.updateByPhoneNumber(phoneNumber, contact);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) {
        contactService.deleteById(id);
    }

    @DeleteMapping("/phone/{phoneNumber}")
    @ResponseStatus(NO_CONTENT)
    public void deleteByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        contactService.deleteByPhoneNumber(phoneNumber);
    }
}
