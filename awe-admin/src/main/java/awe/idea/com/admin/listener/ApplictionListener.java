package awe.idea.com.admin.listener;

import awe.idea.com.admin.utils.queue.jvm.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplictionListener implements ServletContextListener {
    private static final Logger logger= LoggerFactory.getLogger(ApplictionListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("ApplictionListener contextInitialized");
//        TaskRunner.run();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("ApplictionListener contextDestroyed");
//        TaskRunner.shutdown();
    }
}
