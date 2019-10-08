package kr.forpet.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "forpet_common_disease")
public class CommonDisease {

    @ColumnInfo(name = "no")
    @PrimaryKey
    private Integer no;

    @ColumnInfo(name = "disease")
    private String disease;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "infection")
    private String infection;

    @ColumnInfo(name = "symptom")
    private String symptom;

    @ColumnInfo(name = "prevention")
    private String prevention;

    @ColumnInfo(name = "pet_type")
    private String petType;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getPrevention() {
        return prevention;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }
}