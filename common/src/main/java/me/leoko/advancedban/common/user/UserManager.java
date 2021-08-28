package me.leoko.advancedban.common.user;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import me.leoko.advancedban.common.cache.CacheProvider;
import me.leoko.advancedban.common.scheduler.SchedulerAdapter;
import me.leoko.advancedban.common.storage.AbstractStorageProvider;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public final class UserManager {

    private final SchedulerAdapter schedulerAdapter;
    private final CacheProvider cacheProvider;
    private final AbstractStorageProvider storageProvider;

    public UserManager(SchedulerAdapter schedulerAdapter, CacheProvider cacheProvider, AbstractStorageProvider storageProvider) {
        this.schedulerAdapter = schedulerAdapter;
        this.cacheProvider = cacheProvider;
        this.storageProvider = storageProvider;
    }

    /**
     * Get a user from cache, if player is not cached, then it is queried from database and returned.
     *
     * This method doesn't add the user to cache if is not cached, use with caution with offline users.
     *
     * @param uniqueId User uniqueId to query from cache or database.
     * @return User if an entry exists, otherwise null.
     */
    public CompletableFuture<@Nullable User> getUser(UUID uniqueId) {
        CompletableFuture<User> cachedUser = cacheProvider.getUser(uniqueId);
        if (cachedUser != null) {
            return cachedUser;
        }
        return schedulerAdapter.toFuture(() -> storageProvider.getUser(uniqueId));
    }

    /**
     * Get a user from cache or database, if user is not in cache then we'll get it from {@link AbstractStorageProvider} and add
     * it to our {@link CacheProvider} until it gets cleaned up.
     *
     * @param uniqueId User uniqueId to query from cache or database.
     * @return User if an entry exists, otherwise null.
     */
    public CompletableFuture<@Nullable User> loadUser(UUID uniqueId) {
        return cacheProvider.loadUser(uniqueId);
    }

    /**
     * Removes a user entry from cache. It doesn't remove active ban or history from cache.
     *
     * @param user User to remove from cache.
     */
    public void cleanup(User user) {
        cacheProvider.invalidate(user.getUniqueId());
    }
}
