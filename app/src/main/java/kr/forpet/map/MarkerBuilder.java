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

    private LatLng mLatLng;

    private String mCatCode;
    private String mHash;
    private String mTitle;
    private String mEvent;
    private String mSale;

    public MarkerBuilder(LatLng latLng) {
        this.mLatLng = latLng;
    }

    public MarkerBuilder catCode(String catCode) {
        this.mCatCode = catCode;
        return this;
    }

    public MarkerBuilder hash(String hash) {
        this.mHash = hash;
        return this;
    }

    public MarkerBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public MarkerBuilder event(String event) {
        this.mEvent = event;
        return this;
    }

    public MarkerBuilder sale(String sale) {
        this.mSale = sale;
        return this;
    }

    public MarkerOptions build(Context context) {
        StringBuilder sb = new StringBuilder(mHash).append(",").append(mCatCode);

        MarkerOptions marker = new MarkerOptions();
        marker.position(mLatLng);
        marker.snippet(sb.toString());
        marker.icon(MarkerBuilder.createIconFromDrawable(context, mCatCode));

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