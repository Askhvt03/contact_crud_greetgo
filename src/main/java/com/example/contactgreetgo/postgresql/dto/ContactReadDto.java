package com.example.contactgreetgo.postgresql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactReadDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private String secondPhoneNumber;
}
