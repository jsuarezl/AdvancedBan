package me.leoko.advancedban.common.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import me.leoko.advancedban.common.punishment.Punishment;
import me.leoko.advancedban.common.punishment.PunishmentFactory;
import me.leoko.advancedban.common.scheduler.SchedulerAdapter;
import me.leoko.advancedban.common.storage.StorageProvider;
import me.leoko.advancedban.common.storage.model.user.StoredUser;
import me.leoko.advancedban.common.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public class CacheProvider {

    private final StorageProvider storageProvider; // we need to access data from storage provider sometimes
    private final AsyncLoadingCache<UUID, @NotNull User> cachedUsers;
    private final AsyncLoadingCache<UUID, @Nullable Set<Punishment>> punishmentHistory;
    private final AsyncLoadingCache<UUID, @Nullable Punishment> activeBans;

    public CacheProvider(@NotNull SchedulerAdapter schedulerAdapter, @NotNull StorageProvider storageProvider, @NotNull PunishmentFactory punishmentFactory) {
        this.storageProvider = storageProvider;
        cachedUsers = Caffeine.newBuilder().executor(schedulerAdapter.async()).buildAsync(uniqueId -> {
            StoredUser storedUser = storageProvider.getUserData(uniqueId);
            if (storedUser != null) {
                return new User(punishmentFactory, storedUser);
            } else {
                return null;
            }
        });
        punishmentHistory = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).weakValues().executor(schedulerAdapter.async()).buildAsync((uuid, executor) -> {
            CompletableFuture<User> cachedUser = cachedUsers.get(uuid); // force entry to be loaded
            if (cachedUser != null) { // check if user exists on cache or database
                return cachedUser.thenApply(User::getHistory);
            }
            return null; // user doesn't exist, so we couldn't get any punishment
        });
        activeBans = Caffeine.newBuilder().expireAfterAccess(6, TimeUnit.HOURS).executor(schedulerAdapter.async()).buildAsync(((uuid, executor) -> {
            CompletableFuture<User> cachedUser = cachedUsers.get(uuid); // force entry to be loaded
            if (cachedUser != null) { // check if user exists on cache or database
                return cachedUser.thenApply(User::getBan);
            }
            return null; // user doesn't exist, so we couldn't get any punishment
        }));
    }

    public void invalidate(UUID uniqueId) {
        // TODO: may be remove history too, active punishments shouldn't be explicitly removed from cache
        cachedUsers.synchronous().invalidate(uniqueId);
    }

    public @Nullable CompletableFuture<User> getUser(UUID uniqueId) {
        return cachedUsers.getIfPresent(uniqueId);
    }

    public @Nullable CompletableFuture<User> getUser(UUID uniqueId, Function<UUID, User> userFunction) {
        return cachedUsers.get(uniqueId, userFunction);
    }

    public CompletableFuture<@Nullable Set<Punishment>> getPunishments(UUID uniqueId) {
        return punishmentHistory.get(uniqueId);
    }

    public CompletableFuture<@Nullable Punishment> getBan(UUID uniqueId) {
        return activeBans.get(uniqueId);
    }

    public void shutdown() {
        cachedUsers.synchronous().asMap().values().forEach(storageProvider::saveUser);
    }
}
