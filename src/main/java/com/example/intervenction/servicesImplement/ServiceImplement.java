package com.example.intervenction.servicesImplement;

import com.example.intervenction.entities.Etudiant;
import com.example.intervenction.entities.Service;
import com.example.intervenction.repositories.ServiceRepository;
import com.example.intervenction.services.ServiceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImplement implements ServiceService {

    ServiceRepository serviceRepository;

    private String response = "";

    public ServiceImplement(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> all() {
        List<Service> serviceList = new ArrayList<>();
        serviceRepository.findAll().forEach(serviceList::add);
        return serviceList;
    }

    @Override
    public Service un(Long id) {
        return serviceRepository.findById(id).get();
    }

    @Override
    public String plus(Service service) {
        final Service service1 = serviceRepository.save(service);
        if (service1 != null) {
            return this.response = "Le département " + service1.getLibelle() + " à été enregistrée";
        }else {
            return this.response = "Le département  n'a pas été enregistré";
        }
    }

    @Override
    public String update(Long id, Service ser) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            service.setLibelle(ser.getLibelle());
            service.setDescription(ser.getDescription());

            final Service service1 = serviceRepository.save(service);
            if (service1 != null) {
                return this.response = "Le département " + service1.getLibelle() + " à été modifié";
            }else {
                return this.response = "Le département  n'a pas pue etre modifié";
            }
        }else {
            throw new IllegalArgumentException("Le département est introuvable, ID : " + id);
        }
    }

    @Override
    public String on(Long id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            service.setStatut("inactif");
            final Service service1 = serviceRepository.save(service);
            if (service1 != null) {
                return this.response = "Le département " + service1.getLibelle() + " à été désactivé";
            }else {
                return this.response = "Le département  n'a pas pu etre désactivé";
            }
        }else {
            throw new IllegalArgumentException("Le département est introuvable, ID : " + id);
        }
    }

    @Override
    public String off(Long id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            service.setStatut("inactif");
            final Service service1 = serviceRepository.save(service);
            if (service1 != null) {
                return this.response = "Le département " + service1.getLibelle() + " à été désactivé";
            }else {
                return this.response = "Le département  n'a pas pu etre désactivé";
            }
        }else {
            throw new IllegalArgumentException("Le département est introuvable, ID : " + id);
        }
    }

    @Override
    public String clean(Long id) {
        return null;
    }
}
