package com.brainbits.trivia.brainbits;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class FragmentInbox  extends Fragment {

    View view;


    public FragmentInbox() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.inbox_fragment,container,false);

        String[] messages = {"Message 1", "Message 2", "Message 3"};
        ListView messageView = (ListView)view.findViewById(R.id.inbox_feed);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,messages);
        messageView.setAdapter(adapter);
        return view;
    }


}
