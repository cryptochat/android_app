package app.cryptochat.com.cryptochat.Activity.SearchUserActivity;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.R;
import app.cryptochat.com.cryptochat.Tools.Tools;

/**
 * Created by romankov on 13.05.17.
 */

public class SearchUserAdapter extends BaseAdapter {

    private ArrayList<UserViewModels> userViewModelses;
    private LayoutInflater layoutInflater;
    private Context context;

    SearchUserAdapter(Context context, ArrayList<UserViewModels> userModels){
        this.userViewModelses = userModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return userViewModelses.size();
    }

    @Override
    public Object getItem(int position) {
        return userViewModelses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.user_list_search, parent, false);
        }
        UserViewModels userViewModels = userViewModelses.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if(userViewModels.getUrlAvatar() == null) {
            // Image User
            Bitmap userImage = BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_user_default);
            userImage = Tools.getRoundedCornerBitmap(userImage, userImage.getWidth() / 2);
            imageView.setImageBitmap(userImage);
        }else{
            Picasso.with(context)
                    .load(userViewModels.getUrlAvatar())
                    .placeholder(R.mipmap.image_user_default)
                    .into(imageView);
        }
        // User Name
        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(userViewModels.getFullName());

        return view;
    }
}
