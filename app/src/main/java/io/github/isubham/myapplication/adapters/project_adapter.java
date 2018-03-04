package io.github.isubham.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.isubham.myapplication.R;
import io.github.isubham.myapplication.model.project;

/**
 * Created by suraj on 4/3/18.
 */


public class project_adapter extends
            RecyclerView.Adapter<project_adapter.project_viewholder> {

        public static class project_viewholder extends RecyclerView.ViewHolder{

            TextView project_name, project_start_date, project_end_date, project_id;

            project_viewholder(View V){
                super(V);
                project_name = (TextView) V.findViewById(R.id.rl_project_name);
                project_start_date = (TextView) V.findViewById(R.id.rl_project_start_date);
                project_end_date = (TextView) V.findViewById(R.id.rl_project_end_date);
                project_id = (TextView) V.findViewById(R.id.rl_project_id);

            }
        }

        public List<project> projects;

        public project_adapter(List<project> projects){

            this.projects = projects;

        }

    @Override
    public int getItemCount() {
        return projects.size();
    }


        @Override
        public project_adapter.project_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.rl_project, parent, false);

            project_viewholder project_viewholder = new project_viewholder(V);
            return project_viewholder;
        }

        @Override
        public void onBindViewHolder(project_adapter.project_viewholder holder, int position) {

            holder.project_name.setText(projects.get(position).project_name);
            holder.project_start_date.setText(projects.get(position).project_start_date);
            holder.project_end_date.setText(projects.get(position).project_end_date);
            holder.project_id.setText(projects.get(position).project_id);

        }
    }

