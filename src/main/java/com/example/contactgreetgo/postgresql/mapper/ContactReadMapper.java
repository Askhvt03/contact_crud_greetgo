package com.example.contactgreetgo.postgresql.mapper;

import com.example.contactgreetgo.postgresql.dto.ContactReadDto;
import com.example.contactgreetgo.postgresql.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactReadMapper {
    ContactReadMapper INSTANCE = Mappers.getMapper(ContactReadMapper.class);
    ContactReadDto toDto(Contact entity);
}
