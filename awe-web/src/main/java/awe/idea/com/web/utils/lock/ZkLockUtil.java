package awe.idea.com.web.utils.lock;

import awe.idea.com.common.utils.CacheKey;
import awe.idea.com.common.utils.PropertiesUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

public class ZkLockUtil{
	private static Logger logger= LoggerFactory.getLogger(ZkLockUtil.class);
	@Value("${zookeeper.address}")
	private static String address;
	
	public static CuratorFramework client;

	static{
		try {
			address = PropertiesUtils.getInstance().get("zookeeper.address");
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
			client = CuratorFrameworkFactory.newClient(address, retryPolicy);
			client.start();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ZkLockUtil init error.",e);
		}
	}

	public static String generateLockKey(String lockKey){
		return  CacheKey.ZookeeperCacheKey.WEB_LOCK_PROFIT + lockKey;
	}

    //获得了锁
    public static void acquire(String lockKey){
    	try {
    		InterProcessMutex mutex = new InterProcessMutex(client, lockKey);
    		mutex.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    //时效锁
    public static boolean acquire(String lockKey,long time){
    	try {
    		InterProcessMutex mutex = new InterProcessMutex(client, lockKey);
    		return mutex.acquire(time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
    }
    
    //释放锁
    public static void release(String lockKey){
    	try {
    		InterProcessMutex mutex = new InterProcessMutex(client, lockKey);
    		mutex.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}  
