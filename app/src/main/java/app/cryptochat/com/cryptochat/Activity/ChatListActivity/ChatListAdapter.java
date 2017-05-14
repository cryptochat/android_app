package app.cryptochat.com.cryptochat.Activity.ChatListActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.R;
import app.cryptochat.com.cryptochat.Tools.Tools;

/**
 * Created by romankov on 12.05.17.
 */

public class ChatListAdapter extends BaseAdapter {

    private ArrayList<ChatListViewModel> chatListViewModels;
    private LayoutInflater lInflater;
    Context context;


    ChatListAdapter(Context context, ArrayList<ChatListViewModel> userModels){
        this.chatListViewModels = userModels;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.chatListViewModels.size();
    }

    @Override
    public Object getItem(int position) {
        return this.chatListViewModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ChatListViewModel chatListViewModel = chatListViewModels.get(position);
        if(view == null){
            view = lInflater.inflate(R.layout.chat_list_user, parent, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if(chatListViewModel.getUrlAvatar() == null) {
            // Image User
            Bitmap userImage = BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_user_default);
            userImage = Tools.getRoundedCornerBitmap(userImage, userImage.getWidth() / 2);
            imageView.setImageBitmap(userImage);
        }else{
            Picasso.with(context)
                    .load(chatListViewModel.getUrlAvatar())
                    .placeholder(R.mipmap.image_user_default)
                    .into(imageView);
        }

        // User Name
        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(chatListViewModel.getName());

        // Last Message
        TextView lastViewMessage = (TextView) view.findViewById(R.id.textViewDescription);
        lastViewMessage.setText(chatListViewModel.getLastMessage());

        // Online
        View viewStatus = (View)view.findViewById(R.id.viewStatus);
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        if(chatListViewModel.isOnline()){
//            drawable.getPaint().setColor(context.getColor(R.color.colorUserOnline));
        }else{
//            drawable.getPaint().setColor(context.getColor(R.color.colorUserOffline));
        }

//        viewStatus.setBackground(drawable);

        return view;
    }
}
