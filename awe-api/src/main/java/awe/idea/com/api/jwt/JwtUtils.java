package awe.idea.com.api.jwt;

import awe.idea.com.api.config.ErrorCode;
import awe.idea.com.api.config.Constants;
import awe.idea.com.common.utils.GenerateUtil;
import awe.idea.com.common.utils.RRException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	private static final String JWT_USERINF = "APIUSERINFO";
    private static final String salt = "f324cbc272096e0550a829d513ce4fbd"; //必须16位
	/**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,expireTime后过期
     * @param username 用户名
     * @param time 过期时间s
     * @return 加密的token
     */
    public static String sign(String username, String salt, long time) {
        try {
            Date date = new Date(System.currentTimeMillis()+time*1000);
            Algorithm algorithm = Algorithm.HMAC256(salt);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static JwtToken getJwtToken(String token) throws Exception {
        try {
            Date now = Calendar.getInstance().getTime();
            DecodedJWT jwt = JWT.decode(token);
            if(jwt.getExpiresAt().before(now)){
                throw new RRException("身份凭证已过期，请重新登录。", ErrorCode.TOKEN_FAIL.getCode());
            }
            String userinfo = jwt.getClaim(JWT_USERINF).asString();
            return jsonMapper.readValue(userinfo,JwtToken.class);
        }catch (RRException e){
            throw e;
        }catch (Exception e) {
            logger.error("getJwtToken error.",e);
            throw new RRException("身份凭证错误，请重新登录。", ErrorCode.TOKEN_FAIL.getCode());
        }
    }

    public static String signJwtToken(JwtToken jwtToken) throws Exception{
        try {
            String jwtTokenStr = jsonMapper.writeValueAsString(jwtToken);
            Date date = new Date(System.currentTimeMillis()+ Constants.TOKEN_EXPIRED_TIME);
            Algorithm algorithm = Algorithm.HMAC256(salt);
            // 附带username信息
            return JWT.create()
                    .withClaim(JWT_USERINF, jwtTokenStr)
                    .withExpiresAt(date)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } catch (Exception e) {
            logger.error("signJwtToken error.",e);
            throw new RRException("身份凭证错误，请重新登录。");
        }
    }

    /**
     * token是否过期
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * 生成随机盐,长度32位
     * @return
     */
    public static String generateSalt(){
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(16).toHex();
        return hex;
    }

    public static void main(String[] args) throws  Exception{
//        System.out.println(generateSalt());
        JwtToken baseParam = JwtToken.builder()
                .platform("shop").client("android")
                .token(GenerateUtil.randomUUID())
                .username("ivan").version("1.10")
                .build();
        String token = signJwtToken(baseParam);
        System.out.println(token);
        System.out.println(token.length());

        baseParam = getJwtToken(token);
        System.out.println(jsonMapper.writeValueAsString(baseParam));

        System.out.println(isTokenExpired(token));

    }

}
