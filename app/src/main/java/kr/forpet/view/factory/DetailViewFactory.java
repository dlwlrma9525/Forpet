package kr.forpet.view.factory;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import kr.forpet.R;
import kr.forpet.annotation.ItemView;
import kr.forpet.annotation.Label;

public class DetailViewFactory implements ItemViewFactory {

    private Class<?> clazz;

    public DetailViewFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public View createView(Context context) {
        return null;
    }
}