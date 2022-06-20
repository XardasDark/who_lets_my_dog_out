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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.fhbielefeld.wholetsthedogoutfrontend.MainActivity;
import com.fhbielefeld.wholetsthedogoutfrontend.R;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIClient;
import com.fhbielefeld.wholetsthedogoutfrontend.api.APIInterface;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.GetUsersModel;
import com.fhbielefeld.wholetsthedogoutfrontend.api.models.SendMessageModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.MyViewHolder> {

    ArrayList<String> usernameData;
    ArrayList<String> lastMessageData;
    ArrayList<String> userAvatar;
    ArrayList<String> dateData;
    Context context;

    public MessagesRecyclerAdapter(Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> image, ArrayList<String> date) {
        context = ct;
        usernameData = s1;
        lastMessageData = s2;
        userAvatar = image;
        dateData = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_messages_overview_box, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText(usernameData.get(position));
        holder.message.setText(lastMessageData.get(position));
        holder.date.setText(dateData.get(position));


        new DownloadImageTask(holder.image).execute(userAvatar.get(position));
        String user = usernameData.get(position);
        String avatar = userAvatar.get(position);

        holder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MainActivity.targetUser = user;
                MainActivity.picture = avatar;
                Navigation.findNavController(v).navigate(R.id.show_chat_detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usernameData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //private final List<PlaceholderContent.PlaceholderItem> mValues;
        //private final View mItemDetailFragmentContainer;

        TextView username, message, date;
        ImageView image;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.messagerOverviewUserName);
            message = itemView.findViewById(R.id.messagerOverviewUserLastMessage);
            image = itemView.findViewById(R.id.messagerOverviewAvatar);
            layout = itemView.findViewById(R.id.messagerOverviewLayout);
            date = itemView.findViewById(R.id.messagerOverviewDateTime);

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
