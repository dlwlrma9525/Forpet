package kr.forpet.view.knowledge.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kr.forpet.view.factory.VaccineViewFactory;
import kr.forpet.view.factory.ViewFactory;

public class KnowledgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewFactory factory = new VaccineViewFactory();
        View contentView = factory.createView(this);
        setContentView(contentView);
    }
}
