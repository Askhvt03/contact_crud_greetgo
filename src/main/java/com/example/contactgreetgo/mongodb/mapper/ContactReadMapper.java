package com.example.contactgreetgo.mongodb.mapper;

import com.example.contactgreetgo.mongodb.dto.ContactReadDto;
import com.example.contactgreetgo.mongodb.document.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactReadMapper {
    ContactReadMapper INSTANCE = Mappers.getMapper(ContactReadMapper.class);
    ContactReadDto toDto(Contact document);
}
