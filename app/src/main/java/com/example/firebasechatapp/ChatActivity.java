package com.example.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasechatapp.Adapter.ChatAdapter;
import com.example.firebasechatapp.Model.Message;
import com.example.firebasechatapp.Model.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    UserInformation selectedUser;
    RecyclerView chatListRecyclerView;

    Button sendButton;
    DatabaseReference chatRef;

    EditText chatEt;

    List<Message> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        chatRef = FirebaseDatabase.getInstance().getReference("ChatMessage");

        if(getIntent() != null)
        {
           selectedUser = getIntent().getParcelableExtra("userInfo");
           getSupportActionBar().setTitle(selectedUser.getName());
            //here we fetch data according to user
            fetchMessages();
        }

        chatListRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
        chatListRecyclerView.setHasFixedSize(true);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //now first we send chat data to firebase
        chatEt = (EditText) findViewById(R.id.messageEt);
        sendButton = (Button) findViewById(R.id.sendBtn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToFirebase();
                fetchMessages();
            }
        });


    }

    private void fetchMessages() {
        chatRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(selectedUser.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList = new ArrayList<>();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Message currentMessage = snapshot1.getValue(Message.class);
                    chatList.add(currentMessage);
                }
                //here we send data to adapter
                ChatAdapter  adapter = new ChatAdapter(ChatActivity.this,chatList);
                chatListRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessageToFirebase()
    {
        Message send = new Message(chatEt.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(),selectedUser.getKey(),"sender");
        chatRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(selectedUser.getKey()).push().setValue(send);
        Message receiver = new Message(chatEt.getText().toString(),selectedUser.getKey(),FirebaseAuth.getInstance().getCurrentUser().getUid(),"receiver");
        chatRef.child(selectedUser.getKey()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(receiver);

        chatEt.setText("");
    }
}