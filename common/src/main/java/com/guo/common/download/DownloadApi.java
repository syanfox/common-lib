package com.guo.common.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;



public interface DownloadApi {

    /**
     * 下载app
     *
     * @return
     */
    @GET("")
    @Streaming
    Call<ResponseBody> download(
            @Url String url);
}
