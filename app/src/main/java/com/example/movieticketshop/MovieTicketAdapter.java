package com.example.movieticketshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieTicketAdapter extends RecyclerView.Adapter<MovieTicketAdapter.ViewHolder> implements Filterable {
    private ArrayList<MovieTicket> mTicketItemsData = new ArrayList<>();
    private ArrayList<MovieTicket> mTicketItemsDataAll= new ArrayList<>();
    private Context nContext;
    private int lastPosition = -1;

    MovieTicketAdapter(Context context, ArrayList<MovieTicket> itemsData){
        this.mTicketItemsData = itemsData;
        this.mTicketItemsDataAll = itemsData;
        nContext = context;
    }

    @Override
    public MovieTicketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(nContext)
                .inflate(R.layout.list_item, parent,false));
    }

    @Override
    public void onBindViewHolder(MovieTicketAdapter.ViewHolder holder, int position) {
        MovieTicket currentItem = mTicketItemsData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(nContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mTicketItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return movieShopingFilter;
    }

    private Filter movieShopingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MovieTicket> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mTicketItemsData.size();
                results.values = mTicketItemsDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(MovieTicket item : mTicketItemsDataAll) {
                    if(item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mTicketItemsData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleText;
        private TextView mLengthText;
        private TextView mDescriptionText;
        private TextView mAgeLimitText;
        private TextView mPriceText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;


        public ViewHolder(View itemView) {
            super(itemView);

            mTitleText = itemView.findViewById(R.id.title);
            mLengthText = itemView.findViewById(R.id.length);
            mDescriptionText = itemView.findViewById(R.id.description);
            mAgeLimitText = itemView.findViewById(R.id.ageLimit);
            mPriceText = itemView.findViewById(R.id.price);
            mItemImage = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(view -> ((ShoppingActivity)nContext).updateAlertIcon());

        }

        public void bindTo(MovieTicket currentItem) {
            mTitleText.setText(currentItem.getTitle());
            mLengthText.setText(currentItem.getLength());
            mDescriptionText.setText(currentItem.getDescription());
            mAgeLimitText.setText(currentItem.getAgeLimit());
            mPriceText.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRatingInfo());

            Glide.with(nContext).load(currentItem.getImageResource()).into(mItemImage);
        }
    }
}
