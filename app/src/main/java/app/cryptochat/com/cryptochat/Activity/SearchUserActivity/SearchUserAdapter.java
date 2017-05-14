package app.cryptochat.com.cryptochat.Activity.SearchUserActivity;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.R;

/**
 * Created by romankov on 13.05.17.
 */

public class SearchUserAdapter extends BaseAdapter {

    private ArrayList<UserViewModels> userViewModelses;
    private LayoutInflater layoutInflater;

    SearchUserAdapter(Context context, ArrayList<UserViewModels> userModels){
        this.userViewModelses = userModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return view;
    }
}
