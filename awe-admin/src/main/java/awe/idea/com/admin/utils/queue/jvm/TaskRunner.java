package awe.idea.com.admin.utils.queue.jvm;

import awe.idea.com.admin.models.TaskJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *   BlockingQueue阻塞队列
 *   阻塞队列，当队列为空时，调用出队线程进入等待，队列已满时，调用入队当前线程进入等待。 ApplicationRunner系统启动执行run
 */
public class TaskRunner {
	private static final Logger logger= LoggerFactory.getLogger(TaskRunner.class);
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(1);
    public static void run() {
    	Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while(true){
					//进程内队列
					try {
						TaskJob taskJob = TaskQueue.getMailQueue().consume();
						if(taskJob!=null){
							logger.info(taskJob.getOrderId() + taskJob.getUsername());
						}
						Thread.sleep(200);
					} catch (InterruptedException e) {
						logger.error("TaskRunner InterruptedException ",e);
					}catch (Exception e){
						logger.error("TaskRunner Exception ",e);
					}
				}
			}
		};
		threadPool.execute(runnable);
    }

    public static void shutdown(){
    	threadPool.shutdownNow();
	}
}