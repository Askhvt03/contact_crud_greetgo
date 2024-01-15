package com.example.contactgreetgo.postgresql.mapper;

import com.example.contactgreetgo.postgresql.dto.ContactCreateDto;
import com.example.contactgreetgo.postgresql.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactCreateMapper {
    ContactCreateMapper INSTANCE = Mappers.getMapper(ContactCreateMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Contact toEntity(ContactCreateDto dto);
}
