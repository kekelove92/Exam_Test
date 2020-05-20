package com.tunganh.exam_android_dev.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tunganh.exam_android_dev.model.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<UserModel> list;
    private Context context;
    private String url;
    private String followers_url;


    public UserAdapter(List<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_list_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = list.get(position);
        holder.userName.setText(userModel.getLogin());
        Glide.with(context).load(userModel.getAvatarUrl())
                .into(holder.userImg);
        if (userModel.getAvatarUrl() == null || userModel.getAvatarUrl().isEmpty()){
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION){
                        UserModel userModel = list.get(pos);
                        url = userModel.getUrl();
                        followers_url = userModel.getFollowersUrl();
                        openNextFragment();
                    }
                }
            });
        }
    }

    private void openNextFragment(){
        Bundle bundle = new Bundle();
        bundle.putString(Common.url, url);
        bundle.putString(Common.followers_url, followers_url);
        Fragment fragment = new DetaiUserFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
