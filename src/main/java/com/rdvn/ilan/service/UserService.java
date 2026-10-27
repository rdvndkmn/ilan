package com.rdvn.ilan.service;

import com.rdvn.ilan.enums.Role;
import com.rdvn.ilan.entity.User;
import com.rdvn.ilan.dto.UserRegisterRequest;
import com.rdvn.ilan.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String saveUser(User user){
        String newPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);
        return "başarılı";
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getIdUser(long id){

        return userRepository.findById(id);
    }

    public Optional<User> putIdUser(long id, User user){
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();

            currentUser.setName(user.getName());
            currentUser.setSurname(user.getSurname());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone(user.getPhone());
            currentUser.setPassword(user.getPassword());
            userRepository.save(currentUser);
            return Optional.of(currentUser);
        }

        return Optional.empty();
    }

    public Optional<User> getEmail(String email){
        return userRepository.findByEmail(email);
    }

    public String deleteUser(long id){
        userRepository.deleteById(id);
        return "silindi";
    }

    public String saveUserRegisterRequest(UserRegisterRequest userRegisterRequest){

         if(userRepository.findByEmail(userRegisterRequest.getEmail()).isEmpty()){
             User newUser = new User();
             String hPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
             newUser.setName(userRegisterRequest.getFirstName());
             newUser.setSurname(userRegisterRequest.getLastName());
             newUser.setEmail(userRegisterRequest.getEmail());
             newUser.setPhone(userRegisterRequest.getPhone());
             newUser.setPassword(hPassword);
             newUser.setRole(Role.USER);
             newUser.setCreatedAt(LocalDateTime.now());
             userRepository.save(newUser);
             return "başarılı";
         }


         return "mail mevcut";

    }

    public String getAdmin(){
        return "admin kontrolü başarılı";

    }




}
