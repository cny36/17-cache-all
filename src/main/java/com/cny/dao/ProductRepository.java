package com.cny.dao;

import com.cny.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : chennengyuan
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
}
