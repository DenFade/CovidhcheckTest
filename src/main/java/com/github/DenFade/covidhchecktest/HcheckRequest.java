package com.github.DenFade.covidhchecktest;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HcheckRequest {

    private final HcheckClient client;

    private String body;

    private static final String baseURL = "https://eduro.%s.go.kr";
    private static final String initURL = baseURL + "/hcheck/index.jsp";
    private static final String authURL = baseURL + "/stv_cvd_co00_012.do";
    private static final String finalURL = baseURL + "/stv_cvd_co01_000.do";

    public HcheckRequest(HcheckClient client){
        this.client = client;
    }

    public String getInitURL(){
        return String.format(initURL, client.getLocalEdu());
    }

    public String getAuthURL() {
        return String.format(authURL, client.getLocalEdu());
    }

    public String getSubmitURL(){
        return String.format(finalURL, client.getLocalEdu());
    }

    public HcheckRequest enter(){
        OkHttpClient ohc = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getInitURL())
                .get()
                .build();
        try{
            Response res = ohc.newCall(request).execute();
            String cookie = Utils.cookieParser(res.headers("set-cookie"));
            client.setCookie("Cookie", cookie);
        } catch (IOException e){
            e.printStackTrace();
        }
        return this;
    }

    @SuppressWarnings("all")
    public HcheckRequest authorize(){
        Map<String, String> params = new HashMap<>();
        params.put("qstnCrtfcNoEncpt", "");
        params.put("rtnRsltCode", "");
        params.put("schulCode", client.getSchoolCode());
        params.put("schulNm", client.getSchoolName());
        params.put("pName", client.getMyName());
        params.put("frnoRidno", client.getBirthday());
        params.put("aditCrtfcNo", "");

        OkHttpClient ohc = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.get(HcheckClient.contentType), Utils.bodyParser(params));
        Request request = new Request.Builder()
                .url(getAuthURL())
                .header("User-Agent", HcheckClient.userAgent)
                .header("X-Requested-With", HcheckClient.xReqWith)
                .header("Referer", client.getReferer())
                .header("Accept-Language", HcheckClient.acceptLang)
                .header("Cookie", client.getCookie("Cookie"))
                .post(requestBody)
                .build();
        try {
            Response res = ohc.newCall(request).execute();
            body = res.body().string();
        } catch (IOException e){
            e.printStackTrace();
        }
        return this;
    }

    public HcheckRequest submit(){
        JSONObject json = new JSONObject(body);
        String no_encpt = json.getJSONObject("resultSVO").getJSONObject("data").getString("qstnCrtfcNoEncpt");
        String result_code = "SUCCESS";

        Map<String, String> params = new HashMap<>();
        params.put("qstnCrtfcNoEncpt", no_encpt);
        params.put("rtnRsltCode", result_code);
        params.put("schulNm", "");
        params.put("stdntName", "");
        params.put("rspns01", "1");
        params.put("rspns02", "1");
        params.put("rspns07", "0");
        params.put("rspns08", "0");
        params.put("rspns09", "0");

        OkHttpClient ohc = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.get(HcheckClient.contentType), Utils.bodyParser(params));
        Request request = new Request.Builder()
                .url(getSubmitURL())
                .header("User-Agent", HcheckClient.userAgent)
                .header("X-Requested-With", HcheckClient.xReqWith)
                .header("Referer", client.getReferer())
                .header("Accept-Language", HcheckClient.acceptLang)
                .header("Cookie", client.getCookie("Cookie"))
                .post(requestBody)
                .build();
        try {
            Response res = ohc.newCall(request).execute();
            System.out.println(res.body().string());
        } catch (IOException e){
            e.printStackTrace();
        }
        return this;
    }
}
