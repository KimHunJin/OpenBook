package org.team2.unithon.openbook.menu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hkm.slider.Animations.DescriptionAnimation;
import com.hkm.slider.SliderLayout;
import com.hkm.slider.SliderTypes.BaseSliderView;
import com.hkm.slider.SliderTypes.TextSliderView;
import com.hkm.slider.TransformerL;
import com.hkm.slider.Tricks.ViewPagerEx;

import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.items.MainFragmentItem;
import org.team2.unithon.openbook.model.Test;
import org.team2.unithon.openbook.network.RestAPI;
import org.team2.unithon.openbook.utils.DataProvider;
import org.team2.unithon.openbook.utils.NumZeroForm;
import org.team2.unithon.openbook.utils.StaticServerUrl;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailShopPage extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final String TAG = "DetailShopPage";

    private SliderLayout mDemoSlider;
    DataProvider dataProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        Intent it = getIntent();
        int id = it.getExtras().getInt("id");

        Log.e(TAG,id+"");

        initialize();

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        dataProvider = new DataProvider();

        network(id);
    }

    void initialize() {

    }

    void network(int id) {
        Log.e(TAG, "do network");
        Map map = new HashMap();
        map.put("id", id+"");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticServerUrl.URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI connectService = retrofit.create(RestAPI.class);
        Call<Test> call = connectService.getOneInfo(map);
        call.enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                Test test = response.body();
                Log.e(TAG,response.message());
                for(int i=0;i<test.getResult().size();i++) {
                    int id = Integer.parseInt(test.getResult().get(i).getId());
                    String title = test.getResult().get(i).getStore_name();
                    String sub = test.getResult().get(i).getExtra();
                    for(int j=0;j<test.getResult().get(i).getImages().length;j++) {
                        dataProvider.setHashFile((j+1)+"",test.getResult().get(i).getImages()[j]);
                    }
                    String location = test.getResult().get(i).getLocaiton();
                    String openTime = test.getResult().get(i).getOpen_time();
                    String closeTime = test.getResult().get(i).getClose_time();
                    String owner = test.getResult().get(i).getOwner_name();
                    String phone = test.getResult().get(i).getPhone();

                }
                setupSlider();  // 이미지 슬라이드를 불러옵니다.
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    // 이미지를 URL로 가져올 땐 이 부분을 사용합니다.
    // 이미지를 src로 가져오기 위해서는 이 부분을 주석처리 하면 됩니다.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void defaultCompleteSlider(final HashMap<String, String> maps) {
        for (String name : maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .enableSaveImageByLongClick(getFragmentManager())
                    .setOnSliderClickListener(this);
            //add your extra information
            textSliderView.getBundle().putString("extra", name);
            mDemoSlider.addSlider(textSliderView);
        }
    }

    /**
     * 슬라이드를 통해 이미지를 처리하는 매서드입니다.
     */
    @SuppressLint("ResourceAsColor")
    private void setupSlider() {
        // remember setup first
        Log.e("setup", "setup slider");
        mDemoSlider.setPresetTransformer(TransformerL.DepthPage);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.setOffscreenPageLimit(4);
        mDemoSlider.setSliderTransformDuration(400, new LinearOutSlowInInterpolator());
        mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(R.color.colorAccent, R.color.colorBlack);
        final NumZeroForm n = new NumZeroForm(this);
        mDemoSlider.setNumLayout(n);
        mDemoSlider.setPresetTransformer("DepthPage");
        mDemoSlider.stopAutoCycle();

        defaultCompleteSlider(dataProvider.getFileSrcHorizontal());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView coreSlider) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mDemoSlider.stopAutoCycle();
    }
}
