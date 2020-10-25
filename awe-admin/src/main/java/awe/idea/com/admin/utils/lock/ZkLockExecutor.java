package awe.idea.com.admin.utils.lock;

import awe.idea.com.common.utils.R;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ZkLockExecutor {
    private static Logger logger= LoggerFactory.getLogger(ZkLockExecutor.class);

    public static R execute(String lockKey,long seconds,ZkRunable zkRunable){
        boolean locked = false;
        InterProcessMutex mutex = null;
        lockKey = ZkLockUtil.generateLockKey(lockKey);
        try {
            mutex = new InterProcessMutex(ZkLockUtil.client, lockKey);
            locked = mutex.acquire(seconds, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error(String.format("获取zk锁错误,key:%s，",lockKey),e);
            locked = true;
        }

        try {
            if(locked){
                return  zkRunable.run();
            }else{
                return  R.ok("获取zk锁失败，请稍后再试。");
            }
        }catch (Exception e){
            logger.error(String.format("释放zk锁错误,key:%s，",lockKey),e);
            return  R.ok("获取zk锁错误，请稍后再试。");
        }finally {
            if(mutex != null && locked){
                try {
                    mutex.release();
                } catch (Exception e) {
                    logger.error(String.format("释放zk锁错误,key:%s，",lockKey),e);
                }
            }
        }
    }

    public interface ZkRunable{
        R run() throws Exception;
    }
}
