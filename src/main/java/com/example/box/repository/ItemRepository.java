package com.example.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.box.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
