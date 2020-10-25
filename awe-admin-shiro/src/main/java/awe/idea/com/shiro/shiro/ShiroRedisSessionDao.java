package awe.idea.com.shiro.shiro;

import awe.idea.com.shiro.sso.ILoginSSOUtil;
import awe.idea.com.shiro.utils.RedisUtil;
import awe.idea.com.common.utils.CacheKey;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;

/**
 * 开启redis缓存shiro后的sessionManager管理
 */
@Component
public class ShiroRedisSessionDao extends EnterpriseCacheSessionDAO {
    @Resource
    private RedisUtil redisUtil;
    @Resource(name = "loginSSOInstance")
    private ILoginSSOUtil loginSSOUtil;

    Logger log= LoggerFactory.getLogger(getClass());
    @Override
    public void update(Session session) throws UnknownSessionException {
        log.info("更新seesion,id=[{}]",session.getId().toString());
        try {
            redisUtil.set(session.getId().toString(),session,ShiroConfig.SESSION_EXPIRE_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Session session) {
        log.info("删除seesion,id=[{}]",session.getId().toString());
        try {
            String username = (String) session.getAttribute(CacheKey.SessionKey.ADMIN_SESSION_USER);
            String sessionId = session.getId().toString();
            if(StringUtils.isNotEmpty(username)){
                loginSSOUtil.removeSsoInfo(sessionId,username);
            }
            redisUtil.delete(session.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        log.info("创建seesion,id=[{}]",session.getId().toString());
        try {
            redisUtil.set(sessionId.toString(),session,ShiroConfig.SESSION_EXPIRE_TIME);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("获取seesion,id=[{}]",sessionId.toString());
        Session session = null;
        try {
            session = (Session) redisUtil.get(sessionId.toString(),ShiroConfig.SESSION_EXPIRE_TIME);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return session;
    }

    // 把session对象转化为byte保存到redis中
    public byte[] sessionToByte(Session session){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutput oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 把byte还原为session
    public Session byteToSession(byte[] bytes){
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
    }

}