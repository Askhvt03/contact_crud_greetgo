package com.example.contactgreetgo.mongodb.mapper;

import com.example.contactgreetgo.mongodb.dto.ContactCreateDto;
import com.example.contactgreetgo.mongodb.document.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactCreateMapper {
    ContactCreateMapper INSTANCE = Mappers.getMapper(ContactCreateMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Contact toDocument(ContactCreateDto dto);
}
