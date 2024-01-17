package com.example.contactgreetgo.mongodb.service.impl;

import com.example.contactgreetgo.mongodb.document.Contact;
import com.example.contactgreetgo.mongodb.dto.ContactCreateDto;
import com.example.contactgreetgo.mongodb.dto.ContactFilter;
import com.example.contactgreetgo.mongodb.dto.ContactReadDto;
import com.example.contactgreetgo.mongodb.mapper.ContactCreateMapper;
import com.example.contactgreetgo.mongodb.mapper.ContactReadMapper;
import com.example.contactgreetgo.mongodb.repository.ContactRepository;
import com.example.contactgreetgo.mongodb.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service("mongoDbContactService")
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {

    @Autowired
    @Qualifier("mongoDbContactRepository")
    private ContactRepository contactRepository;

    @Override
    public List<ContactReadDto> findAll(ContactFilter filter) {
        int offset = (filter != null && filter.offset() != null) ? filter.offset() : 0;
        int limit = (filter != null && filter.limit() != null) ? filter.limit() : 10;

        PageRequest pageable = PageRequest.of(offset, limit);

        return contactRepository.findAll(pageable).stream()
                .map(ContactReadMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public ContactReadDto findById(String id) {
        return contactRepository.findById(id)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public ContactReadDto findByNumber(String phoneNumber) {
        return contactRepository.findByPhoneNumber(phoneNumber)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public ContactReadDto create(ContactCreateDto contact) {
        return Optional.of(contact)
                .map(ContactCreateMapper.INSTANCE::toDocument)
                .map(contactRepository::save)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
    }

    @Override
    public ContactReadDto updateById(String id, ContactCreateDto contact) {
        return contactRepository.findById(id)
                .map(document -> copy(contact ,document))
                .map(contactRepository::save)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public ContactReadDto updateByPhoneNumber(String phoneNumber, ContactCreateDto contact) {
        return contactRepository.findByPhoneNumber(phoneNumber)
                .map(document -> copy(contact ,document))
                .map(contactRepository::save)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public void deleteById(String id) {
        contactRepository.findById(id)
                .ifPresent(document -> contactRepository.deleteById(id));
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        contactRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(document -> contactRepository.deleteById(document.getId()));
    }

    private Contact copy(ContactCreateDto fromObject, Contact toObject) {
        toObject.setName(fromObject.getName());
        toObject.setBirthDate(LocalDate.parse(fromObject.getBirthDate()));
        toObject.setPhoneNumber(fromObject.getPhoneNumber());
        toObject.setSecondPhoneNumber(fromObject.getSecondPhoneNumber());

        return toObject;
    }
}
