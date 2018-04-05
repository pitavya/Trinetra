package io.github.isubham.myapplication.model;

/**
 * Created by suraj on 4/4/18.
 */

public class auth {

    public String authentication_id, gen_worker_id, status;

    public auth(String authentication_id, String gen_worker_id, String status) {
        this.authentication_id = authentication_id;
        this.gen_worker_id = gen_worker_id;
        this.status = status;
    }


    public auth() {
    }
}
