package com.imengyu.datacenter.repository;

import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.entity.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer>, JpaSpecificationExecutor<Device> {

    /**
     * 根据产品ID查询并分页
     * @param productId 产品ID
     * @param pageable 分页
     */
    Page<Device> findByProductId(Integer productId, Pageable pageable);

    /**
     * 根据用户ID查询并分页
     * @param userId 用户ID
     * @param pageable 分页
     */
    Page<Device> findByUserId(Integer userId, Pageable pageable);

    /**
     * 根据组ID查询并分页
     * @param groupId 组ID
     * @param pageable 分页
     */
    Page<Device> findByGroupId(Integer groupId, Pageable pageable);
}
