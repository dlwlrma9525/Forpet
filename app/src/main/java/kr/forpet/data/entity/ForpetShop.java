package kr.forpet.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import kr.forpet.annotation.ItemView;
import kr.forpet.annotation.Label;

@Entity(
        tableName = "forpet_shop",
        indices = {
                @Index(name = "idx_shop_category", value = {"category_group_code"}),
                @Index(name = "idx_shop_name", value = {"place_name"}),
                @Index(name = "idx_shop_x", value = {"x"}),
                @Index(name = "idx_shop_hash", value = {"forpet_hash"}),
                @Index(name = "idx_shop_y", value = {"y"})
        }
)
public class ForpetShop {
    // Android ROOM 의 경우, 기본타입이면 무조건 not null = true 처리..
    @ColumnInfo(name = "no")
    public Integer no;

    @ColumnInfo(name = "forpet_hash")
    @PrimaryKey
    @NonNull
    public String forpetHash;

    @Label("주소")
    @ItemView(idx = 4)
    @ColumnInfo(name = "address_name")
    public String addressName;

    @Label("상세주소")
    @ItemView(idx = 5)
    @Ignore
    public String addressNameDetail;

    @Label("서비스종류")
    @ItemView(idx = 0)
    @ColumnInfo(name = "category_group_code")
    public String categoryGroupCode;

    @ColumnInfo(name = "kakao_id")
    public String kakaoId;

    @Label("사업자번호")
    @ItemView(idx = 2)
    @Ignore
    public String businessNo;

    @Label("대표자이름")
    @ItemView(idx = 3)
    @Ignore
    public String ceoName;

    @Label("담당자이름")
    @ItemView(idx = 6)
    @Ignore
    public String chargeName;

    @Label("연락처")
    @ItemView(idx = 7)
    @ColumnInfo(name = "phone")
    public String phone;

    @Label("이메일")
    @ItemView(idx = 8)
    @Ignore
    public String email;

    @Label("이름")
    @ItemView(idx = 1)
    @ColumnInfo(name = "place_name")
    public String placeName;

    @Label("업체사진")
    @ItemView(idx = 9)
    @ColumnInfo(name = "place_image_url")
    public String placeImageUrl;

    @ColumnInfo(name = "homepage")
    public String homepage;

    @ColumnInfo(name = "road_address_name")
    public String roadAddressName;

    @ColumnInfo(name = "x")
    public Double x;

    @ColumnInfo(name = "y")
    public Double y;

    @Label("주차가능")
    @ColumnInfo(name = "opt_parking")
    public String optParking;

    @Label("예약가능")
    @ColumnInfo(name = "opt_reservation")
    public String optReservation;

    @Label("휴일영업")
    @ColumnInfo(name = "opt_3")
    public String opt3;

    @Label("주차가능")
    @ColumnInfo(name = "opt_4")
    public String opt4;

    @Label("배달가능")
    @ColumnInfo(name = "opt_5")
    public String opt5;

    @Label("Wifi")
    @ColumnInfo(name = "opt_6")
    public String opt6;

    @Label("추가옵션")
    @ColumnInfo(name = "opt_7")
    public String opt7;

    @ColumnInfo(name = "event")
    public String event;

    @ColumnInfo(name = "dc")
    public String dc;

    @ColumnInfo(name = "forpet_recommend")
    public String forpetRecommend;

    @ColumnInfo(name = "love_point")
    public String lovePoint;

    @Label("업체소개")
    @ItemView(idx = 10)
    @ColumnInfo(name = "intro")
    public String intro;
}