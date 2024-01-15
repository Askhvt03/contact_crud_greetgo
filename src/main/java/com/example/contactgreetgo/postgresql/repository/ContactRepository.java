package com.example.contactgreetgo.postgresql.repository;

import com.example.contactgreetgo.postgresql.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("postgreSqlContactRepository")
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByPhoneNumber(String phoneNumber);

    @Query(value = "select * from t_contact c order by c.id limit ?1 offset ?2", nativeQuery = true)
    List<Contact> findAllByLimitAndOffset(Integer limit, Integer offset);
}
