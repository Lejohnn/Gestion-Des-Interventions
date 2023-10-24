package com.example.intervenction.controllers;

import com.example.intervenction.entities.Service;
import com.example.intervenction.services.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ServiceController {

    ServiceService serviceService;

    private String res = "";

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/allSer")
    public List<Service> allSer(){
        return serviceService.all();
    }




    @GetMapping("/unSer/{id}")
    public Service unServ(@PathVariable(value = "id") Long id){
        return serviceService.un(id);
    }




    @PostMapping("/addSer")
    public String addSer(@RequestBody Service service){
        return serviceService.plus(service);
    }


    @PutMapping("/updateSer/{id}")
    public String updateSer(@PathVariable Long id, @RequestBody Service service){
        return serviceService.update(id, service);
    }


    @PutMapping("/onSer/{id}")
    public String onSer(@PathVariable(value = "id") Long id){
        return serviceService.on(id);
    }


    @PutMapping("/offSer/{id}")
    public String offSer(@PathVariable(value = "id") Long id){
        return serviceService.off(id);
    }

}
