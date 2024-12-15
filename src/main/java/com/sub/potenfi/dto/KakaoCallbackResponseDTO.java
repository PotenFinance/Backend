package com.sub.potenfi.dto;

public class KakaoCallbackResponseDTO {
    private String id;               // 사용자 ID
    private String connectedAt;      // 연결된 시간
    private String nickname;         // 사용자 닉네임
    private String email;            // 이메일
    private String accessToken;      // 액세스 토큰
    private String refreshToken;     // 리프레시 토큰

    // 생성자
    public KakaoCallbackResponseDTO(String id, String connectedAt, String nickname, String email,
                                    String accessToken, String refreshToken) {
        this.id = id;
        this.connectedAt = connectedAt;
        this.nickname = nickname;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getConnectedAt() { return connectedAt; }
    public void setConnectedAt(String connectedAt) { this.connectedAt = connectedAt; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
