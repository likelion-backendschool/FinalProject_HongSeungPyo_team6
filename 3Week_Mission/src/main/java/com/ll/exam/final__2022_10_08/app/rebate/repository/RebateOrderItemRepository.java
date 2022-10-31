package com.ll.exam.final__2022_10_08.app.rebate.repository;

import com.ll.exam.final__2022_10_08.app.rebate.entity.RebateOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RebateOrderItemRepository extends JpaRepository<RebateOrderItem,Long> {
    RebateOrderItem findByOrderItemId(Long orderItemId);
    List<RebateOrderItem> findAllByRebateAvailable(boolean rebateAvailable);
}
