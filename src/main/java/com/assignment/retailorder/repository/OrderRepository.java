package com.assignment.retailorder.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.retailorder.entity.RetailOrder;

public interface OrderRepository extends JpaRepository<RetailOrder, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "update RetailOrder set status = :status where orderId= :orderId")
	public void updateOrder(@Param("orderId") Long id, @Param("status") String sta);

}
