package com.api1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api1.entity.CustomerOrder;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder,Long>{

}
