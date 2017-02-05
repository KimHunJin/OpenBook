package org.team2.unithon.openbook.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import org.team2.unithon.openbook.MainActivity;
import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.adapters.MainMapRecyclerViewAdapter;
import org.team2.unithon.openbook.items.MainMapFragmentItem;
import org.team2.unithon.openbook.menu.DetailShopPage;
import org.team2.unithon.openbook.model.Test;
import org.team2.unithon.openbook.network.RestAPI;
import org.team2.unithon.openbook.utils.NMapFragment;
import org.team2.unithon.openbook.utils.NMapViewerResourceProvider;
import org.team2.unithon.openbook.utils.NaverKey;
import org.team2.unithon.openbook.utils.RecyclerViewOnItemClickListener;
import org.team2.unithon.openbook.utils.StaticServerUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.pathData;
import static com.nhn.android.naverlogin.OAuthLoginDefine.LOG_TAG;
import static org.team2.unithon.openbook.BuildConfig.DEBUG;

public class MainMapFragment extends NMapFragment {

    private static final int REQUEST_CODE_PERMISSION = 2;

    private NMapContext mMapContext;
    private String CLIENT_ID = NaverKey.CLIENT_ID;

    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mMapOverlayManager;

    private static final String TAG = "MainMapFragment";

    private LocationManager locationManager;

    private NMapController mMapController;
    private NMapView mMapView;
    private NGeoPoint nGeoPoint;

    private RecyclerView rcvMainMap;
    private MainMapRecyclerViewAdapter mainMapRecyclerViewAdapter;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_map, container, false);
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (NMapView) getView().findViewById(R.id.mapView);
        mMapView.setClientId(CLIENT_ID);// 클라이언트 아이디 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapController = mMapView.getMapController();
        NMapView.LayoutParams lp = new NMapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT);

        mMapView.setBuiltInZoomControls(true, lp);
        mMapContext.setupMapView(mMapView);
        mMapViewerResourceProvider = new NMapViewerResourceProvider(getContext());
        mMapOverlayManager = new NMapOverlayManager(getContext(), mMapView, mMapViewerResourceProvider);

        rcvMainMap = (RecyclerView)mView.findViewById(R.id.rcv_main_map);
        rcvMainMap.setHasFixedSize(true);
        mainMapRecyclerViewAdapter = new MainMapRecyclerViewAdapter(mView.getContext());
        rcvMainMap.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        rcvMainMap.setAdapter(mainMapRecyclerViewAdapter);

        checkPermission();
//        new TedPermission(getContext())
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
//                .check();


    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            Log.e(TAG, "permission deny");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Log.e(TAG,"OnRequestPermission");

                    getLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * 현재 위치를 10초마다 받아옵니다.
     * 로케이션이 변경될 경우 이후의 이벤트를 처리합니다.
     */
    void getLocation() {
        locationManager = (LocationManager) mView.getContext().getSystemService(getContext().LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, // 초
                    1,
                    mLocationListener
            );
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    mLocationListener
            );
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    /**
     * 로케이션이 변경되었을 경우입니다.
     * 위치 값을 가져와 현재 위치를 보여줍니다.
     */
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
//            double altitude = location.getAltitude();   //고도
//            float accuracy = location.getAccuracy();    //정확도
//            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            nGeoPoint = new NGeoPoint(longitude, latitude);
//            Log.e(TAG, nGeoPoint.getLatitude() + " " + nGeoPoint.getLongitude());
            mMapController.setMapCenter(nGeoPoint, 14); // 10초마다 현재 위치로 이동

            network(nGeoPoint.getLongitude() + "", nGeoPoint.getLatitude() + "");

//            NMapOverlayItem nMapOverlayItem = new NMapOverlayItem(nGeoPoint,"you","current location",getResources().getDrawable(R.mipmap.ic_launcher));

//            NMapOverlayItem nMapOverlayItem = new NMapOverlayItem(nGeoPoint,"you","current location",getResources().getDrawable(R.mipmap.ic_launcher));

            // put gps to server

            rcvMainMap.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(), rcvMainMap, new RecyclerViewOnItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    int id = mainMapRecyclerViewAdapter.getItems().get(position).getmNumber();
                    Intent it = new Intent(getActivity(), DetailShopPage.class);
                    it.putExtra("id",id);
                    startActivity(it);
                }

                @Override
                public void onItemLongClick(View v, int position) {

                }
            }));
        }

        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    /* MapView State Change Listener*/
    private final NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {

        @Override
        public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {

            if (errorInfo == null) { // success
                // restore map view state such as map center position and zoom level.

            } else { // fail
                Log.e(LOG_TAG, "onFailedToInitializeWithError: " + errorInfo.toString());

                Toast.makeText(getContext(), errorInfo.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onAnimationStateChange(NMapView mapView, int animType, int animState) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onAnimationStateChange: animType=" + animType + ", animState=" + animState);
            }
        }

        @Override
        public void onMapCenterChange(NMapView mapView, NGeoPoint center) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onMapCenterChange: center=" + center.toString());
            }
        }

        @Override
        public void onZoomLevelChange(NMapView mapView, int level) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onZoomLevelChange: level=" + level);
            }
        }

        @Override
        public void onMapCenterChangeFine(NMapView mapView) {

        }
    };

    private final NMapView.OnMapViewTouchEventListener onMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {

        @Override
        public void onLongPress(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLongPressCanceled(NMapView mapView) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSingleTapUp(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTouchDown(NMapView mapView, MotionEvent ev) {

        }

        @Override
        public void onScroll(NMapView mapView, MotionEvent e1, MotionEvent e2) {
        }

        @Override
        public void onTouchUp(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

    };


    void network(String x, String y) {
        Map map = new HashMap();
        map.put("x", x);
        map.put("y", y);

//        NMapPathData poIdata = new NMapPathData(5);


//        poIdata.initPathData();

//        poIdata.beginPOIdata(5);
//        poIdata.addPOIitem(nGeoPoint, "you", getResources().getDrawable(R.mipmap.ic_launcher), 5);


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

                NMapPOIdata poIdata = new NMapPOIdata(test.getResult().size(),mMapViewerResourceProvider,true);
                poIdata.beginPOIdata(test.getResult().size());

                Log.e(TAG,test.getResult().size()+"");

                double x = 0;
                double y = 0;
                String text = "";
                for(int i=0;i<test.getResult().size();i++) {
                    x = Double.parseDouble(test.getResult().get(i).getX());
                    y = Double.parseDouble(test.getResult().get(i).getY());
                    text = test.getResult().get(i).getStore_name();
                    String location = "";
                    if(test.getResult().get(i).getLocaiton()==null) {
                        location = "";
                    } else {
                        location = test.getResult().get(i).getLocaiton();
                    }
                    int id = Integer.parseInt(test.getResult().get(i).getId());

                    Log.e(TAG,id+ " : " + text + " : " + location);
                    mainMapRecyclerViewAdapter.addData(new MainMapFragmentItem(id,text,location));
                    Log.e(TAG,x+" " + y);
                    poIdata.addPOIitem(x,y,text,getResources().getDrawable(R.drawable.ic_pin_01),null);
                    for(int j=0;j<test.getResult().get(i).getImages().length;j++) {
                        Log.e(TAG,test.getResult().get(i).getImages()[j]);
                    }
                }

                mainMapRecyclerViewAdapter.notifyDataSetChanged();
                NMapPOIdataOverlay poIdataOverlay = mMapOverlayManager.createPOIdataOverlay(poIdata,null);
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}