package com.example.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.box.entity.Box;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {
    Optional<Box> findByTxref(String txref);
}
