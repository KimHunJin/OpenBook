package org.team2.unithon.openbook.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.naverlogin.OAuthLogin;

import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.adapters.MainRecyclerViewAdapter;
import org.team2.unithon.openbook.items.MainFragmentItem;
import org.team2.unithon.openbook.menu.DetailShopPage;
import org.team2.unithon.openbook.model.GPSPost;
import org.team2.unithon.openbook.model.Test;
import org.team2.unithon.openbook.network.RestAPI;
import org.team2.unithon.openbook.utils.RecyclerViewOnItemClickListener;
import org.team2.unithon.openbook.utils.StaticServerUrl;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        mView = inflater.inflate(R.layout.fragment_main, container, false);

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

        onClick();
    }

    void addData() {
        network(OAuthLogin.getInstance().getAccessToken(getContext()));

    }

    void network(String token) {

        Map map = new HashMap();
        map.put("token", token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticServerUrl.URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI connectService = retrofit.create(RestAPI.class);
        Call<Test> call = connectService.getImages(map);
        call.enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                Test test = response.body();
                for(int i=0;i<test.getResult().size();i++) {
                    int id = Integer.parseInt(test.getResult().get(i).getId());
                    String image = test.getResult().get(i).getImages()[0];
                    String title = test.getResult().get(i).getStore_name();
                    String sub = test.getResult().get(i).getExtra();
                    mainRecyclerViewAdapter.addData(new MainFragmentItem(id,image,title,sub));
                }

                mainRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    void onClick() {
        rcvMainContents.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(), rcvMainContents, new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent it = new Intent(getActivity(), DetailShopPage.class);
                int id = mainRecyclerViewAdapter.getItems().get(position).getmNumber();
                it.putExtra("id",id);
                startActivity(it);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        }));
    }

}
