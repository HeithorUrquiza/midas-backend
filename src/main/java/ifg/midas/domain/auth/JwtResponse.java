package ifg.midas.domain.auth;

public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter para o token
    public String getToken() {
        return token;
    }
}
