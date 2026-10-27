package com.rdvn.ilan.controller;

import com.rdvn.ilan.entity.User;
import com.rdvn.ilan.dto.UserRegisterRequest;
import com.rdvn.ilan.enums.Role;
import com.rdvn.ilan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public String addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/user")
    public List<User> user(){
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> userId(@PathVariable("id") long id){
        Optional<User> user = userService.getIdUser(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user/{id}")
    public Optional<User> putUser(@PathVariable("id") long id, @RequestBody User user){
        return userService.putIdUser(id,user);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/user/email/{email}")
    public Optional<User> getEmail(@PathVariable("email") String email){
        return userService.getEmail(email);
    }

    @PostMapping("/api/users/register")
    public String resigterSave(@RequestBody UserRegisterRequest userRegisterRequest){
        return userService.saveUserRegisterRequest(userRegisterRequest);
    }



    /**
    @GetMapping(value = "/user", params = "name")
    public User userName(@RequestParam("name") String name){
        return userService.nameUser(name);
    }
    **/

}
