package com.ll.exam.final__2022_10_08.app.cashlog;

import com.ll.exam.final__2022_10_08.app.cashlog.entity.CashLog;
import com.ll.exam.final__2022_10_08.app.cashlog.repository.CashLogRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashLogService {
    private final CashLogRepository cashLogRepository;
    public void addCacheLog(Member member, long cache) {
        CashLog cashLog = CashLog.builder()
                .price(cache)
                .price(cache)
                .eventType("예치금__충전__"+cache+"")
                .build();
        cashLogRepository.save(cashLog);
    }
}
