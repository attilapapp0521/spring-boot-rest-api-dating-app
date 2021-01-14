package com.example.datingapp.controller;

import com.example.datingapp.dto.AdminDto;
import com.example.datingapp.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users-with-roles")
    public ResponseEntity<List<AdminDto>> getUsersWithRoles() {
           return new ResponseEntity<>(adminService.getUsersWithRoles(), OK) ;
    }

    @PostMapping("/edit-roles/{username}")
    public ResponseEntity<Void> editRoles(@PathVariable String username, String roles){
        return adminService.editRoles(username, roles.toUpperCase());
    }



    public ResponseEntity<Void> getPhotosForModeration() {
        return new ResponseEntity<>(OK);
    }

}
