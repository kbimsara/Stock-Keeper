package com.example.stockkeep;

public class DataModel {
    private final String name,email,pw,item,date,qt;

    public DataModel(String name, String email, String pw, String item, String date, String qt) {
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.item = item;
        this.date = date;
        this.qt = qt;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPw() {
        return pw;
    }

    public String getItem() {
        return item;
    }

    public String getDate() {
        return date;
    }

    public String getQt() {
        return qt;
    }
}
