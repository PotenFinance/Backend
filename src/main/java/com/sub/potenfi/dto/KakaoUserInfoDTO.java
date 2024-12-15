package com.sub.potenfi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // 알 수 없는 필드를 무시
public class KakaoUserInfoDTO {
    private String id;

    @JsonProperty("connected_at")
    private String connectedAt;

    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(String connectedAt) {
        this.connectedAt = connectedAt;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public KakaoAccount getKakaoAccount() {
        return kakaoAccount;
    }

    public void setKakaoAccount(KakaoAccount kakaoAccount) {
        this.kakaoAccount = kakaoAccount;
    }

    @JsonIgnoreProperties(ignoreUnknown = true) // 알 수 없는 필드를 무시
    public static class Properties {
        private String nickname;

        // Getters and Setters
        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true) // 알 수 없는 필드를 무시
    public static class KakaoAccount {
        private String email;

        @JsonProperty("profile_nickname_needs_agreement")
        private boolean profileNicknameNeedsAgreement;

        // Getters and Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isProfileNicknameNeedsAgreement() {
            return profileNicknameNeedsAgreement;
        }

        public void setProfileNicknameNeedsAgreement(boolean profileNicknameNeedsAgreement) {
            this.profileNicknameNeedsAgreement = profileNicknameNeedsAgreement;
        }
    }
}
