package kr.forpet.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "forpet_food", indices = {@Index(name = "idx_food_no", value = {"no"})})
public class Food {

    @ColumnInfo(name = "no")
    @PrimaryKey
    private Integer no;

    @ColumnInfo(name = "pet_type")
    private String petType;

    @ColumnInfo(name = "food_type")
    private String foodType;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "spec")
    private String spec;

    @ColumnInfo(name = "strong")
    private String strong;

    @ColumnInfo(name = "weak")
    private String weak;

    @ColumnInfo(name = "precaution")
    private String precaution;

    @ColumnInfo(name = "remarks")
    private String remarks;

    @ColumnInfo(name = "option")
    private String option;

    @ColumnInfo(name = "last_updated")
    private String lastUpdated;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getStrong() {
        return strong;
    }

    public void setStrong(String strong) {
        this.strong = strong;
    }

    public String getWeak() {
        return weak;
    }

    public void setWeak(String weak) {
        this.weak = weak;
    }

    public String getPrecaution() {
        return precaution;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}