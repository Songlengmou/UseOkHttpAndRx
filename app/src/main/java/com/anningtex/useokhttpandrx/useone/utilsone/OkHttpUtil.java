package com.anningtex.useokhttpandrx.useone.utilsone;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @Author Song
 * @Desc:
 */
public class OkHttpUtil {

    public static void sendOkHttpRequest(String address, RequestBody requestBody, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
