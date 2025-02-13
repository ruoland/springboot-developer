package land.land.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import land.land.domain.OAuthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f812b8ebd359ba278d52c403978e88f6");
        params.add("redirect_uri", "http://localhost:8080/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoToToken = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST, kakaoToToken, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            OAuthToken token = objectMapper.readValue(response.getBody(), OAuthToken.class);
            return ""+ asdf(token);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response.getBody();
    }

    public ResponseEntity<String> asdf(OAuthToken oAuthToken){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer"+oAuthToken.getAccess_token());
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoToToken = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST, kakaoToToken, String.class);
        return response;
    }


}