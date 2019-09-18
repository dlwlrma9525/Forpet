package kr.forpet.view.search.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.forpet.data.entity.Shop;
import kr.forpet.databinding.ItemSearchBinding;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    private List<Shop> mDataList;

    public SearchRecyclerAdapter() {
        mDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding binding
                = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {
        Shop shop = mDataList.get(position);
        holder.bind(shop);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setItem(List<Shop> dataList) {
        mDataList = dataList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchBinding binding;

        ViewHolder(@NonNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Shop shop) {
            binding.setShop(shop);
        }
    }
}
