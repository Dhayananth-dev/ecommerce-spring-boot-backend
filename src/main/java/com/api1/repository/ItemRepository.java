package com.api1.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api1.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
