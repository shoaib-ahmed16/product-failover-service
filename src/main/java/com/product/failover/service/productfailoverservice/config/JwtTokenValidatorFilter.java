package com.product.failover.service.productfailoverservice.config;

import com.product.failover.service.productfailoverservice.dto.enums.Constants;
import com.product.failover.service.productfailoverservice.exception.InvalidTokenIssuer;
import com.product.failover.service.productfailoverservice.exception.InvalidTokenSubject;
import com.product.failover.service.productfailoverservice.exception.JwtInValidToken;
import com.product.failover.service.productfailoverservice.exception.TokenExpired;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenValidatorFilter extends OncePerRequestFilter {


    @Value("${jwt.security.token.secret_key}")
    public String JWT_SECRET_KEY;


    @Value("${jwt.security.token.issuer}")
    public String jwtIssuer;

    @Value("${jwt.security.token.subject}")
    public String jwtSubject;


    @Value("${jwt.security.token.earlierBefore}")
    private Long earlierBefore;


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String header = req.getHeader(Constants.AUTHORIZATION.getValue());
        if (header != null && header.startsWith(Constants.TOKEN_PREFIX.getValue())) {
            String authToken = header.split(" ")[1];
            try {
                SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());
                Claims claims=getClaims(key,authToken);
                if(Objects.isNull(claims)){
                    log.error("Invalid Claim or null value for claim. Not a valid Generated Token");
                    throw new JwtInValidToken("Invalid Claim or null value for claim. Not a valid Generated Token.");
                }
                String username =String.valueOf(claims.get(Constants.USERNAME.getValue()));
                String authorities=String.valueOf(claims.get(Constants.AUTHORITIES.getValue()));
                if(!claims.getIssuer().equals(jwtIssuer)){
                    log.error("This Token is not issued By Elevate Mart Security Service");
                    throw new InvalidTokenIssuer("This Token is not issued By Elevate Mart Security Service");
                }
                if(!claims.getSubject().equals(jwtSubject)){
                    log.error("This Token is not issued By Elevate Mart Security Service");
                    throw new InvalidTokenSubject("This Token is not issued By Elevate Mart Security Service");
                }
                if(claims.getExpiration().before(new Date(new Date().getTime()-earlierBefore))){
                    log.error("This Token is expired. Please Login again!");
                    throw new TokenExpired("This Token is expired. Please Login again!");
                }
                Authentication auth= new UsernamePasswordAuthenticationToken(username,null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch(JwtInValidToken jwt) {
                throw new JwtInValidToken(jwt.getMessage());
            }catch (InvalidTokenIssuer | TokenExpired | InvalidTokenSubject inti){
                throw new InvalidTokenIssuer(inti.getMessage());
            } catch (Exception e){
                log.error("Authentication Failed. Username or Password not valid. Exception : {}",e.getMessage());
                throw new RuntimeException("Authentication Failed. Username or Password not valid. Exception: "+e.getMessage());
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }
        filterChain.doFilter(req, res);
    }

    private Claims getClaims(SecretKey key, String jwtToken){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
    }

}
