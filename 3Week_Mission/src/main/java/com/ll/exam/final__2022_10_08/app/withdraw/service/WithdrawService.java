package com.ll.exam.final__2022_10_08.app.withdraw.service;

import com.ll.exam.final__2022_10_08.app.base.dto.RsData;
import com.ll.exam.final__2022_10_08.app.base.exception.InsufficientCashException;
import com.ll.exam.final__2022_10_08.app.cash.entity.CashLog;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.withdraw.entity.Withdraw;
import com.ll.exam.final__2022_10_08.app.withdraw.repository.WithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WithdrawService {
    private final MemberService memberService;
    private final WithdrawRepository withdrawRepository;
    @Transactional
    public void apply(Member member, String bankName, String bankAccountNo, long price) {

        long restCash = memberService.getRestCash(member);
        // 예치금보다 출금 금액이 더 많을 때
        if (restCash < price){
            throw new InsufficientCashException();
        }

        CashLog cashLog = memberService.addCash(member, -1 * (price), "출금__계좌번호__%s__%s".formatted(bankAccountNo, bankName))
                .getData().getCashLog();
        Withdraw withdraw = Withdraw.builder()
                .bankName(bankName)
                .bankAccountNo(bankAccountNo)
                .price(price)
                .cashLog(cashLog)
                .build();
        withdrawRepository.save(withdraw);
    }
}
