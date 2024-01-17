package com.example.contactgreetgo.postgresql;

import com.example.contactgreetgo.postgresql.dto.ContactCreateDto;
import com.example.contactgreetgo.postgresql.dto.ContactReadDto;
import com.example.contactgreetgo.postgresql.entity.Contact;
import com.example.contactgreetgo.postgresql.repository.ContactRepository;
import com.example.contactgreetgo.postgresql.service.impl.ContactServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class ContactServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Askhat";
    private static final String PHONE_NUMBER = "87072982487";
    private static final String SECOND_PHONE_NUMBER = "87472877883";
    private static final LocalDate BIRTH_DATE = LocalDate.parse("2003-08-03");

    @InjectMocks
    private ContactServiceImpl contactService;

    @Mock
    private ContactRepository contactRepository;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        Contact contact = new Contact();
        contact.setId(ID);

        when(contactRepository.findById(ID)).thenReturn(Optional.of(contact));

        ContactReadDto result = contactService.findById(ID);

        assertNotNull(result);
        assertEquals(result.getId(), ID);
    }

    @Test
    public void testCreate() {
        ContactCreateDto createDto = new ContactCreateDto();
        createDto.setName(NAME);
        createDto.setBirthDate(BIRTH_DATE.toString());
        createDto.setPhoneNumber(PHONE_NUMBER);
        createDto.setSecondPhoneNumber(SECOND_PHONE_NUMBER);



        Contact contact = new Contact();
        contact.setId(ID);
        contact.setName(NAME);
        contact.setBirthDate(BIRTH_DATE);
        contact.setPhoneNumber(PHONE_NUMBER);
        contact.setSecondPhoneNumber(SECOND_PHONE_NUMBER);

        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactReadDto result = contactService.create(createDto);

        assertNotNull(result);
        assertEquals(result.getId(), contact.getId());
        assertEquals(result.getName(), createDto.getName());
        assertEquals(result.getBirthDate().toString(), createDto.getBirthDate());
        assertEquals(result.getPhoneNumber(), createDto.getPhoneNumber());
        assertEquals(result.getSecondPhoneNumber(), createDto.getSecondPhoneNumber());
    }
}
