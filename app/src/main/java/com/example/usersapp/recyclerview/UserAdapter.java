package com.example.usersapp.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usersapp.R;
import com.example.usersapp.room.DbUser;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<DbUser> userList;

    public UserAdapter(Context context, List<DbUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        DbUser user = userList.get(position);
        holder.firstNameTextView.setText(user.firstName);
        holder.lastNameTextView.setText(user.lastName);
        holder.countryTextView.setText(user.country);
        holder.cityTextView.setText(user.city);

        Glide.with(context)
                .load(user.picture)
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(holder.userImageView);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView, lastNameTextView, countryTextView, cityTextView;
        ImageView userImageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.firstName);
            lastNameTextView = itemView.findViewById(R.id.lastName);
            countryTextView = itemView.findViewById(R.id.country);
            cityTextView = itemView.findViewById(R.id.city);
            userImageView = itemView.findViewById(R.id.userImage);
        }
    }
}
