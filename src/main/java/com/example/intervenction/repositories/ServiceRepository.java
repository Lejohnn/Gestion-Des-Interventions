package com.example.intervenction.repositories;

import com.example.intervenction.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    interface EtudiantRepository {
    }
}
