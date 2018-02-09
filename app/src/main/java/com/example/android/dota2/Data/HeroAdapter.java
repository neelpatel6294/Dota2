package com.example.android.dota2.Data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.dota2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PATEL on 2/2/2018.
 */

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.MyViewHolder> {


    private List<Heroes> heroesList;


    public HeroAdapter(List<Heroes> mNumberItems) {
        this.heroesList = mNumberItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.number_rv_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Heroes item = heroesList.get(position);
        Context context = holder.heroImage.getContext();
        Picasso.with(context).load("https://api.opendota.com"
                + item.getHeroImage()).into(holder.heroImage);
        holder.heroId.setText(item.getHeroId());
        holder.heroName.setText(item.getName());
        holder.heroAttribute.setText(item.getPrimary_attribute());
        holder.heroAttack_type.setText(item.getAttack_type());

    }

    @Override
    public int getItemCount() {
        return heroesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView heroImage;
        TextView heroId;
        TextView heroName;
        TextView heroAttribute;
        TextView heroAttack_type;

        public MyViewHolder(View itemView) {
            super(itemView);
            heroImage = (ImageView) itemView.findViewById(R.id.hero_image);
            heroId = (TextView) itemView.findViewById(R.id.hero_id);
            heroName = (TextView) itemView.findViewById(R.id.hero_name);
            heroAttribute = (TextView) itemView.findViewById(R.id.hero_attribute);
            heroAttack_type = (TextView) itemView.findViewById(R.id.hero_attack_Type);
        }

    }

}
