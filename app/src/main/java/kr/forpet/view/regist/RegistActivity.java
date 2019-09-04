package kr.forpet.view.regist;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import kr.forpet.R;

public class RegistActivity extends AppCompatActivity {

    @BindView(R.id.text_regist_header)
    TextView testRegistHeader;

    @BindView(R.id.button_regist)
    Button buttonRegist;

    @BindView(R.id.view_regist_item)
    ViewGroup viewRegistItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
    }
}