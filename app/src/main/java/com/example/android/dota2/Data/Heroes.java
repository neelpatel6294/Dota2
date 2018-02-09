package com.example.android.dota2.Data;

/**
 * Created by PATEL on 2/2/2018.
 */

public class Heroes {

    private String heroImage;
    private String heroId;
    private String name;
    private String primary_attribute;
    private String attack_type;

    public Heroes(String heroImage,String heroId,String name, String primary_attribute, String attack_type) {
        this.heroImage = heroImage;
        this.heroId = heroId;
        this.name = name;
        this.primary_attribute = primary_attribute;
        this.attack_type = attack_type;
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
}
