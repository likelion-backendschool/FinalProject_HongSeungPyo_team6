package com.ll.exam.final__2022_10_08.app.cashlog.repository;

import com.ll.exam.final__2022_10_08.app.cashlog.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog,Long> {
}
