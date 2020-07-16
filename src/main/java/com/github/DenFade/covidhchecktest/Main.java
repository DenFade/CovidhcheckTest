package com.github.DenFade.covidhchecktest;

public class Main {

    public static void main(String[] args){
        HcheckClient client = new HcheckClient(args[0], args[1], args[2], args[3], args[4]);
        HcheckRequest request = new HcheckRequest(client);

        request.enter()
                .authorize()
                .submit();
    }
}
