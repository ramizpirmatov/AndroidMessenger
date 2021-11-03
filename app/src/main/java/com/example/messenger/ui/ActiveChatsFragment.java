package com.example.messenger.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.MessengerApplication;
import com.example.messenger.R;
import com.example.messenger.adapters.ConversationAdapter;
import com.example.messenger.db.DataBaseController;
import com.example.messenger.handler.ConversationEvent;
import com.example.messenger.model.Conversation;
import com.example.messenger.utils.MessageManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ActiveChatsFragment extends Fragment
{

    private View view;
    private ConversationAdapter adapter;
    private RecyclerView recyclerView;
    private List<Conversation> conversations;
    private static Menu menu;
    private MenuItem removeSelection;

    private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver()
    {
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount)
        {
            ActiveChatsFragment.setDeleteSelectedChatItem(adapter.getCountOfCheckedChats() > 0);

            super.onItemRangeChanged(positionStart, itemCount);
        }
    };


    public ActiveChatsFragment()
    {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        if (!MessengerApplication.getSocket().connected())
        {
            MessengerApplication.getSocket().connect();
            MessageManager.getInstance().init();
        }
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_active_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_chats);

        conversations = DataBaseController.getConversations();
        adapter = new ConversationAdapter(conversations, getContext());
        adapter.registerAdapterDataObserver(observer);

        setRecyclerView();

        return view;
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        MessengerApplication.getSocket().disconnect();
        MessengerApplication.getSocket().off();
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart()
    {
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyConversations(ConversationEvent event)
    {
        for (int i = 0; i < conversations.size(); i++)
        {
            if (conversations.get(i).getId() == event.getConversation().getId())
            {
                conversations.set(i, event.getConversation());
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.toolbar_menu, menu);
        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static void setDeleteSelectedChatItem(boolean visibility)
    {
       menu.findItem(R.id.delete_selected_chats).setVisible(visibility);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (removeSelection == null) removeSelection = menu.findItem(R.id.remove_selection);

        if (item.getItemId() == R.id.select_chat)
        {
            adapter.setSelectEnabled(true);
            removeSelection.setVisible(true);
            adapter.notifyDataSetChanged();
        }
        else if (item.getItemId() == R.id.remove_selection)
        {
            removeSelection.setVisible(false);
            adapter.setSelectEnabled(false);
            for (int i = 0; i < conversations.size(); i++)
            {
                conversations.get(i).setSelected(false);
                if (adapter.getCountOfCheckedChats() > 0)
                {
                    int count = adapter.getCountOfCheckedChats();
                    adapter.setCountOfCheckedChats(--count);
                    adapter.notifyItemChanged(i);
                }
            }

            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView()
    {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
}