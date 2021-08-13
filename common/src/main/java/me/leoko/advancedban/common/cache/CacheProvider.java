package me.leoko.advancedban.common.cache;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import me.leoko.advancedban.common.punishment.Punishment;
import me.leoko.advancedban.common.scheduler.SchedulerAdapter;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public class CacheProvider {

    private final SchedulerAdapter schedulerAdapter;
    private final AsyncLoadingCache<UUID, @Nullable Set<Punishment>> punishmentHistory;
    private final AsyncLoadingCache<UUID, @Nullable Set<Punishment>> activePunishments;

    public CacheProvider(SchedulerAdapter schedulerAdapter, AsyncCacheLoader<UUID, Set<Punishment>> historyLoader, AsyncCacheLoader<UUID, Set<Punishment>> punishmentLoader) {
        this.schedulerAdapter = schedulerAdapter;
        punishmentHistory = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).executor(schedulerAdapter.async()).buildAsync(historyLoader);
        activePunishments = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).executor(schedulerAdapter.async()).buildAsync(punishmentLoader);
    }

    public CompletableFuture<@Nullable Set<Punishment>> getPunishments(UUID uniqueId) {
        return punishmentHistory.getIfPresent(uniqueId);
    }

    public CompletableFuture<@Nullable Set<Punishment>> getActivePunishments(UUID uniqueId) {
        return activePunishments.getIfPresent(uniqueId);
    }
}
