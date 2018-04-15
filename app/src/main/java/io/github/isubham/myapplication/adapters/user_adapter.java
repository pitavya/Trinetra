package io.github.isubham.myapplication.adapters;

/**
 * Created by suraj on 15/4/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.isubham.myapplication.R;
import io.github.isubham.myapplication.model.user;

public class user_adapter extends RecyclerView.Adapter<user_adapter.user_viewholder> {

    public static class user_viewholder extends RecyclerView.ViewHolder{
        TextView user_name, user_type, user_id,  user_email;
        ImageView rl_user_avatar;

        user_viewholder(View V){
            super(V);
            user_name = (TextView) V.findViewById(R.id. rl_user_name);
            user_type = (TextView) V.findViewById(R.id. rl_user_type);
            user_email = (TextView) V.findViewById(R.id.rl_user_email);
            user_id = (TextView) V.findViewById(R.id.   rl_user_id);
            rl_user_avatar = (ImageView) V.findViewById(R.id. rl_user_avatar);
        }
    }

    public List<user> users;

    public user_adapter(List<user> users) {
        this.users = users;
    }

    @Override
    public int getItemCount(){
        return users.size();
    }


    @Override
    public user_adapter.user_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rl_user, parent, false);

        user_viewholder user_viewholder = new user_viewholder(V);
        return  user_viewholder;
    }

    @Override
    public void onBindViewHolder(user_adapter.user_viewholder holder, int position) {
        holder.user_id.setText(       users.get(position).user_id);
        holder.user_email.setText(    users.get(position).email);
        holder.user_name.setText(     users.get(position).name);
        holder.user_type.setText(     users.get(position).user_type);
        String user_type = users.get(position).user_type;

        switch (user_type) {

            case "1" :
                holder.rl_user_avatar.setImageResource(R.drawable.admin);
                break;

            case "2" :
                holder.rl_user_avatar.setImageResource(R.drawable.contracter);
                break;

            case "3" :
                holder.rl_user_avatar.setImageResource(R.drawable.supervisor);
                break;
        }

    }

}
