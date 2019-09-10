package kr.forpet.view.factory;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.forpet.R;
import kr.forpet.data.entity.OpenTime;
import kr.forpet.data.entity.Shop;

public class BottomSheetViewFactory implements ItemViewFactory {

    private Shop mShop;

    public BottomSheetViewFactory(Shop shop) {
        this.mShop = shop;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_bottom_sheet, null);

        TextView textPlaceName = contentView.findViewById(R.id.text_detail_place);
        textPlaceName.setText(mShop.getPlaceName());

        TextView textAddress = contentView.findViewById(R.id.text_detail_address);
        textAddress.setText(mShop.getRoadAddressName());

        TextView textPhone = contentView.findViewById(R.id.text_detail_phone);
        textPhone.setText(mShop.getPhone());

        TextView textHomepage = contentView.findViewById(R.id.text_detail_homepage);
        textHomepage.setText(mShop.getHomepage());

        StringBuilder sb = new StringBuilder();
        for (OpenTime time : mShop.getOpenTimeList()) {
            if (time.getType().equals("영업일")) {
                sb.append(time.getDay()).append(" : ").append(time.getPeriod()).append(" ");
            } else {
                sb.append(time.getType()).append(" : ").append(time.getDay()).append(" ");
            }

            if (time.getRemarks() != null)
                sb.append(time.getRemarks());

            sb.append("\r\n");
        }

        TextView textOpenTime = contentView.findViewById(R.id.text_open_time);
        textOpenTime.setText(sb.toString());

        LinearLayout linearLayout = contentView.findViewById(R.id.linear_detail_option);

        if (mShop.getOptParking() != null)
            linearLayout.addView(createOptionView(context, R.drawable.enable_parking));
        if (mShop.getOptReservation() != null)
            linearLayout.addView(createOptionView(context, R.drawable.enable_reservation));
        if (mShop.getOptWifi() != null)
            linearLayout.addView(createOptionView(context, R.drawable.enable_wifi));
        if (mShop.getOpt365().equals("Y"))
            linearLayout.addView(createOptionView(context, R.drawable.enable_365));
        if (mShop.getOptNight().equals("Y"))
            linearLayout.addView(createOptionView(context, R.drawable.enable_night));
        if (mShop.getOptShop().equals("Y"))
            linearLayout.addView(createOptionView(context, R.drawable.enable_shop));
        if (mShop.getOptBeauty().equals("Y"))
            linearLayout.addView(createOptionView(context, R.drawable.enable_beauty));
        if (mShop.getOptBigdog().equals("Y"))
            linearLayout.addView(createOptionView(context, R.drawable.enable_bigdog));
        if (mShop.getOptHotel().equals("Y"))
            linearLayout.addView(createOptionView(context, R.drawable.enable_hotel));

        return contentView;
    }

    private ImageView createOptionView(Context context, int resourceId) {
        FrameLayout.LayoutParams params
                = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        params.height = Math.round(21 * metrics.density);
        params.width = Math.round(21 * metrics.density);
        params.setMarginEnd(Math.round(5 * metrics.density));

        ImageView image = new ImageView(context);
        image.setImageResource(resourceId);
        image.setLayoutParams(params);

        return image;
    }
}
