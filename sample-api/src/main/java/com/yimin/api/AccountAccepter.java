package com.yimin.api;

/**
 * @author yimin
 */
public class AccountAccepter {
    public String acceptAccount(Account waterAccounts) {
        return "Declarative Programming language";
    }

    public String acceptAccount(WaterAccount waterAccounts) {
        return "Water Fee";
    }

    public String acceptAccount(ElectricityAccount electricityAccounts) {
        return "Electricity Fee";
    }
}
