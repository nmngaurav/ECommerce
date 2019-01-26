package com.ecommerce.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.application.entity.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>  {

}
