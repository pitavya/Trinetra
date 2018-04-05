package io.github.isubham.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.isubham.myapplication.R;
import io.github.isubham.myapplication.model.auth;


/**
 * Created by suraj on 4/4/18.
 */

public class auth_adapter extends
            RecyclerView.Adapter<auth_adapter.auth_viewholder> {

        public static class auth_viewholder extends RecyclerView.ViewHolder{

            TextView auth_id, gen_worker_id, status;

            auth_viewholder(View V){
                super(V);

                auth_id = (TextView)V.findViewById(R.id.rl_auth_id);
                gen_worker_id = (TextView)V.findViewById(R.id.rl_gen_worker_id);
                status = (TextView)V.findViewById(R.id.rl_status);

            }
        }

        public List<auth> auths;

        public auth_adapter(List<auth> auths){

            this.auths = auths;

        }

    @Override
    public int getItemCount() {
        return auths.size();
    }


        @Override
        public auth_adapter.auth_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.rl_auth, parent, false);

            auth_viewholder auth_viewholder = new auth_viewholder(V);
            return auth_viewholder;
        }

        @Override
        public void onBindViewHolder(auth_adapter.auth_viewholder holder, int position) {

            holder.auth_id.setText(auths.get(position).authentication_id);
            holder.gen_worker_id.setText(auths.get(position).gen_worker_id);
            holder.status.setText(auths.get(position).status);

        }

    }

