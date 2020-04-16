package com.example.messenger_v11.SocketNetwork;

public class AuthService {



    String AuthResponse;


    private static final String AUTH_REQUEST =
            "{" +

                    "\"subType\":\"Request\"," +
                    "\"type\":\"Auth\"" +
                    "}";
    void checkAuth(){


    }





    public String getAuthResponse() {
        return AuthResponse;
    }

    public void setAuthResponse(String authResponse) {
        AuthResponse = authResponse;
    }


}
