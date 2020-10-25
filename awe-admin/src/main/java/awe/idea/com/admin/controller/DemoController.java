package awe.idea.com.admin.controller;

import awe.idea.com.admin.models.TaskJob;
import awe.idea.com.admin.utils.lock.ZkLockExecutor;
import awe.idea.com.admin.utils.queue.jvm.TaskQueue;
import awe.idea.com.common.utils.GenerateUtil;
import awe.idea.com.common.utils.R;
import awe.idea.com.service.entity.SysRoleEntity;
import awe.idea.com.service.service.SysRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class DemoController {
    private static final Logger log= LoggerFactory.getLogger(DemoController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/zookrun")
    @ResponseBody
    public R zookrun(){
        ZkLockExecutor.ZkRunable zkRunable = new ZkLockExecutor.ZkRunable() {
            @Override
            public R run() throws Exception {
                log.info("zookeeper run instance");
                try {
                    Thread.sleep(5000);
                }catch (Exception e){

                }
                return R.ok();
            }
        };
        return  ZkLockExecutor.execute("admin_demo",1,zkRunable);
    }

    @RequestMapping("/jvmtask")
    @ResponseBody
    public R jvmtask(){
        TaskJob killed = TaskJob.builder()
                .orderId(GenerateUtil.randomUUID()).username("admin").build();
        try {
            TaskQueue.getMailQueue().produce(killed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("success");
    }

    @RequestMapping("/update")
    public R update(Long roleId) throws Exception{
        SysRoleEntity roleEntity = sysRoleService.queryObject(roleId);
        String randomText = UUID.randomUUID().toString();
        List<Long> menuList = Arrays.asList(new Long[]{76l,66l,70l,69l});

        roleEntity.setCreateTime(new Date());
        roleEntity.setRemark(randomText);
        roleEntity.setMenuIdList(menuList);

        System.out.println("update : " + objectMapper.writeValueAsString(roleEntity));

        sysRoleService.update(roleEntity);
        return R.ok().put("data",roleEntity);
    }
}
