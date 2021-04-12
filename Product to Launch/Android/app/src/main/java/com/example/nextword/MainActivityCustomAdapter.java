package com.example.nextword;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivityCustomAdapter extends RecyclerView.Adapter<MainActivityCustomAdapter.ViewHolder> {


      ArrayList<Info> map;


    public MainActivityCustomAdapter(ArrayList<Info> map) {
        this.map = map;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.heading.setText(map.get(position).getHeading());
        holder.text.setText(map.get(position).getDescription());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),ViewheadingActivity.class);
                i.putExtra("heading",map.get(position).getHeading());
                i.putExtra("description",map.get(position).getDescription());
                v.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView heading, text;
        ConstraintLayout view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.headingi);
            text = itemView.findViewById(R.id.textdes);
            view = itemView.findViewById(R.id.viewId);

        }
    }
}
