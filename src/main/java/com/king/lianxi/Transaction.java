package com.king.lianxi;

import lombok.Data;

import java.time.LocalDate;

/**
 * Created by ljq on 2020-10-28 17:19
 */
@Data
public class Transaction {

    final double amount;
    final LocalDate when;

    public Transaction(double amount, String when) {
        this.amount = amount;
        this.when = LocalDate.parse(when);
    }

    public Transaction(double amount) {
        this.amount = amount;
        this.when = null;
    }

}
