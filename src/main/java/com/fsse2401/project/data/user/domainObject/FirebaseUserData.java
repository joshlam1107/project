package com.fsse2401.project.data.user.domainObject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Getter @Setter
public class FirebaseUserData {
    private String firebaseUid;
    private String email;

    public FirebaseUserData(JwtAuthenticationToken token){
        this.firebaseUid = (String)token.getTokenAttributes().get("user_id");
        this.email = (String)token.getTokenAttributes().get("email");
    }
}