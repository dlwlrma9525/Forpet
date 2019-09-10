package kr.forpet.view.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;

public class BottomSheetViewFactory implements ItemViewFactory {

    private Shop shop;

    public BottomSheetViewFactory(Shop shop) {
        this.shop = shop;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_bottom_sheet, null);

        TextView textViewPlaceName = contentView.findViewById(R.id.text_detail_place);
        TextView textViewAddress = contentView.findViewById(R.id.text_detail_address);
        TextView textViewPhone = contentView.findViewById(R.id.text_detail_phone);
        TextView textViewHomepage = contentView.findViewById(R.id.text_detail_homepage);

        textViewPlaceName.setText(shop.getPlaceName());
        textViewAddress.setText(shop.getRoadAddressName());
        textViewPhone.setText(shop.getPhone());
        textViewHomepage.setText(shop.getHomepage());

        return contentView;
    }
}
