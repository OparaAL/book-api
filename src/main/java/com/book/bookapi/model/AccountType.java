package com.book.bookapi.model;

public enum AccountType {
    APPLICATION("application"),
    GOOGLE("google"),
    GITHUB("github"),
    FACEBOOK("facebook");


    private String value;

    AccountType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static AccountType fromString(String text) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.value.equalsIgnoreCase(text)) {
                return accountType;
            }
        }
        return null;
    }
}
