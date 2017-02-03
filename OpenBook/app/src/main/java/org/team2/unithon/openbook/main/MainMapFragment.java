package org.team2.unithon.openbook.main;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;

import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.utils.NMapFragment;
import org.team2.unithon.openbook.utils.NaverKey;

import static com.nhn.android.naverlogin.OAuthLoginDefine.LOG_TAG;
import static org.team2.unithon.openbook.BuildConfig.DEBUG;

public class MainMapFragment extends NMapFragment {
    private NMapContext mMapContext;
    private String CLIENT_ID = NaverKey.CLIENT_ID;

    private LocationManager locationManager;

    private NMapController mMapController;
    private NMapView mMapView;

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

        mMapView.setBuiltInZoomControls(true,lp);
        mMapContext.setupMapView(mMapView);
        getLocation();
    }

    /**
     * 현재 위치를 10초마다 받아옵니다.
     * 로케이션이 변경될 경우 이후의 이벤트를 처리합니다.
     */
    void getLocation() {
        locationManager = (LocationManager) mView.getContext().getSystemService(getContext().LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000, // 초
                    1,
                    mLocationListener
            );
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10000,
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
            NGeoPoint nGeoPoint = new NGeoPoint(longitude,latitude);
            mMapController.setMapCenter(nGeoPoint,14); // 10초마다 현재 위치로 이동
//            NMapOverlayItem nMapOverlayItem = new NMapOverlayItem(nGeoPoint,"you","current location",getResources().getDrawable(R.mipmap.ic_launcher));

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

}