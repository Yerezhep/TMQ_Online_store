package kz.tmq.tmq_online_store.auth.util;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class CookieUtils {

    @Value("${cookie.jwt.name}")
    private String cookieJwtName;

    @Value("${cookie.jwt.max-age}")
    private long cookieJwtDuration;

    @Value("${cookie.domain}")
    private String cookieJwtDomain;

    public HttpCookie createJwtCookie(String token) {
        return ResponseCookie.from(cookieJwtName, token)
                .maxAge(cookieJwtDuration)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .domain(cookieJwtDomain)
                .path("/")
                .build();
    }

    public String getCookieJwt(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(cookieJwtName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public HttpCookie deleteJwtCookie() {
        return ResponseCookie.from(cookieJwtName, null)
                .maxAge(0)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .domain(cookieJwtDomain)
                .path("/")
                .build();
    }

}
