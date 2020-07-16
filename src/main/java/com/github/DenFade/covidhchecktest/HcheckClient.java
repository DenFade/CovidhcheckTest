package com.github.DenFade.covidhchecktest;

import java.util.HashMap;
import java.util.Map;

public class HcheckClient {

    private final String schoolCode;
    private final String schoolName;
    private final String myName;
    private final String birthday;
    private final String localEdu;
    private final Map<String, String> cookies = new HashMap<>();

    public final static String contentType =  "application/x-www-form-urlencoded; charset=UTF-8";
    public final static String userAgent = "Mozilla/5.0 (Linux; Android 9; SM-J530S) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Mobile Safari/537.36";
    public final static String xReqWith = "XMLHttpRequest";
    public final static String referer = "https://eduro.%s.go.kr";
    public final static String acceptLang = "ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3";

    public HcheckClient(String schoolCode, String schoolName, String myName, String birthday, String localEdu){
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;
        this.myName = myName;
        this.birthday = birthday;
        this.localEdu = localEdu;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getMyName() {
        return myName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocalEdu() {
        return localEdu;
    }

    public String getReferer(){
        return String.format(referer, this.localEdu);
    }

    public void setCookie(String key, String value){
        cookies.put(key, value);
    }

    public String getCookie(String key){
        return cookies.get(key);
    }
}
