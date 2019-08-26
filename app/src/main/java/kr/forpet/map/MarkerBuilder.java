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
import kr.forpet.data.entity.ForpetShop;

public class MarkerBuilder {

    private Context context;
    private LatLng latLng;

    private String catCode;
    private String hash;
    private String title;
    private String event;
    private String sale;

    public MarkerBuilder(Context context, LatLng latLng) {
        this.context = context;
        this.latLng = latLng;
    }

    public MarkerBuilder catCode(String catCode) {
        this.catCode = catCode;
        return this;
    }

    public MarkerBuilder hash(String hash) {
        this.hash = hash;
        return this;
    }

    public MarkerBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MarkerBuilder event(String event) {
        this.event = event;
        return this;
    }

    public MarkerBuilder sale(String sale) {
        this.sale = sale;
        return this;
    }

    public MarkerOptions build() {
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.snippet(hash);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        if (event.equals("N")) {
            view = inflater.inflate(R.layout.marker, null);
        } else {
            view = inflater.inflate(R.layout.marker_icon, null);

            ImageView icon = view.findViewById(R.id.icon_marker);
            icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_event_marker));
        }

        ImageView logoView = view.findViewById(R.id.image_marker);
        TextView titleView = view.findViewById(R.id.text_marker);

        int drawable = 0;
        switch (ForpetShop.CatCode.compare(catCode)) {
            case SHOP:          drawable = R.drawable.icon_ani_shop_s;  break;
            case PHARM:         drawable = R.drawable.icon_pharm_s;     break;
            case HOSPITAL:      drawable = R.drawable.icon_hospital_s;  break;
            case DOGPENSION:    drawable = R.drawable.icon_pension_s;   break;
            case DOGGROUND:     drawable = R.drawable.icon_play_s;      break;
            case DOGCAFE:       drawable = R.drawable.icon_dog_cafe_s;  break;
            case CATCAFE:       drawable = R.drawable.icon_cat_cafe_s;  break;
            case BEAUTY:        drawable = R.drawable.icon_hairshop;    break;
        }

        logoView.setImageDrawable(ContextCompat.getDrawable(context, drawable));
        titleView.setText(title);

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