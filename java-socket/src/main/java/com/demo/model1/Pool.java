package com.demo.model1;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * 线程池
 */
public class Pool {

    ThreadPool threadPool = new ThreadPool(
            5,
            10,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(200),
            new ThreadPoolExecutor.DiscardPolicy()
    );

    /**
     * submit方法能提供线程执行的返回值，但只有实现了Callable才会有返回值
     */
    public void submit() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    return "i am over";
                }
            };
            Future future = threadPool.submit(callable);
            System.out.println(future.get());
        }
        ;
    }

    /**
     * execute方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功；
     */
    public void execute(Runnable object) throws InterruptedException {
        System.out.println(threadPool.getTaskCount());
        Thread.sleep(4*1000);
        threadPool.execute(object);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        threadPool.shutdown();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //客户端启动
        Pool pool = new Pool();
        for (int i = 0; i < 10; i++) {
            pool.execute(new Client());
        }
    }

}