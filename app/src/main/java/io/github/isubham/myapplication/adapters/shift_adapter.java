package io.github.isubham.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.isubham.myapplication.R;
import io.github.isubham.myapplication.model.shift;

/**
 * Created by suraj on 5/3/18.
 */

public class shift_adapter extends
            RecyclerView.Adapter<shift_adapter.shift_viewholder> {


    private final List<shift> shifts;

    public shift_adapter(List<shift> shifts){

            this.shifts = shifts;

    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }


    @Override
    public shift_adapter.shift_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.rl_shift, parent, false);

            shift_viewholder project_viewholder = new shift_viewholder(V);
            return project_viewholder;
        }

    @Override
    public void onBindViewHolder(shift_adapter.shift_viewholder holder, int position) {
            holder.shift_id.setText(    shifts.get(position).shift_id);
            holder.shift_datetime.setText(          shifts.get(position).shift_datetime);

            holder.shift_type.setText(          shifts.get(position).shift_type);

    }

    public class shift_viewholder extends RecyclerView.ViewHolder {

        TextView shift_id;
        TextView shift_datetime;
        TextView shift_type;

        public shift_viewholder(View V) {
            super(V);
            shift_id = (TextView) V.findViewById(R.id.rl_shift_shift_id);
            shift_datetime = (TextView) V.findViewById(R.id.rl_shift_shift_datetime);
            shift_type = (TextView) V.findViewById(R.id.rl_shift_shift_type);
        }


    }



}

