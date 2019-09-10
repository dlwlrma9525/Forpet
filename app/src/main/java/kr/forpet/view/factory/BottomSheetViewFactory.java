package kr.forpet.view.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

        return contentView;
    }
}
