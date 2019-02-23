package com.ukjava.mywebmodel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ukjava.mywebmodel.R;
import com.ukjava.mywebmodel.bean.MyBean;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<MyBean.ResultsBean> lists;

    public MyAdapter(Context context,ArrayList<MyBean.ResultsBean> list){
        this.mContext = context;
        this.lists = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyBean.ResultsBean resultsBean = lists.get(position);
        holder.who.setText(resultsBean.getWho());
        holder.desc.setText(resultsBean.getDesc());
        //使用Glide来进行图片加载
        Glide.with(mContext).load(resultsBean.getUrl()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return lists==null ? 0 : lists.size();
    }


    //创建MyViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView who;
        private  TextView desc;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            who = itemView.findViewById(R.id.who);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
