package com.jit.imhi.model;

import java.util.Date;

public class SMSCode {

    private String  code;
    private Date date;

    public SMSCode(String phone, Date date) {
        this.code = phone;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
