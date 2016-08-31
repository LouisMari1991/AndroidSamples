package com.googlesamples.displayingbitmaps.util;


import java.util.ArrayDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sync on 2016/5/21.
 */
public class AsyncTask<Params, Progress, Result> {
    private static final String LOG_TAG = "AsyncTask";
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingDeque<Runnable> sPoolWorkQueue =
            new LinkedBlockingDeque<Runnable>(10);

    public static final Executor THREAD_POOL_EXCUTOR =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                    TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);


//  public static final Executor SERIAL_EXEXCUTOR = Utils.hasHoneycomb() ?
//          new SeriaExec


    private static class SerialExecutor implements Executor {

        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        /**
         * Executes the given command at some time in the future.  The command
         * may execute in a new thread, in a pooled thread, or in the calling
         * thread, at the discretion of the {@code Executor} implementation.
         *
         * @param command the runnable task
         * @throws RejectedExecutionException if this task cannot be
         *                                    accepted for execution
         * @throws NullPointerException       if command is null
         */
        @Override
        public synchronized void execute(final Runnable command) {
            mTasks.offer(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        public enum Status {
            /**
             * Indicates that the task has not been executed yet;
             */
            PENDING,
            //      /**
//       * Indicates tah {@link AsyncTask#onPostExecute} has finish.
//       */
            RUNNING,
            FINISHED,
        }

//    @SuppressWarnings({"UnusedDeclaration"})
//    protected void onPostExecute(Result result){
//
//    };

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                THREAD_POOL_EXCUTOR.execute(mActive);
            }
        }

    }


    private static abstract class WorkRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    private static class AsyncTaskResult<Data> {
        final AsyncTask mTask;
        final Data[] mData;

        AsyncTaskResult(AsyncTask task, Data... data) {
            mTask = task;
            mData = data;
        }

    }

}
