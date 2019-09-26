package kr.forpet.network.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ForpetApiService {

    @FormUrlEncoded
    @POST("forpet_request_v1.php?apitype=registdevice")
    Call<ResponseBody> registDevice(@Field("user_id") String userId, @Field("fcm_token") String fcmToken, @Field("device_type") String deviceType);

    @Multipart
    @POST("https://forpets.kr/api/forpet_request_v1.php?apitype=satisfaction")
    Call<ResponseBody> satisfaction(
            @Part("user_id") RequestBody userId,
            @Part("forpet_hash") RequestBody forpetHash,
            @Part("point1") RequestBody point1,
            @Part("point2") RequestBody point2,
            @Part("point3") RequestBody point3,
            @Part("point4") RequestBody point4,
            @Part("point5") RequestBody point5,
            @Part("comment") RequestBody comment,
            @Part MultipartBody.Part fileImage
    );
}
