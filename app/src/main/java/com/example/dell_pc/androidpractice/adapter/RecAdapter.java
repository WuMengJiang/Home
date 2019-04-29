package com.example.dell_pc.androidpractice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell_pc.androidpractice.R;
import com.example.dell_pc.androidpractice.bean.User;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    private ArrayList<User.NewslistBean> beans;
    private Context context;
    //武猛将
    public RecAdapter(ArrayList<User.NewslistBean> beans, Context context) {
        this.beans = beans;
        this.context = context;
    }

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rec_item, null);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
    public interface OnClick{
        void onItemClick(int position,User.NewslistBean user);
    }
    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, final int i) {
        final User.NewslistBean u = beans.get(i);
            holder.tv.setText(beans.get(i).getTitle());
        Glide.with(context).load(beans.get(i).getPicUrl()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i,u);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private  ImageView img;
        private  TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
