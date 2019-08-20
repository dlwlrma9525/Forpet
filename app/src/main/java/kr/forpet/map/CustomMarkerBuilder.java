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
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.forpet.R;

public class CustomMarkerBuilder {
    private Context context;
    private String type;
    private LatLng latLng;
    private boolean event;
    private boolean sale;

    public CustomMarkerBuilder(Context context, LatLng latLng) {
        this.context = context;
        this.latLng = latLng;
    }

    public CustomMarkerBuilder type(String type) {
        this.type = type;
        return this;
    }

    public CustomMarkerBuilder event(boolean event) {
        this.event = event;
        return this;
    }

    public CustomMarkerBuilder sale(boolean sale) {
        this.sale = sale;
        return this;
    }

    public MarkerOptions build() {
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        if(!event) {
            view = inflater.inflate(R.layout.marker, null);
        } else {
            view = inflater.inflate(R.layout.marker_icon, null);

            ImageView iconMarker = view.findViewById(R.id.icon_marker);
            iconMarker.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_event_marker));
        }

        ImageView imageMarker = view.findViewById(R.id.image_marker);
        TextView textMarker = view.findViewById(R.id.text_marker);

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
        textMarker.setText("Forpet");

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
