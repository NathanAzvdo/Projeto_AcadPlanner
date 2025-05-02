package com.NathanAzvdo.AcadPlanner.repository;

import com.NathanAzvdo.AcadPlanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean findByEmail(String email);

}
