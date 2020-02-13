package api.eatgoapi.filters;

import api.eatgoapi.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager, JwtUtil jwtUtil) {

        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        Authentication authentication = getAuthentication(request);
        if(authentication != null){
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication) ;
        }
        chain.doFilter(request, response);
    }

    //여기서 Authentication 은 예전과 달리 여기에서 사용하는 것
    private Authentication getAuthentication(HttpServletRequest request){


        //헤더에서 데이터 얻기
        String token = request.getHeader("Authorization");
        //TODO: JWT 분석
        if(token == null){
            return null;
        }

        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(claims, null);
        return authentication;
    }

}
