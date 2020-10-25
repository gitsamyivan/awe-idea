package awe.idea.com.test.gen;

import awe.idea.com.gen.genservice.SysGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestApplication {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws  Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        SysGeneratorService service = applicationContext.getBean(SysGeneratorService.class);
        Map<String, String> userMap = service.queryTable("tb_user");
        for (Map.Entry<String, String> inner : userMap.entrySet()) {
            System.out.println(inner.getKey() + " - " + String.valueOf(inner.getValue()));
        }
    }
}
