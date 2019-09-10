package kr.forpet.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

import kr.forpet.annotation.Label;

@Entity(tableName = "forpet_shop",
        indices = {
                @Index(name = "idx_shop_category", value = {"category_group_code"}),
                @Index(name = "idx_shop_name", value = {"place_name"}),
                @Index(name = "idx_shop_x", value = {"x"}),
                @Index(name = "idx_shop_y", value = {"y"})
        }
)
public class Shop {

    public enum CategoryGroupCode {
        SHOP, PHARM, HOSPITAL, DOGPENSION, DOGGROUND, DOGCAFE, CATCAFE, BEAUTY;

        public static CategoryGroupCode compare(String str) {
            return valueOf(str.toUpperCase());
        }
    }

    // ROOM 의 경우, 기본타입 이면 notNull = true..
    @ColumnInfo(name = "no")
    private Integer no;

    // @ColumnInfo 뒤 @PrimaryKey, @NonNull 적용
    @ColumnInfo(name = "forpet_hash")
    @PrimaryKey
    @NonNull
    private String forpetHash;

    @ColumnInfo(name = "address_name")
    @Label("주소")
    private String addressName;

    @Ignore
    @Label("상세주소")
    private String addressNameDetail;

    @ColumnInfo(name = "category_group_code")
    @Label("서비스종류")
    private String categoryGroupCode;

    @ColumnInfo(name = "kakao_id")
    private String kakaoId;

    @ColumnInfo(name = "phone")
    @Label("연락처")
    private String phone;

    @ColumnInfo(name = "place_name")
    @Label("이름")
    private String placeName;

    @ColumnInfo(name = "place_image_url")
    @Label("업체사진")
    private String placeImageUrl;

    @ColumnInfo(name = "homepage")
    private String homepage;

    @ColumnInfo(name = "road_address_name")
    private String roadAddressName;

    @ColumnInfo(name = "x")
    private Double x;

    @ColumnInfo(name = "y")
    private Double y;

    @ColumnInfo(name = "opt_parking")
    @Label("주차")
    private String optParking;

    @ColumnInfo(name = "opt_reservation")
    @Label("예약")
    private String optReservation;

    @ColumnInfo(name = "opt_wifi")
    @Label("와이파이")
    private String optWifi;

    @ColumnInfo(name = "opt_365")
    @Label("연중무휴")
    private String opt365;

    @ColumnInfo(name = "opt_night")
    @Label("야간")
    private String optNight;

    @ColumnInfo(name = "opt_shop")
    @Label("용품판매")
    private String optShop;

    @ColumnInfo(name = "opt_beauty")
    @Label("미용")
    private String optBeauty;

    @ColumnInfo(name = "opt_bigdog")
    @Label("대형견가능")
    private String optBigdog;

    @ColumnInfo(name = "opt_hotel")
    @Label("호텔")
    private String optHotel;

    @ColumnInfo(name = "event")
    private String event;

    @ColumnInfo(name = "dc")
    private String dc;

    @ColumnInfo(name = "forpet_recommend")
    private String forpetRecommend;

    @ColumnInfo(name = "love_point1")
    private Integer lovePoint1;

    @ColumnInfo(name = "love_point2")
    private Integer lovePoint2;

    @ColumnInfo(name = "love_point3")
    private Integer lovePoint3;

    @ColumnInfo(name = "love_point4")
    private Integer lovePoint4;

    @ColumnInfo(name = "love_point5")
    private Integer lovePoint5;

    @ColumnInfo(name = "intro")
    private String intro;

    @ColumnInfo(name = "additional_info")
    private String additionalInfo;

    @ColumnInfo(name = "last_updated")
    private String lastUpdated;

    @Ignore
    private List<OpenTime> openTimeList;

    @Ignore
    private float distance;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getOptWifi() {
        return optWifi;
    }

    public void setOptWifi(String optWifi) {
        this.optWifi = optWifi;
    }

    public String getOpt365() {
        return opt365;
    }

    public void setOpt365(String opt365) {
        this.opt365 = opt365;
    }

    public String getOptNight() {
        return optNight;
    }

    public void setOptNight(String optNight) {
        this.optNight = optNight;
    }

    public String getOptShop() {
        return optShop;
    }

    public void setOptShop(String optShop) {
        this.optShop = optShop;
    }

    public String getOptBeauty() {
        return optBeauty;
    }

    public void setOptBeauty(String optBeauty) {
        this.optBeauty = optBeauty;
    }

    public String getOptBigdog() {
        return optBigdog;
    }

    public void setOptBigdog(String optBigdog) {
        this.optBigdog = optBigdog;
    }

    public String getOptHotel() {
        return optHotel;
    }

    public void setOptHotel(String optHotel) {
        this.optHotel = optHotel;
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

    public Integer getLovePoint1() {
        return lovePoint1;
    }

    public void setLovePoint1(Integer lovePoint1) {
        this.lovePoint1 = lovePoint1;
    }

    public Integer getLovePoint2() {
        return lovePoint2;
    }

    public void setLovePoint2(Integer lovePoint2) {
        this.lovePoint2 = lovePoint2;
    }

    public Integer getLovePoint3() {
        return lovePoint3;
    }

    public void setLovePoint3(Integer lovePoint3) {
        this.lovePoint3 = lovePoint3;
    }

    public Integer getLovePoint4() {
        return lovePoint4;
    }

    public void setLovePoint4(Integer lovePoint4) {
        this.lovePoint4 = lovePoint4;
    }

    public Integer getLovePoint5() {
        return lovePoint5;
    }

    public void setLovePoint5(Integer lovePoint5) {
        this.lovePoint5 = lovePoint5;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public List<OpenTime> getOpenTimeList() {
        return openTimeList;
    }

    public void setOpenTimeList(List<OpenTime> openTimeList) {
        this.openTimeList = openTimeList;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}