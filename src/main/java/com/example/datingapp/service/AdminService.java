package com.example.datingapp.service;

import com.example.datingapp.domain.Roles;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;


@Service
@Transactional
public class AdminService {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public AdminService(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }


    public List<AdminDto> getUsersWithRoles() {
        return userService.getAllUser().stream()
                .map(AdminDto::new).collect(Collectors.toList());

    }
    public ResponseEntity<Void> editRoles(String username, String roles) {
        User user = userService.findUserByUsername(username);
        String[] rolesSlice = roles.split(",");

        if(user == null){
            return new ResponseEntity<>(NOT_FOUND);
        }


        user.setRoles(Arrays.stream(rolesSlice)
                .map(name ->  Roles.valueOf("ROLE_" + name)).collect(Collectors.toList()));
        accountService.saveUser(user);
        return new ResponseEntity<>(OK);
    }
}
