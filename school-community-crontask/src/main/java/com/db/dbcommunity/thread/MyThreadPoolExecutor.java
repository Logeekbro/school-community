package com.db.dbcommunity.thread;

import java.util.concurrent.*;

public class MyThreadPoolExecutor {

    private static final ExecutorService threadPool;

    static {
        threadPool = new ThreadPoolExecutor(
                10, 10000, 128, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static void run(Runnable runnable) {
        CompletableFuture.runAsync(runnable, threadPool);
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

}
