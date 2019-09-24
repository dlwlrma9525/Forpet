package kr.forpet.view.recommend;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;

public class RecommendActivity extends AppCompatActivity {

    @BindView(R.id.radiogroup_recommend_consensus)
    RadioGroup radioGroupConsensus;

    @BindView(R.id.radiogroup_recommend_facility)
    RadioGroup radioGroupFacility;

    @BindView(R.id.radiogroup_recommend_price)
    RadioGroup radioGroupPrice;

    @BindView(R.id.radiogroup_recommend_kindness)
    RadioGroup radioGroupKindness;

    @BindView(R.id.button_recommend_evaluate)
    Button buttonEvaluate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);

        buttonEvaluate.setOnClickListener((v) -> {
            this.finish();
        });
    }
}
