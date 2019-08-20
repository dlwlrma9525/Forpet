package kr.forpet.data;

import kr.forpet.annotation.ItemView;
import kr.forpet.annotation.Label;

public class PetShop {
    private String forpetHash;

    @Label("주소")
    @ItemView(idx = 4)
    private String addressName;

    @Label("상세주소")
    @ItemView(idx = 5)
    private String addressNameDetail;

    @Label("서비스종류")
    @ItemView(idx = 0)
    private String categoryGroupCode;

    private String categoryGroupName;

    private String categoryName;

    private String distance;

    private int kakaoId;

    @Label("사업자번호")
    @ItemView(idx = 2)
    private String businessNo;

    @Label("대표자이름")
    @ItemView(idx = 3)
    private String ceoName;

    @Label("담당자이름")
    @ItemView(idx = 6)
    private String chargeName;

    @Label("연락처")
    @ItemView(idx = 7)
    private String phone;

    @Label("이메일")
    @ItemView(idx = 8)
    private String email;

    @Label("이름")
    @ItemView(idx = 1)
    private String placeName;

    private String placeUrl;

    @Label("업체사진")
    @ItemView(idx = 9)
    private String placeImageUrl;

    private String homepage;

    private String roadAddressName;

    private double x;

    private double y;

    private String ykiho;

    private String yadmnm;

    private int starPoint;

    private int reviewCount;

    private int orgNo;

    @Label("예약가능")
    private String optParking;

    @Label("주차가능")
    private String optReservation;

    @Label("휴일영업")
    private String opt3;

    @Label("주차가능")
    private String opt4;

    @Label("배달가능")
    private String opt5;

    @Label("Wifi")
    private String opt6;

    @Label("추가옵션")
    private String opt7;

    private String event;

    private String dc;

    private String forpetRecommend;

    private int lovePoint;

    @Label("업체소개")
    @ItemView(idx = 10)
    private String intro;
}