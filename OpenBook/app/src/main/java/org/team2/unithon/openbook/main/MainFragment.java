package org.team2.unithon.openbook.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.adapters.MainRecyclerViewAdapter;
import org.team2.unithon.openbook.items.MainFragmentItem;

/**
 * Created by HunJin on 2017-02-04.
 */

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private View mView;
    private RecyclerView rcvMainContents;
    private MainRecyclerViewAdapter mainRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //inflater를 통해 xml을 가져온다.
        mView = inflater.inflate(R.layout.frame_main, container, false);

        initialize();

        return mView;
    }

    void initialize() {
        rcvMainContents = (RecyclerView) mView.findViewById(R.id.rcv_main_contents);
        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(mView.getContext());
        rcvMainContents.setHasFixedSize(true);
        rcvMainContents.setLayoutManager(new GridLayoutManager(mView.getContext(),2));
        addData();
        rcvMainContents.setAdapter(mainRecyclerViewAdapter);
    }

    void addData() {
        mainRecyclerViewAdapter.addData(new MainFragmentItem(0,"",""));
        mainRecyclerViewAdapter.addData(new MainFragmentItem(1,"","A"));
        mainRecyclerViewAdapter.addData(new MainFragmentItem(2,"","B"));
        mainRecyclerViewAdapter.addData(new MainFragmentItem(3,"","C"));
        mainRecyclerViewAdapter.addData(new MainFragmentItem(4,"","D"));
        mainRecyclerViewAdapter.addData(new MainFragmentItem(5,"","E"));
    }
}
