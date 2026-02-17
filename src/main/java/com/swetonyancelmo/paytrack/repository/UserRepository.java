package com.swetonyancelmo.paytrack.repository;

import com.swetonyancelmo.paytrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

}
