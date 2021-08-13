package me.leoko.advancedban.common.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

/**
 * @author Beelzebu
 */
public abstract class SchedulerAdapter {

    public abstract Executor async();

    public <T> CompletableFuture<T> toFuture(Callable<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.call();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, async());
    }

    public CompletableFuture<Void> toFuture(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, async());
    }
}
