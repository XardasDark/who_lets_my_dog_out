package com.fhbielefeld.wholetsthedogoutfrontend.messagesscreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fhbielefeld.wholetsthedogoutfrontend.MainActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.searchscreen.RecylerViewAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MessageDetailRecylerAdapter extends RecyclerView.Adapter<MessageDetailRecylerAdapter.MyViewHolder> {

    ArrayList<String> messages;
    ArrayList<Boolean> own;
    String user,picture;
    Context context;

    public MessageDetailRecylerAdapter(Context ct,ArrayList<String>messages,ArrayList<Boolean>own,String user,String picture){
        this.messages=messages;this.own=own;context=ct; this.user = user;this.picture=picture;
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
            new DownloadImageTask( holder.image).execute(MainActivity.picture);
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
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.message_my);
            bodymessage = itemView.findViewById(R.id.message_body);
            name = itemView.findViewById(R.id.name_message);
            image = itemView.findViewById(R.id.avatar);
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            InputStream in = null;
            try {
                in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
