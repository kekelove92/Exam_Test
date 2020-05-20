package com.tunganh.exam_android_dev.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tunganh.exam_android_dev.R;
import com.tunganh.exam_android_dev.common.Common;
import com.tunganh.exam_android_dev.fragment.DetaiUserFragment;
import com.tunganh.exam_android_dev.model.DetailModel;
import com.tunganh.exam_android_dev.model.UserModel;

import java.util.List;

public class DetailAdapter  extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<DetailModel> list;
    private Context context;


    public DetailAdapter(List<DetailModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list_user, parent, false);
        return new DetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder holder, int position) {
        DetailModel detailModel = list.get(position);
        holder.userName.setText(detailModel.getLogin());
        Glide.with(context).load(detailModel.getAvatarUrl())
                .into(holder.userImg);
        if (detailModel.getAvatarUrl() == null || detailModel.getAvatarUrl().isEmpty()){
            holder.userImg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView userName;
        ImageView userImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            userName = itemView.findViewById(R.id.tv_user);
            userImg = itemView.findViewById(R.id.img_user);

        }
    }


}
