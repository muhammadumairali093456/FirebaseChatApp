package com.example.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.firebasechatapp.Adapter.UserAdapter;
import com.example.firebasechatapp.Model.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    //Display users in a recycler view

    List<UserInformation> userDataList;
    RecyclerView userListRecyclerView;
    DatabaseReference userRef;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        fetchUserDataFromDB();

        userListRecyclerView = (RecyclerView) findViewById(R.id.userList);
        userListRecyclerView.setHasFixedSize(true);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //we need 2 parameters for this one is context and other is user list we fetch data from db
              //we need to create an adapter to display data into recyclerview first we create single layout for list item

    }

    private void fetchUserDataFromDB() {
        userRef = FirebaseDatabase.getInstance().getReference("ChatUsers");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userDataList = new ArrayList<>();

                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    //if my uid is not equal to fetch user id then save otherwise nothing to save
                    if(!mAuth.getCurrentUser().getUid().equals(snapshot1.getKey()))
                    {
                        UserInformation currentUser = snapshot1.getValue(UserInformation.class);
                        //Now add to our list

                        currentUser.setKey(snapshot1.getKey());

                        userDataList.add(currentUser);
                    }
                }
                UserAdapter adapter = new UserAdapter(Home.this,userDataList );
                userListRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged(); //any changes occurs update recycler view
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //on

    }
}