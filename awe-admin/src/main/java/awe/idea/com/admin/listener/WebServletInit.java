package awe.idea.com.admin.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


public class WebServletInit implements WebApplicationInitializer {
    private static final Logger logger= LoggerFactory.getLogger(ApplictionListener.class);
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("WebServletInit onStartup");
    }
}