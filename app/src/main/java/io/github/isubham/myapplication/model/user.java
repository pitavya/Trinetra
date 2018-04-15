package io.github.isubham.myapplication.model;

/**
 * Created by suraj on 3/4/18.
 */

public class user {
    public String name, email,  user_id,  user_type;

    public user(String name, String email,  String user_id,  String user_type) {
        this.name = name;
        this.email = email;
        this.user_id = user_id;
        this.user_type = user_type;
    }

    public user(){

    }
}
