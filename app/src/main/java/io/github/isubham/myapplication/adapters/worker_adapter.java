package io.github.isubham.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.isubham.myapplication.R;
import io.github.isubham.myapplication.model.worker;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by suraj on 4/4/18.
 */

public class worker_adapter extends RecyclerView.Adapter<worker_adapter.worker_viewholder> {

    public static class worker_viewholder extends RecyclerView.ViewHolder{
        TextView worker_name, worker_type, worker_id,  worker_aadhar_id;

        worker_viewholder(View V){
            super(V);
            worker_name = (TextView) V.findViewById(R.id.rl_worker_name);
            worker_type = (TextView) V.findViewById(R.id.rl_worker_type);
            worker_aadhar_id = (TextView) V.findViewById(R.id.rl_worker_aadhar_id);
            worker_id = (TextView) V.findViewById(R.id.rl_worker_id);
        }
    }

    public List<worker> workers;

    public worker_adapter(List<worker> workers) {
        this.workers = workers;
    }

    @Override
    public int getItemCount(){
        return workers.size();
    }


    @Override
    public worker_adapter.worker_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rl_worker, parent, false);

        worker_viewholder worker_viewholder = new worker_viewholder(V);
        return  worker_viewholder;
    }

    @Override
    public void onBindViewHolder(worker_adapter.worker_viewholder holder, int position) {
        holder.worker_id.setText(workers.get(position).worker_id);
        holder.worker_aadhar_id.setText(workers.get(position).worker_aadhar_id);
        holder.worker_name.setText(workers.get(position).worker_name);
        holder.worker_type.setText(workers.get(position).worker_type);
    }

}
