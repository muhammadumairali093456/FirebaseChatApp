package com.example.firebasechatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechatapp.Model.Message;
import com.example.firebasechatapp.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    List<Message> chatMessage;

    public ChatAdapter(Context context, List<Message> chatMessage) {
        this.context = context;
        this.chatMessage = chatMessage;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_chat_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Message currentMessage = chatMessage.get(position);
        if(currentMessage.getType().equals("sender")){
            holder.rightTV.setText(currentMessage.getContent());
            holder.leftTV.setText(currentMessage.getContent());
            holder.rightTV.setVisibility(View.VISIBLE);
            holder.leftTV.setVisibility(View.GONE);
            holder.cardright.setVisibility(View.VISIBLE);
            holder.cardleft.setVisibility(View.GONE);
        }
        else
        {
            holder.rightTV.setText(currentMessage.getContent());
            holder.leftTV.setText(currentMessage.getContent());
            holder.rightTV.setVisibility(View.GONE);
            holder.leftTV.setVisibility(View.VISIBLE);
            holder.cardright.setVisibility(View.GONE);
            holder.cardleft.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       CardView cardleft , cardright;
        TextView leftTV,rightTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardleft = (CardView)itemView.findViewById(R.id.cardLeft);
            cardright = (CardView)itemView.findViewById(R.id.cardRight);
            leftTV = (TextView) itemView.findViewById(R.id.leftTV);
            rightTV = (TextView) itemView.findViewById(R.id.rightTV);

        }
    }
}
