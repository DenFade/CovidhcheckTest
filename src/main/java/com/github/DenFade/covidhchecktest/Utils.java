package com.github.DenFade.covidhchecktest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    private Utils(){

    }

    public static String cookieParser(List<String> list){
        return list.stream().map(v -> v.split(";")[0]).collect(Collectors.joining(";"));
    }

    public static String bodyParser(Map<String, String> map){
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> sb.append("&").append(k).append("=").append(v));
        return sb.substring(1);
    }
}