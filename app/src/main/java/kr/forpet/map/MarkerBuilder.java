package kr.forpet.map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;

public class MarkerBuilder {

    private LatLng latLng;

    private String catCode;
    private String hash;
    private String title;
    private String event;
    private String sale;

    public MarkerBuilder(LatLng latLng) {
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

    public MarkerOptions build(Context context) {
        StringBuilder sb = new StringBuilder(hash).append(",").append(catCode);

        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.snippet(sb.toString());
        marker.icon(MarkerBuilder.createIconFromDrawable(context, catCode));

        return marker;
    }

    public static BitmapDescriptor createIconFromDrawable(Context context, String catCode) {
        int drawable = 0;
        switch (Shop.CatCode.compare(catCode)) {
            case SHOP:
                drawable = R.drawable.marker_shop;
                break;
            case PHARM:
                drawable = R.drawable.marker_pharm;
                break;
            case HOSPITAL:
                drawable = R.drawable.marker_hospital;
                break;
            case DOGPENSION:
                drawable = R.drawable.marker_dogpension;
                break;
            case DOGGROUND:
                drawable = R.drawable.marker_dogground;
                break;
            case DOGCAFE:
                drawable = R.drawable.marker_cafe;
                break;
            case CATCAFE:
                drawable = R.drawable.marker_cafe;
                break;
            case BEAUTY:
                drawable = R.drawable.marker_beauty;
                break;
        }

        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(drawable);
        return BitmapDescriptorFactory.fromBitmap(bitmapDrawable.getBitmap());
    }
}