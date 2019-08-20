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

public class RequestViewFactory implements ItemViewFactory {

    private Class<?> clazz;

    public RequestViewFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public View createView(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        ArrayList<Field> fields = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            Log.i("reflection", field.getName());

            if (field.getAnnotation(ItemView.class) != null)
                fields.add(field);
        }

        Collections.sort(fields, (Field o1, Field o2) -> {
            ItemView a1 = o1.getAnnotation(ItemView.class);
            ItemView a2 = o2.getAnnotation(ItemView.class);

            if (a1.idx() > a2.idx())
                return 1;
            else if (a1.idx() < a2.idx())
                return -1;
            else
                return 0;
        });

        for (Field field : fields) {
            Label annotation = field.getAnnotation(Label.class);

            View child = inflater.inflate(R.layout.item_request, null);

            TextView textView = child.findViewById(R.id.text_item);
            textView.setText(annotation.value());

            EditText editText = child.findViewById(R.id.edit_item);
            editText.setTag(annotation.value());

            Button button = child.findViewById(R.id.button_item);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            params.bottomMargin = Math.round(6 * metrics.density);

            if (annotation.value().contains("종류")) {
                FrameLayout frameLayout = child.findViewById(R.id.frame_item);
                Spinner spinner = child.findViewById(R.id.spinner_item);

                editText.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            } else if (annotation.value().contains("사진")) {
                View optionView = createOptionView(context);
                layout.addView(optionView);

                button.setText("사진");
                button.setVisibility(View.VISIBLE);
            } else if (annotation.value().equals("주소")) {
                button.setText("선택");
                button.setVisibility(View.VISIBLE);
            }

            child.setLayoutParams(params);
            layout.addView(child);
        }

        return layout;
    }

    private View createOptionView(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_request, null);

        TextView textView = view.findViewById(R.id.text_item);
        textView.setText(context.getString(R.string.regist_header_opt));

        EditText editText = view.findViewById(R.id.edit_item);
        editText.setVisibility(View.GONE);

        GridLayout grid = view.findViewById(R.id.grid_item);
        grid.setVisibility(View.VISIBLE);

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().startsWith("opt")) {
                CheckBox cb = new CheckBox(context);
                cb.setTag(f.getName());
                cb.setText(f.getAnnotation(Label.class).value());
                grid.addView(cb);
            }
        }

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.topMargin = Math.round(16 * metrics.density);
        params.bottomMargin = Math.round(16 * metrics.density);

        view.setLayoutParams(params);
        return view;
    }
}