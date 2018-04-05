package io.github.isubham.myapplication.model;

/**
 * Created by suraj on 3/4/18.
 */

public class user {
    public String name, email, password, user_id, aadhar_id, user_type;

    public user(String name, String email, String password, String user_id, String aadhar_id, String user_type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.user_id = user_id;
        this.aadhar_id = aadhar_id;
        this.user_type = user_type;
    }

}
