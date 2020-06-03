package com.imengyu.datacenter.repository;

import com.imengyu.datacenter.entity.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    /**
     * 根据用户ID查询并分页
     * @param userId 用户ID
     * @param pageable 分页
     * @return
     */
    Page<Product> findByUserId(Integer userId, Pageable pageable);
}
