package awe.idea.com.gen;

import awe.idea.com.gen.genservice.SysGeneratorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        SysGeneratorService service = applicationContext.getBean(SysGeneratorService.class);

//        String[] tableNames = new String[]{"tb_user","tb_account"};
//        String[] tableNames = new String[]{"schedule_job","schedule_job_log"};
//        String[] tableNames = new String[]{"tb_user","tb_account","schedule_job","schedule_job_log","sys_log","sys_menu","sys_role","sys_role_menu","sys_user","sys_user_role"};
        String[] tableNames = new String[]{"sys_config"};
        //当前工程物理路径
        String rootPath = System.getProperty("user.dir") + "\\awe-service\\src" ;
//        String rootPath = System.getProperty("user.dir") + "\\awe-gen\\src" ;
        service.generatorCodeFile(tableNames,rootPath);

        System.out.println("success");
    }


    /**
     * 可直接生成代码到service项目中
     */
    public static void serviceGen(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        SysGeneratorService service = applicationContext.getBean(SysGeneratorService.class);

        String[] tableNames = new String[]{"tb_user","tb_account","schedule_job","schedule_job_log","sys_log","sys_menu","sys_role","sys_role_menu","sys_user","sys_user_role"};
        //当前工程物理路径
        String rootPath = System.getProperty("user.dir") + "\\awe-service\\src" ;
        service.generatorCodeFile(tableNames,rootPath);

        System.out.println("success");
    }
}
