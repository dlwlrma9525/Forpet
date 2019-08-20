package kr.forpet.view.request.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kr.forpet.R;
import kr.forpet.data.Advertisement;
import kr.forpet.data.Hospital;
import kr.forpet.data.PetShop;
import kr.forpet.data.Pharmacy;
import kr.forpet.view.factory.ItemViewFactory;
import kr.forpet.view.factory.RequestViewFactory;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent intent = getIntent();
        Class clazz = (Class) intent.getSerializableExtra("type");

        ItemViewFactory factory = new RequestViewFactory(clazz);
        View contentView = factory.createView(this);

        TextView textView = findViewById(R.id.header_request);

        Button button = findViewById(R.id.button_request);
        button.setText(getString(R.string.regist));

        FrameLayout frameLayout = findViewById(R.id.content_request);
        frameLayout.addView(contentView);

        if (clazz.getName().equals(Hospital.class.getName()))
            textView.setText(getString(R.string.regist_header_hosp));
        else if (clazz.getName().equals(Pharmacy.class.getName()))
            textView.setText(getString(R.string.regist_header_pharmacy));
        else if (clazz.getName().equals(PetShop.class.getName()))
            textView.setText(getString(R.string.regist_header_shop));
        else if (clazz.getName().equals(Advertisement.class.getName()))
            textView.setText(getString(R.string.regist_header_ad));
    }
}