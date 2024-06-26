package com.ra.repository.user;

import com.ra.models.entity.Order;
import com.ra.models.entity.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrdersByUserId(Long id);
    Page<Order> findOrdersByUser_FullNameLike (String fullName, Pageable pageable);

}
