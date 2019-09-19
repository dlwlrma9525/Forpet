package kr.forpet.data.databinding;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import kr.forpet.data.entity.Shop;
import kr.forpet.view.search.adapter.SearchRecyclerAdapter;

public class DataBindingAdapter {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Shop> observableList) {
        SearchRecyclerAdapter adapter = (SearchRecyclerAdapter) recyclerView.getAdapter();
        adapter.setItem(observableList);
        adapter.notifyDataSetChanged();
    }

    @BindingConversion
    public static String conversionFloatToString(float value) {
        return String.format("%.2f km", value / 1000);
    }
}
