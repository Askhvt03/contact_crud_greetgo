package com.example.contactgreetgo.mongodb.service;

import com.example.contactgreetgo.mongodb.dto.ContactCreateDto;
import com.example.contactgreetgo.mongodb.dto.ContactFilter;
import com.example.contactgreetgo.mongodb.dto.ContactReadDto;

import java.util.List;

public interface ContactService {
    List<ContactReadDto> findAll(ContactFilter filter);

    ContactReadDto findById(String id);

    ContactReadDto findByNumber(String phoneNumber);

    ContactReadDto create(ContactCreateDto contact);

    ContactReadDto updateById(String id, ContactCreateDto contact);

    ContactReadDto updateByPhoneNumber(String phoneNumber, ContactCreateDto contact);

    void deleteById(String id);

    void deleteByPhoneNumber(String phoneNumber);
}
