package kr.forpet.view.factory;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.forpet.R;
import kr.forpet.data.entity.OpenTime;
import kr.forpet.data.entity.Shop;

public class BottomSheetViewFactory implements ItemViewFactory {

    private Context mContext;
    private Shop mShop;

    public BottomSheetViewFactory(Shop shop) {
        this.mShop = shop;
    }

    @Override
    public View createView(Context context) {
        mContext = context;

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

        GridLayout grid = contentView.findViewById(R.id.linear_detail_option);

        if (mShop.getOptParking() != null)
            grid.addView(createOptionView(R.drawable.enable_parking));
        if (mShop.getOptReservation() != null)
            grid.addView(createOptionView(R.drawable.enable_reservation));
        if (mShop.getOptWifi() != null)
            grid.addView(createOptionView(R.drawable.enable_wifi));
        if (mShop.getOpt365().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_365));
        if (mShop.getOptNight().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_night));
        if (mShop.getOptShop().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_shop));
        if (mShop.getOptBeauty().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_beauty));
        if (mShop.getOptBigdog().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_bigdog));
        if (mShop.getOptHotel().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_hotel));

        LinearLayout layoutAdditionalInfo = contentView.findViewById(R.id.layout_additional_info);
        if(mShop.getAdditionalInfo() != null) {
            // do something..
        } else {
            layoutAdditionalInfo.setVisibility(View.GONE);
        }

        LinearLayout layoutMedicine = contentView.findViewById(R.id.layout_medicine);
        if(mShop.getCategoryGroupCode().equals(Shop.CategoryGroupCode.PHARM.toString())) {
            TextView textMedicine = contentView.findViewById(R.id.text_medicine);
            textMedicine.setText(mShop.getPharmacy().getMedicineCategoriesForSale());
        } else {
            layoutMedicine.setVisibility(View.GONE);
        }

        return contentView;
    }

    private View createOptionView(int resourceId) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_opt, null);

        FrameLayout.LayoutParams params
                = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        params.setMargins(0, 0, Math.round(10 * metrics.density), Math.round(10 * metrics.density));
        v.setLayoutParams(params);

        ImageView image = v.findViewById(R.id.image_opt);
        image.setImageResource(resourceId);

        TextView text = v.findViewById(R.id.text_opt);
        switch (resourceId) {
            case R.drawable.enable_parking:
                text.setText("주차");
                break;
            case R.drawable.enable_reservation:
                text.setText("예약");
                break;
            case R.drawable.enable_wifi:
                text.setText("무선인터넷");
                break;
            case R.drawable.enable_365:
                text.setText("연중무휴");
                break;
            case R.drawable.enable_night:
                text.setText("야간");
                break;
            case R.drawable.enable_shop:
                text.setText("용품");
                break;
            case R.drawable.enable_beauty:
                text.setText("미용");
                break;
            case R.drawable.enable_bigdog:
                text.setText("대형견 가능");
                break;
            case R.drawable.enable_hotel:
                text.setText("호텔");
                break;
        }

        return v;
    }
}
