package com.juli.imageloaderdemo.model.api.apiservice;


import com.juli.imageloaderdemo.model.entity.ImageEntity;
import com.juli.imageloaderdemo.model.entity.ResultMode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by stormbaron on 17-6-26.
 */

public interface GankApiService {
    @GET("福利/count/{page}/page/{number}")
    Call<ResultMode<ImageEntity>> getImageList(@Path("page") int page, @Path("number") int number);

}
