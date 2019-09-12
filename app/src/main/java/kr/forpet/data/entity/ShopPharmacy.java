package kr.forpet.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "forpet_shop_pharmacy", indices = {@Index(name = "idx_pharm_hash", value = {"forpet_hash"})})
public class ShopPharmacy {

    @ColumnInfo(name = "no")
    @PrimaryKey
    private Integer no;

    @ColumnInfo(name = "forpet_hash")
    private String forpetHash;

    @ColumnInfo(name = "medicine_categories_for_sale")
    private String medicineCategoriesForSale;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getForpetHash() {
        return forpetHash;
    }

    public void setForpetHash(String forpetHash) {
        this.forpetHash = forpetHash;
    }

    public String getMedicineCategoriesForSale() {
        return medicineCategoriesForSale;
    }

    public void setMedicineCategoriesForSale(String medicineCategoriesForSale) {
        this.medicineCategoriesForSale = medicineCategoriesForSale;
    }
}