package com.example.intervenction.repositories;

import com.example.intervenction.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelRepository  extends JpaRepository<Personnel, Long> {
}
