package kr.forpet.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.forpet.R;

public class GoogleMapsMarkerBuilder {
    private Context context;
    private String type;
    private LatLng latLng;
    private boolean event;
    private boolean sale;

    public GoogleMapsMarkerBuilder(Context context, LatLng latLng) {
        this.context = context;
        this.latLng = latLng;
    }

    public GoogleMapsMarkerBuilder type(String type) {
        this.type = type;
        return this;
    }

    public GoogleMapsMarkerBuilder event(boolean event) {
        this.event = event;
        return this;
    }

    public GoogleMapsMarkerBuilder sale(boolean sale) {
        this.sale = sale;
        return this;
    }

    public MarkerOptions build() {
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.google_maps_marker, null);

        ImageView imageMarker = view.findViewById(R.id.image_marker);
        ImageView imageEvent = view.findViewById(R.id.image_event);

        int drawable = 0;
        switch (type) {
            case "cafe":
                drawable = R.drawable.marker_cafe;
                break;
            case "hair":
                drawable = R.drawable.marker_hair;
                break;
            case "hospital":
                drawable = R.drawable.marker_hospital;
                break;
            case "pension":
                drawable = R.drawable.marker_pension;
                break;
            case "pharmacy":
                drawable = R.drawable.marker_pharmacy;
                break;
            case "play":
                drawable = R.drawable.marker_play;
                break;
            case "shop":
                drawable = R.drawable.marker_shop;
                break;
        }

        imageMarker.setImageDrawable(ContextCompat.getDrawable(context, drawable));

        if (event) {
            imageEvent.setVisibility(View.VISIBLE);
            imageEvent.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_event_marker));
        }

        if (sale) {
            imageEvent.setVisibility(View.VISIBLE);
            imageEvent.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_dc_marker));
        }

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        marker.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(windowManager, view)));
        return marker;
    }

    private Bitmap createDrawableFromView(WindowManager windowManager, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
}
