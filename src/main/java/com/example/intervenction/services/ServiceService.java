package com.example.intervenction.services;

import com.example.intervenction.entities.Service;

import java.util.List;

public interface ServiceService {
    List<Service> all();

    Service un(Long id);

    String plus(Service service);

    String update(Long id, Service service);

    String on(Long id);

    String off(Long id);

    String clean(Long id);
}
