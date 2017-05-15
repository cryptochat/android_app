package app.cryptochat.com.cryptochat.Activity.ChatActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import app.cryptochat.com.cryptochat.R;

/**
 * Created by amudarisova on 14.05.17.
 */

public class ChatAdapter extends BaseAdapter {
    private ArrayList<ChatViewModel> chatViewModels;
    private LayoutInflater lInflater;
    Context context;

    public ChatAdapter(Context context, ArrayList<ChatViewModel> chatViewModels) {
        this.chatViewModels = chatViewModels;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.chatViewModels.size();

    }

    @Override
    public Object getItem(int position) {
        return this.chatViewModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ChatViewModel chatViewModel = chatViewModels.get(position);

        if (chatViewModel.getFromMe()) {
            view = lInflater.inflate(R.layout.list_item_message_right, null);
        } else {
            view = lInflater.inflate(R.layout.list_item_message_left, null);
        }

        view.setRotation(180);

        TextView textTime = (TextView) view.findViewById(R.id.textViewTime);
        TextView textMessage = (TextView) view.findViewById(R.id.textViewMessage);

        textTime.setText(chatViewModel.getCreatedAt());
        textMessage.setText(chatViewModel.getMessageText());








        return view;
    }
}
