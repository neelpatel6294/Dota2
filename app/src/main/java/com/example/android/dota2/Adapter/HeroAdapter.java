package com.example.android.dota2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.dota2.MainActivity;
import com.example.android.dota2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PATEL on 2/2/2018.
 */

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.MyViewHolder> {


    private List<Heroes> heroesList;
    final private ListItemClickListener listItemClickListener;


    public interface ListItemClickListener {

        void onListItemClick(Heroes heroes);
    }


    public HeroAdapter(MainActivity mainActivity, List<Heroes> mNumberItems, ListItemClickListener listItemClickListener) {
        this.heroesList = mNumberItems;
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Heroes item = heroesList.get(position);
        Context context = holder.heroImage.getContext();
        Picasso.with(context).load("https://api.opendota.com"
                + item.getHeroImage()).into(holder.heroImage);
        holder.bind(heroesList.get(position), listItemClickListener);
    }

    @Override
    public int getItemCount() {
        return heroesList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView heroImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            heroImage = (ImageView) itemView.findViewById(R.id.custom_Image_View);
            itemView.setOnClickListener(this);
        }

        public void bind(final Heroes heroes, final ListItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onListItemClick(heroes);
                }
            });
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Heroes imageClick = heroesList.get(adapterPosition);
            listItemClickListener.onListItemClick(imageClick);
        }
    }
}
