package kr.forpet.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import kr.forpet.annotation.ItemView;
import kr.forpet.annotation.Label;

@Entity(tableName = "forpet_shop",
        indices = {
                @Index(name = "idx_shop_category", value = {"category_group_code"}),
                @Index(name = "idx_shop_name", value = {"place_name"}),
                @Index(name = "idx_shop_x", value = {"x"}),
                @Index(name = "idx_shop_hash", value = {"forpet_hash"}),
                @Index(name = "idx_shop_y", value = {"y"})
        }
)
public class ForpetShop {

    public enum CatCode {
        SHOP, PHARM, HOSPITAL, DOGPENSION, DOGGROUND, DOGCAFE, CATCAFE, BEAUTY;

        public static CatCode compare(String str) {
            return valueOf(str.toUpperCase());
        }
    }

    // ROOM 의 경우, 기본타입 이면 notNull = true..
    @ColumnInfo(name = "no")
    private Integer no;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "forpet_hash")
    private String forpetHash;

    @Label("주소")
    @ItemView(idx = 4)
    @ColumnInfo(name = "address_name")
    private String addressName;

    @Label("상세주소")
    @ItemView(idx = 5)
    @Ignore
    private String addressNameDetail;

    @Label("서비스종류")
    @ItemView(idx = 0)
    @ColumnInfo(name = "category_group_code")
    private String categoryGroupCode;

    @ColumnInfo(name = "kakao_id")
    private String kakaoId;

    @Label("사업자번호")
    @ItemView(idx = 2)
    @Ignore
    private String businessNo;

    @Label("대표자이름")
    @ItemView(idx = 3)
    @Ignore
    private String ceoName;

    @Label("담당자이름")
    @ItemView(idx = 6)
    @Ignore
    private String chargeName;

    @Label("연락처")
    @ItemView(idx = 7)
    @ColumnInfo(name = "phone")
    private String phone;

    @Label("이메일")
    @ItemView(idx = 8)
    @Ignore
    private String email;

    @Label("이름")
    @ItemView(idx = 1)
    @ColumnInfo(name = "place_name")
    private String placeName;

    @Label("업체사진")
    @ItemView(idx = 9)
    @ColumnInfo(name = "place_image_url")
    private String placeImageUrl;

    @ColumnInfo(name = "homepage")
    private String homepage;

    @ColumnInfo(name = "road_address_name")
    private String roadAddressName;

    @ColumnInfo(name = "x")
    private Double x;

    @ColumnInfo(name = "y")
    private Double y;

    @Label("주차가능")
    @ColumnInfo(name = "opt_parking")
    private String optParking;

    @Label("예약가능")
    @ColumnInfo(name = "opt_reservation")
    private String optReservation;

    @Label("휴일영업")
    @ColumnInfo(name = "opt_3")
    private String opt3;

    @Label("주차가능")
    @ColumnInfo(name = "opt_4")
    private String opt4;

    @Label("배달가능")
    @ColumnInfo(name = "opt_5")
    private String opt5;

    @Label("Wifi")
    @ColumnInfo(name = "opt_6")
    private String opt6;

    @Label("추가옵션")
    @ColumnInfo(name = "opt_7")
    private String opt7;

    @ColumnInfo(name = "event")
    private String event;

    @ColumnInfo(name = "dc")
    private String dc;

    @ColumnInfo(name = "forpet_recommend")
    private String forpetRecommend;

    @ColumnInfo(name = "love_point")
    private String lovePoint;

    @Label("업체소개")
    @ItemView(idx = 10)
    @ColumnInfo(name = "intro")
    private String intro;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    @NonNull
    public String getForpetHash() {
        return forpetHash;
    }

    public void setForpetHash(@NonNull String forpetHash) {
        this.forpetHash = forpetHash;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressNameDetail() {
        return addressNameDetail;
    }

    public void setAddressNameDetail(String addressNameDetail) {
        this.addressNameDetail = addressNameDetail;
    }

    public String getCategoryGroupCode() {
        return categoryGroupCode;
    }

    public void setCategoryGroupCode(String categoryGroupCode) {
        this.categoryGroupCode = categoryGroupCode;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceImageUrl() {
        return placeImageUrl;
    }

    public void setPlaceImageUrl(String placeImageUrl) {
        this.placeImageUrl = placeImageUrl;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getRoadAddressName() {
        return roadAddressName;
    }

    public void setRoadAddressName(String roadAddressName) {
        this.roadAddressName = roadAddressName;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getOptParking() {
        return optParking;
    }

    public void setOptParking(String optParking) {
        this.optParking = optParking;
    }

    public String getOptReservation() {
        return optReservation;
    }

    public void setOptReservation(String optReservation) {
        this.optReservation = optReservation;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getOpt5() {
        return opt5;
    }

    public void setOpt5(String opt5) {
        this.opt5 = opt5;
    }

    public String getOpt6() {
        return opt6;
    }

    public void setOpt6(String opt6) {
        this.opt6 = opt6;
    }

    public String getOpt7() {
        return opt7;
    }

    public void setOpt7(String opt7) {
        this.opt7 = opt7;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getForpetRecommend() {
        return forpetRecommend;
    }

    public void setForpetRecommend(String forpetRecommend) {
        this.forpetRecommend = forpetRecommend;
    }

    public String getLovePoint() {
        return lovePoint;
    }

    public void setLovePoint(String lovePoint) {
        this.lovePoint = lovePoint;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}