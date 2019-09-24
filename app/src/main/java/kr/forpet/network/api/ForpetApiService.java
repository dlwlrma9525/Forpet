package kr.forpet.network.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ForpetApiService {

    @FormUrlEncoded
    @POST("forpet_request_v1.php?apitype=registdevice")
    Call<ResponseBody> registDevice(@Field("user_id") String userId, @Field("fcm_token") String fcmToken, @Field("device_type") String deviceType);
}
