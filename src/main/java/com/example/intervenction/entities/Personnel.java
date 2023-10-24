package com.example.intervenction.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@Data
@Table(name = "personnel")
@DiscriminatorValue("1")
public class Personnel extends Utilisateurs{

    public Personnel() {
        super();
    }

    @ManyToOne
    @JoinColumn(name="service", nullable=false)
    private Service service;
}
