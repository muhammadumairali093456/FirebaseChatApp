package com.example.firebasechatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechatapp.ChatActivity;
import com.example.firebasechatapp.Model.UserInformation;
import com.example.firebasechatapp.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    List<UserInformation>  userInformationList;

    public UserAdapter(Context context, List<UserInformation> userInformationList) {
        this.context = context;
        this.userInformationList = userInformationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.single_user_layout,parent,false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInformation currentUser =  userInformationList.get(position);
        holder.userName.setText(userInformationList.get(position).getName());
        holder.userEmail.setText(userInformationList.get(position).getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(context, ChatActivity.class);
                chatIntent.putExtra("userInfo", currentUser);
                context.startActivity(chatIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userInformationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName,userEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.userName);
            userEmail = (TextView) itemView.findViewById(R.id.userEmail);

        }
    }
}
