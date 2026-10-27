package com.rdvn.ilan.repository;

import com.rdvn.ilan.enums.Role;
import com.rdvn.ilan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail (String email);
    Optional<User> findByRole (Role role);
}
