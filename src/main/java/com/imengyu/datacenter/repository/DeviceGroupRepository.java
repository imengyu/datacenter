package com.imengyu.datacenter.repository;

import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.entity.DeviceGroup;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Integer>, JpaSpecificationExecutor<DeviceGroup> {

    /**
     * 根据用户ID查询并分页
     * @param userId 用户ID
     * @param pageable 分页
     */
    Page<Device> findByUserId(Integer userId, Pageable pageable);
    /**
     * 根据用户ID查询并搜索分页
     * @param userId 用户ID
     * @param pageable 分页
     */
    Page<Device> findByUserId(Example<DeviceGroup> s, Integer userId, Pageable pageable);


}
