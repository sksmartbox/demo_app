package com.sampra.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sampra.R;
import com.sampra.data.model.RecordsItem;
import com.sampra.ui.home.news.all.AllViewModel;

import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.BaseViewHolder> {

    public static final int EMPTY_ITEM = 0;
    public static final int ITEM = 1;

    private List<RecordsItem> itemList;

    public AllAdapter(List<RecordsItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_ITEM:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item, parent, false);
                return new EmptyViewHolder(view);
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
                return new AllViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
                return new AllViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        if (holder instanceof AllViewHolder){
            AllViewHolder allViewHolder = (AllViewHolder) holder;
            allViewHolder.bind(itemList.get(position));
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (itemList != null && itemList.size() > 0) {
            return ITEM;
        } else {
            return EMPTY_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (itemList != null && itemList.size() > 0) {
            return itemList.size();
        } else {
            return 1;
        }
    }

    public void addItem(List<RecordsItem> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }


    public class AllViewHolder extends BaseViewHolder {

        ImageView image;
        TextView updateDate;
        TextView desc;

        public AllViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            desc = itemView.findViewById(R.id.description);
            updateDate = itemView.findViewById(R.id.updateDate);
        }

        @Override
        public void bind(RecordsItem item) {

            desc.setText(item.getPostDescription());
            updateDate.setText(item.getUpdatedAt());
            if (!TextUtils.isEmpty(item.getPostImage())){
                image.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                        .load(item.getPostImage())
                        .into(image);
            } else {
                image.setVisibility(View.GONE);
            }
        }
    }


    public class EmptyViewHolder extends BaseViewHolder {

        ImageView image;
        TextView title;
        TextView desc;

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
//            image = itemView.findViewById(R.id.imageView);
//            desc = itemView.findViewById(R.id.description);
        }

        @Override
        public void bind(RecordsItem item) {


        }
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(RecordsItem item);
    }


}
