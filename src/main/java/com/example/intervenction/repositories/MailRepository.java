package com.example.intervenction.repositories;

import com.example.intervenction.entities.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository  extends JpaRepository<Mail, Long> {
}
