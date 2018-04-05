package io.github.isubham.myapplication.model;

/**
 * Created by suraj on 4/4/18.
 */

public class worker {
    public String worker_id, worker_name, worker_aadhar_id,
            worker_type, worker_fingerprint;

    public worker(String worker_id, String worker_name, String worker_aadhar_id, String worker_type, String worker_fingerprint) {
        this.worker_id = worker_id;
        this.worker_name = worker_name;
        this.worker_aadhar_id = worker_aadhar_id;
        this.worker_type = worker_type;
        this.worker_fingerprint = worker_fingerprint;
    }

    public worker() {
    }
}
