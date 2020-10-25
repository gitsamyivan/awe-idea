package awe.idea.com.api.test;

import awe.idea.com.api.controller.IndexController;
import awe.idea.com.api.jwt.JwtToken;
import awe.idea.com.common.models.api.UserLoginReq;
import awe.idea.com.api.jwt.JwtUtils;
import awe.idea.com.common.security.AESUtil;
import awe.idea.com.common.utils.HttpClientUtils;
import awe.idea.com.common.utils.SpringContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

//使用junit4进行测试,无法加载spring-mvc.xml，没有分开mvc和spring
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-mvc.xml"})
public class LoginControllerTest {

    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void LoginTest() throws Exception{
        UserLoginReq userLoginReq = UserLoginReq.builder()
                .username("mark")
                .password("admin")
                .client("android")
                .version("1.0")
                .platform("awe")
                .captcha("xgw22")
                .build();
        String json = mapper.writeValueAsString(userLoginReq);
        System.out.println(json);
        String encryptJson = AESUtil.encrypt2hex(json);
        System.out.println(encryptJson);

        String url = "http://localhost:8081/api/v1/login";
        String params = "requestData="+encryptJson;
        String postResult = HttpClientUtils.postParameters(url,params);
//        String postResult = HttpClientUtils.postJsonParameters(url,params);
        System.out.println("result: "+postResult);
    }


    @Test
    public void LoginOutTest() throws Exception{
        String url = "http://localhost:8081/api/v1/logout";
//        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDMxODQ4NzAsImlhdCI6MTYwMjU4MDA3MCwidXNlcmluZm8iOiJ7XCJwbGF0Zm9ybVwiOlwiYXdlXCIsXCJjbGllbnRcIjpcImFuZHJvaWRcIixcInZlcnNpb25cIjpcIjEuMFwiLFwidXNlcm5hbWVcIjpcIm1hcmtcIixcInRva2VuXCI6XCJlODg2OTgwMzA3YzE0NTAwODQwODBjZThmZTg3ZmI4MlwifSJ9.AY1CzZSQOzn0Ffjv7bjJSY52KvNe3So2QFJCfY-Saj4";
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDMxODUxNTMsImlhdCI6MTYwMjU4MDM1MywidXNlcmluZm8iOiJ7XCJwbGF0Zm9ybVwiOlwiYXdlXCIsXCJjbGllbnRcIjpcImFuZHJvaWRcIixcInZlcnNpb25cIjpcIjEuMFwiLFwidXNlcm5hbWVcIjpcIm1hcmtcIixcInRva2VuXCI6XCJiZDU0MzUwODExMjc0ODc2OWMzYmVkZmIyZDkxNjRhZVwifSJ9.OfAi0Wus32IKY-G_brkNoZWo1nq7bqcUdcjCcc9nsBo";
        Map<String,String> paramMap = new HashMap<>();
        Map<String,String> headMap = new HashMap<>();
        headMap.put("Authorization",jwtToken);
        String postResult = HttpClientUtils.postParameters(url,paramMap,headMap);
        System.out.println("result: "+postResult);
    }

    @Test
    public void GetUserInfoTest()throws Exception{
        String url = "http://localhost:8081/api/v1/getUserInfo";
//        String url = "http://localhost:8081/api/v1/getUserInfo2";
//        String url = "http://localhost:8081/api/v1/getJwtToken";
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBUElVU0VSSU5GTyI6IntcInBsYXRmb3JtXCI6XCJhd2VcIixcImNsaWVudFwiOlwiYW5kcm9pZFwiLFwidmVyc2lvblwiOlwiMS4wXCIsXCJ1c2VybmFtZVwiOlwibWFya1wiLFwidG9rZW5cIjpcIjcxNTdhMzUwZTcyZDQ4Njc5OGY1ZDRjN2QwOTgxZDlkXCJ9IiwiZXhwIjoxNjAzMTk0MzY1LCJpYXQiOjE2MDI1ODk1NjV9.ZOqDcORIZSZQkBKdTQqcX5gagwTRxxV_9cTR5UXgQCA";
        Map<String,String> paramMap = new HashMap<>();
        Map<String,String> headMap = new HashMap<>();
        headMap.put("Authorization",jwtToken);
        String postResult = HttpClientUtils.postParameters(url,paramMap,headMap);
        System.out.println("result: "+postResult);
    }

    @Test
    public void JwtTokenTest()throws Exception{
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDMxODQ0OTEsImlhdCI6MTYwMjU3OTY5MSwidXNlcmluZm8iOiJ7XCJwbGF0Zm9ybVwiOlwic2hvcFwiLFwiY2xpZW50XCI6XCJhbmRyb2lkXCIsXCJ2ZXJzaW9uXCI6XCIxLjEwXCIsXCJ1c2VybmFtZVwiOlwiaXZhblwiLFwidG9rZW5cIjpcImYxODU0MjAyMmI5NDRiYTc5ZjhlYjFhOWMxYjIzMzgwXCJ9In0.mFsChmH5_Y9RxwjdokrV4gaCyHaUhBNlmK6GXakGo7k";
        JwtToken jwtToken = JwtUtils.getJwtToken(token);
        System.out.println(mapper.writeValueAsString(jwtToken));
    }

    @Test
    public void JwtTokenDecode()throws Exception{
        String jwtToken = "A7F9DCB58ADE6D9E902F8CFDF4D7FB978D43CFCF6D96C8D8AF9EDC35CF31BAC5BF3085A32D2F3965C4BC3B239CD1AD15F6B2354089316AC5FA8692715213C62661F784E77E3F7B24D1D4C8184EB944AA0753419AF555DD903D82CF5157CCE72478DD7DEDD2A6B41BCFA377F126AC0CA7117DBA09BF4F50611607D8C5F2E63B462451A97F45597C3A6600BE2B4A39C321DC1C347FB127AFC01E764B413DE256E45A2584BF181621F322F58A00EB4FA073F6A780D636CBE40D72EE2D4EF832CE02244254950577369CA054E5CD45BA16F56799AA4CDC243B4CED59B435C6430DE1DDD88E5D02A58AF4DF9D4AC598F0E1CD80A3B0D5A62A7231EFE4E5A09EF95D3403263BD2487AD8847875EB032B86CF6F144304F54FB711667023EF32CA5940941A81CC6AC50B862319DB687C21D92F98055C24614497410DA5046B29FEE46B858EC6E1025D057C121849492757B1B4FC863823D4D7A56276CD20F74849F8329FB40D1EA0C9F0471332F92AEF0A4C08EA";
        System.out.println(AESUtil.hex2decrypt(jwtToken));
    }

    @Test
    public void TestSpring(){
        IndexController indexController = SpringContextUtils.getBean(IndexController.class);
        System.out.println(indexController);
    }
}
