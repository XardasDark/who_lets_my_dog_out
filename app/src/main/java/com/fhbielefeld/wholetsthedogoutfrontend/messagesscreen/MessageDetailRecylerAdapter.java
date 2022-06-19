package com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fhbielefeld.wholetsthedogoutfrontend.R;

import java.util.ArrayList;

public class MessageDetailRecylerAdapter extends RecyclerView.Adapter<MessageDetailRecylerAdapter.MyViewHolder> {

    ArrayList<String> messages;
    ArrayList<Boolean> own;
    String user;
    Context context;

    public MessageDetailRecylerAdapter(Context ct,ArrayList<String>messages,ArrayList<Boolean>own,String user){
        this.messages=messages;this.own=own;context=ct; this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1) {
            LayoutInflater infalter = LayoutInflater.from(context);
            View view = infalter.inflate(R.layout.fragment_messages_my, parent, false);
            return new MyViewHolder(view);
        }
        else{
            LayoutInflater infalter = LayoutInflater.from(context);
            View view = infalter.inflate(R.layout.fragment_messages_their, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(own.get(position))return 1;
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(own.get(position)) {
            holder.text.setText(messages.get(position));
        }
        else {
            holder.bodymessage.setText(messages.get(position));
            holder.name.setText(user);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        TextView bodymessage;
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.message_my);
            bodymessage = itemView.findViewById(R.id.message_body);
            name = itemView.findViewById(R.id.name_message);

        }
    }
}
