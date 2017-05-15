package app.cryptochat.com.cryptochat.Activity.ChatActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import app.cryptochat.com.cryptochat.R;

/**
 * Created by amudarisova on 14.05.17.
 */

public class ChatAdapter extends BaseAdapter {
    private ArrayList<ChatViewModel> chatViewList;
    private LayoutInflater lInflater;
    Context context;

    public ChatAdapter(Context context, ArrayList<ChatViewModel> chatViewList) {
        this.chatViewList = chatViewList;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.chatViewList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.chatViewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ChatViewModel chatViewModel = chatViewList.get(position);

        if (chatViewModel.getFromMe()) {
            view = lInflater.inflate(R.layout.list_item_message_right, null);
        } else {
            view = lInflater.inflate(R.layout.list_item_message_left, null);
        }

        view.setRotation(180);

        TextView textTime = (TextView) view.findViewById(R.id.textViewTime);
        TextView textMessage = (TextView) view.findViewById(R.id.textViewMessage);
        textMessage.setText(chatViewModel.getMessageText());

        String createdAtTime = null;
        if(chatViewModel.getCreatedAt() == null) {
            Long currentTime = System.currentTimeMillis()/1000;
            String mTime = currentTime.toString();
            try {
                createdAtTime = chatViewModel.convertToDate(mTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            createdAtTime = chatViewModel.getCreatedAt();
        }

        textTime.setText(createdAtTime);

        return view;
    }

    public void add(ChatViewModel model) {
        chatViewList.add(0, model);
    }
}
