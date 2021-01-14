package com.example.datingapp.service;

import com.example.datingapp.domain.Roles;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        List<User> users = userService.getAllUser();
        List<AdminDto> adminDtoList = new ArrayList<>();

        for(User user : users){
            AdminDto adminDto = new AdminDto(user);
            adminDto.setRoles(user.getRoles().stream().map(Enum::toString).map(role -> role.substring(5))
                    .collect(Collectors.toList()));
            adminDtoList.add(adminDto);
        }
        return adminDtoList;
    }
    public ResponseEntity<Void> editRoles(String username, String roles) {
        User user = userService.findUserByUsername(username);
        String[] rolesSlice = roles.split(",");

        if(user == null){
            return new ResponseEntity<>(NOT_FOUND);
        }else if(roles.isEmpty()){
            return new ResponseEntity("At least one role must be assigned",BAD_REQUEST);
        }


        user.setRoles(Arrays.stream(rolesSlice)
                .map(name ->  Roles.valueOf("ROLE_" + name)).collect(Collectors.toList()));
        accountService.saveUser(user);
        return new ResponseEntity<>(OK);
    }
}
