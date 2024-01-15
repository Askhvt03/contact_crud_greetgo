package com.example.contactgreetgo.postgresql.service.impl;

import com.example.contactgreetgo.postgresql.dto.ContactCreateDto;
import com.example.contactgreetgo.postgresql.dto.ContactFilter;
import com.example.contactgreetgo.postgresql.dto.ContactReadDto;
import com.example.contactgreetgo.postgresql.entity.Contact;
import com.example.contactgreetgo.postgresql.mapper.ContactCreateMapper;
import com.example.contactgreetgo.postgresql.mapper.ContactReadMapper;
import com.example.contactgreetgo.postgresql.repository.ContactRepository;
import com.example.contactgreetgo.postgresql.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service("postgreSqlContactService")
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {
    @Autowired
    @Qualifier("postgreSqlContactRepository")
    private ContactRepository contactRepository;

    @Override
    public List<ContactReadDto> findAll(ContactFilter filter) {
        return contactRepository.findAllByLimitAndOffset(filter.limit(), filter.offset()).stream()
                .map(ContactReadMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public ContactReadDto findById(Long id) {
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

    @Transactional
    @Override
    public ContactReadDto create(ContactCreateDto contact) {
        return Optional.of(contact)
                .map(ContactCreateMapper.INSTANCE::toEntity)
                .map(contactRepository::save)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
    }

    @Transactional
    @Override
    public ContactReadDto updateById(Long id, ContactCreateDto contact) {
        return contactRepository.findById(id)
                .map(entity -> copy(contact, entity))
                .map(contactRepository::saveAndFlush)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Transactional
    @Override
    public ContactReadDto updateByNumber(String phoneNumber, ContactCreateDto contact) {
        return contactRepository.findByPhoneNumber(phoneNumber)
                .map(entity -> copy(contact, entity))
                .map(contactRepository::saveAndFlush)
                .map(ContactReadMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        contactRepository.findById(id)
                .ifPresent(entity -> contactRepository.deleteById(id));
    }

    @Transactional
    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        contactRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(entity -> contactRepository.deleteById(entity.getId()));
    }

    private Contact copy(ContactCreateDto fromObject, Contact toObject){
        toObject.setName(fromObject.getName());
        toObject.setBirthDate(LocalDate.parse(fromObject.getBirthDate()));
        toObject.setPhoneNumber(fromObject.getPhoneNumber());
        toObject.setSecondPhoneNumber(fromObject.getSecondPhoneNumber());

        return toObject;
    }
}
