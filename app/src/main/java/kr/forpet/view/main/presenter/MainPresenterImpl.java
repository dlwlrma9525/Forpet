package kr.forpet.view.main.presenter;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import kr.forpet.map.CustomMarkerBuilder;
import kr.forpet.view.main.model.MainModel;

public class MainPresenterImpl implements MainPresenter {

    private MainPresenter.View mView;
    private MainModel mainModel;

    public MainPresenterImpl() {
        mainModel = new MainModel();
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void onCreate(Context context) {
        mainModel.initGooglePlayService(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener((marker) -> {

        });

        onMyGps();
    }

    @Override
    public void onMyGps() {
        try {
            Task task = mainModel.getMyLocation();
            task.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Location result = task.getResult();
                        LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());

                        mView.addMarker(new CustomMarkerBuilder(mView.getContext(), latLng)
                                .type("hospital")
                                .event(true)
                                .build());
                        mView.moveCamera(latLng);
                    } else {
                        Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                        Log.e("GooglePlayServices", "Exception: %s", task.getException());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
