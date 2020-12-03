package com.rejointech.tu_du.model;

public class Login_Cred {
    public Login_Cred() {
    }

    private String Username,
            Email_id,
            Uid;

    public Login_Cred(String username, String email_id, String uid) {
        Username = username;
        Email_id = email_id;
        Uid = uid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String email_id) {
        Email_id = email_id;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
