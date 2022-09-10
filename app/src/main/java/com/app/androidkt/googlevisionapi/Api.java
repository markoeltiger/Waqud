package com.app.androidkt.googlevisionapi;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    @Multipart
    @POST("petrol")
    Call<WaqudResponsePojo> addvalues(
                                      @Part("carId") RequestBody carid,
                                      @Part("companyId") RequestBody companyId,
                                      @Part("litre") RequestBody litre,
                                      @Part("ekramyat") RequestBody ekramyas,
                                      @Part("all_costs") RequestBody all_costs,
                                      @Part("pound") RequestBody costs,
                                      @Part("user_id") RequestBody user_id,

                                      @Part("all_kilometers") RequestBody all_kilometers,
@Part MultipartBody.Part image



    );
}
