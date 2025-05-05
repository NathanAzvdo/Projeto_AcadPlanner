package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.Materia;
import com.NathanAzvdo.AcadPlanner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserDetails> findByEmail(String email);

}
