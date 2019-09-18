package kr.forpet.view.search.adapter;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import kr.forpet.data.entity.Shop;

public class DataBindingAdapter {

    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Shop> observableList) {
        SearchRecyclerAdapter adapter = (SearchRecyclerAdapter) recyclerView.getAdapter();
        adapter.setItem(observableList);
        adapter.notifyDataSetChanged();
    }
}
