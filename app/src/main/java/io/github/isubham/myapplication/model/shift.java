package io.github.isubham.myapplication.model;

/**
 * Created by suraj on 5/3/18.
 */

public class shift {

    public String shift_id, shift_description, shift_datetime, contractor_id, contractor_name;

    public shift(String shift_id, String shift_description, String shift_datetime, String contractor_id, String contractor_name) {
        this.shift_id = shift_id;
        this.shift_description = shift_description;
        this.shift_datetime = shift_datetime;
        this.contractor_id = contractor_id;
        this.contractor_name = contractor_name;
    }

    public shift() {

    }
}
