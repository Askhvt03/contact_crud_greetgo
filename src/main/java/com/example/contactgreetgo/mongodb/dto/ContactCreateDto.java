package com.example.contactgreetgo.mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactCreateDto {
    private String name;
    private String birthDate;
    private String phoneNumber;
    private String secondPhoneNumber;
}
