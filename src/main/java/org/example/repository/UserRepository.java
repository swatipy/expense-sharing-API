package org.example.repository;

import org.example.model.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserBalance,String > {

    Optional<UserBalance> findByUserId(String userId);
}
