package kr.forpet.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "forpet_vaccination")
public class Vaccination {

    @ColumnInfo(name = "no")
    @PrimaryKey
    private Integer no;

    @ColumnInfo(name = "pet_type")
    private String petType;

    @ColumnInfo(name = "vaccine_name")
    private String vaccineName;

    @ColumnInfo(name = "week1")
    private String week1;

    @ColumnInfo(name = "week2")
    private String week2;

    @ColumnInfo(name = "week3")
    private String week3;

    @ColumnInfo(name = "week4")
    private String week4;

    @ColumnInfo(name = "week5")
    private String week5;

    @ColumnInfo(name = "week6")
    private String week6;

    @ColumnInfo(name = "years")
    private String years;

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

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getWeek1() {
        return week1;
    }

    public void setWeek1(String week1) {
        this.week1 = week1;
    }

    public String getWeek2() {
        return week2;
    }

    public void setWeek2(String week2) {
        this.week2 = week2;
    }

    public String getWeek3() {
        return week3;
    }

    public void setWeek3(String week3) {
        this.week3 = week3;
    }

    public String getWeek4() {
        return week4;
    }

    public void setWeek4(String week4) {
        this.week4 = week4;
    }

    public String getWeek5() {
        return week5;
    }

    public void setWeek5(String week5) {
        this.week5 = week5;
    }

    public String getWeek6() {
        return week6;
    }

    public void setWeek6(String week6) {
        this.week6 = week6;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }
}