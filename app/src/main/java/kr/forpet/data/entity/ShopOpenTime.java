package kr.forpet.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "forpet_shop_open_time", indices = {@Index(name = "idx_time_hash", value = {"forpet_hash"})})
public class ShopOpenTime {

    @ColumnInfo(name = "no")
    @PrimaryKey
    private Integer no;

    @ColumnInfo(name = "forpet_hash")
    private String forpetHash;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "day")
    private String day;

    @ColumnInfo(name = "offday_type")
    private String offdayType;

    @ColumnInfo(name = "period")
    private String period;

    @ColumnInfo(name = "remarks")
    private String remarks;

    @NonNull
    public Integer getNo() {
        return no;
    }

    public void setNo(@NonNull Integer no) {
        this.no = no;
    }

    public String getForpetHash() {
        return forpetHash;
    }

    public void setForpetHash(String forpetHash) {
        this.forpetHash = forpetHash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOffdayType() {
        return offdayType;
    }

    public void setOffdayType(String offdayType) {
        this.offdayType = offdayType;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}