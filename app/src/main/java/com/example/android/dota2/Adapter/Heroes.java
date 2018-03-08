package com.example.android.dota2.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PATEL on 2/2/2018.
 */

public class Heroes implements Parcelable {

    private String heroImage;
    private String heroId;
    private String name;
    private String primary_attribute;
    private String attack_type;
    private String hero_roles;

    public Heroes(String heroImage) {
        this.heroImage = heroImage;
    }

    public Heroes(String heroImage, String heroId, String name, String primary_attribute, String attack_type) {
        this.heroImage = heroImage;
        this.heroId = heroId;
        this.name = name;
        this.primary_attribute = primary_attribute;
        this.attack_type = attack_type;
        // this.hero_roles = hero_roles;
    }

    public String getHeroImage() {
        return heroImage;
    }

    public String getHeroId() {
        return heroId;
    }

    public String getName() {
        return name;
    }

    public String getPrimary_attribute() {
        return primary_attribute;
    }

    public String getAttack_type() {
        return attack_type;
    }

    public String getHero_roles() {
        return hero_roles;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.heroImage);
        dest.writeString(this.heroId);
        dest.writeString(this.name);
        dest.writeString(this.primary_attribute);
        dest.writeString(this.attack_type);
        dest.writeString(this.hero_roles);
    }

    protected Heroes(Parcel in) {
        this.heroImage = in.readString();
        this.heroId = in.readString();
        this.name = in.readString();
        this.primary_attribute = in.readString();
        this.attack_type = in.readString();
        this.hero_roles = in.readString();
    }

    public static final Creator<Heroes> CREATOR = new Creator<Heroes>() {
        @Override
        public Heroes createFromParcel(Parcel source) {
            return new Heroes(source);
        }

        @Override
        public Heroes[] newArray(int size) {
            return new Heroes[size];
        }
    };
}
