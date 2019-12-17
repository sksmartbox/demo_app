package com.sampra.ui.adapter;

import android.graphics.Color;
import android.text.Html;
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
import com.sampra.data.model.AllModel;
import com.sampra.data.model.RecordsItem;
import com.sampra.ui.home.news.all.AllViewModel;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void addItemUpdate(List<RecordsItem> itemList){
        int size = this.itemList.size();
        this.itemList.addAll(itemList);
        notifyItemRangeChanged(size,this.itemList.size()-1);
    }


    public class AllViewHolder extends BaseViewHolder {

        ImageView image;
        TextView updateDate;
        ShowMoreTextView desc;
        TextView like,comment;
        ImageView social_icon;

        public AllViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            desc = itemView.findViewById(R.id.description);
            updateDate = itemView.findViewById(R.id.updateDate);
            social_icon = itemView.findViewById(R.id.social_icon);
            like = itemView.findViewById(R.id.like_txt);
            comment = itemView.findViewById(R.id.comment_txt);
        }

        @Override
        public void bind(RecordsItem item) {
            int a;
            desc.setText(Html.fromHtml(item.getPostDescription()));
            getReadMore(desc);
            getUpdateDate(item.getUpdatedAt(),updateDate);
            if(item.getPostComment() == null)
                comment.setText("0");
            else if(((Double) item.getPostComment()).doubleValue()==0.0)
                comment.setText("0");
            else
                comment.setText(item.getPostComment().toString());
            if(item.getType() == 1)
            {
                social_icon.setImageResource(R.drawable.facebook);
            }
            if(item.getType() == 2)
            {
                social_icon.setImageResource(R.drawable.twitter);
            }
            if(item.getType() == 3)
            {
                social_icon.setImageResource(R.drawable.insta);
            }
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

    private void getReadMore(ShowMoreTextView desc) {
        desc.setShowingChar(100);
        desc.setShowingLine(3);
        desc.addShowMoreText("Read More");
        desc.addShowLessText("Read Less");
        desc.setShowMoreColor(Color.BLUE); // or other color
        desc.setShowLessTextColor(Color.BLUE);
    }

    private void getUpdateDate(String updatedAt, TextView updateDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] splitStr = updatedAt.split(" ");
        String year = splitStr[0].split("-")[0];
        String month = splitStr[0].split("-")[1];
        String day = splitStr[0].split("-")[2];
        if(month.equals("1") || month.equals("01"))
            updateDate.setText("Jan "+day+" "+year);
        else if(month.equals("2") || month.equals("02"))
            updateDate.setText("Feb "+day+" "+year);
        else if(month.equals("3") || month.equals("03"))
            updateDate.setText("Mar "+day+" "+year);
        else if(month.equals("4") || month.equals("04"))
            updateDate.setText("April "+day+" "+year);
        else if(month.equals("5") || month.equals("05"))
            updateDate.setText("May "+day+" "+year);
        else if(month.equals("6") || month.equals("06"))
            updateDate.setText("Jun "+day+" "+year);
        else if(month.equals("7") || month.equals("07"))
            updateDate.setText("July "+day+" "+year);
        else if(month.equals("8") || month.equals("08"))
            updateDate.setText("Aug "+day+" "+year);
        else if(month.equals("9") || month.equals("09"))
            updateDate.setText("Sept "+day+" "+year);
        else if(month.equals("10") || month.equals("010"))
            updateDate.setText("Oct "+day+" "+year);
        else if(month.equals("11") || month.equals("011"))
            updateDate.setText("Nov "+day+" "+year);
        else if(month.equals("12") || month.equals("012"))
            updateDate.setText("Dec "+day+" "+year);



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
