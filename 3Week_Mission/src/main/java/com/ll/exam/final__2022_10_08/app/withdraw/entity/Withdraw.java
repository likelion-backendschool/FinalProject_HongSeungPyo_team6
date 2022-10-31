package com.ll.exam.final__2022_10_08.app.withdraw.entity;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Withdraw extends BaseEntity {
    private String bankName;
    private String bankAccountNo;
    private long price;
}
