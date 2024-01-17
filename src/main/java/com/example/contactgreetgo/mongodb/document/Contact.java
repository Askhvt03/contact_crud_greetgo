package com.example.contactgreetgo.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "contact")
public class Contact {
    @MongoId
    private String id;

    private String name;

    private LocalDate birthDate;

    private String phoneNumber;

    private String secondPhoneNumber;

    private LocalDateTime createdAt;
}
