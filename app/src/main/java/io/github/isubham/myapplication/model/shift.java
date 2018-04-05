package io.github.isubham.myapplication.model;

/**
 * Created by suraj on 5/3/18.
 */

public class shift {

    public String shift_id,  shift_datetime, shift_type ;

    public shift(String shift_id, String shift_datetime, String shift_type) {
        this.shift_id = shift_id;
        this.shift_datetime = shift_datetime;
        this.shift_type = shift_type;
    }

    public shift() {

    }
}
