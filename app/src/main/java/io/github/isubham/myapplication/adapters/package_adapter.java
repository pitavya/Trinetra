package io.github.isubham.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.isubham.myapplication.R;
import io.github.isubham.myapplication.model.package_;

/**
 * Created by suraj on 3/4/18.
 */

public class package_adapter extends
            RecyclerView.Adapter<package_adapter.package_viewholder> {

        public static class package_viewholder extends RecyclerView.ViewHolder{

            TextView package_name, package_start_date, package_end_date, package_id;

            package_viewholder(View V){
                super(V);
                package_name = (TextView) V.findViewById(R.id.rl_package_name);
                package_start_date = (TextView) V.findViewById(R.id.rl_package_start_date);
                package_end_date = (TextView) V.findViewById(R.id.rl_package_end_date);
                package_id = (TextView) V.findViewById(R.id.rl_package_id);

            }
        }

        public List<package_> packages;

        public package_adapter(List<package_> packages){

            this.packages = packages;

        }

    @Override
    public int getItemCount() {
        return packages.size();
    }


        @Override
        public package_adapter.package_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.rl_package, parent, false);

            package_viewholder package_viewholder = new package_viewholder(V);
            return package_viewholder;
        }

        @Override
        public void onBindViewHolder(package_adapter.package_viewholder holder, int position) {

            holder.package_name.setText(packages.get(position).package_name);
            holder.package_start_date.setText(packages.get(position).package_start_date);
            holder.package_end_date.setText(packages.get(position).package_end_date);
            holder.package_id.setText(packages.get(position).package_id);

        }

    }

