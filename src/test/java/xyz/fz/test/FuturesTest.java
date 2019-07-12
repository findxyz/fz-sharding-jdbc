package xyz.fz.test;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuturesTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

    @Test
    public void testAllAsList() throws ExecutionException, InterruptedException {
        List<ListenableFuture<String>> result = new ArrayList<>();

        result.add(listeningExecutorService.submit(() -> {
            Thread.sleep(1000L);
            return System.currentTimeMillis() + "";
        }));

        result.add(listeningExecutorService.submit(() -> {
            Thread.sleep(2000L);
            return System.currentTimeMillis() + "";
        }));

        result.add(listeningExecutorService.submit(() -> {
            Thread.sleep(3000L);
            return System.currentTimeMillis() + "";
        }));

        ListenableFuture future = Futures.allAsList(result);
        System.out.println(future.get());
    }
}
