package me.leoko.advancedban.common.user;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import me.leoko.advancedban.common.cache.CacheProvider;
import me.leoko.advancedban.common.player.PlatformPlayer;
import me.leoko.advancedban.common.player.PlayerFactory;
import me.leoko.advancedban.common.punishment.PunishmentFactory;
import me.leoko.advancedban.common.scheduler.SchedulerAdapter;
import me.leoko.advancedban.common.storage.StorageProvider;
import me.leoko.advancedban.common.storage.model.user.StoredUser;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public final class UserManager {

    private static final Console CONSOLE = new Console();
    private final SchedulerAdapter schedulerAdapter;
    private final CacheProvider cacheProvider;
    private final StorageProvider storageProvider;
    private final PunishmentFactory punishmentFactory;
    private final PlayerFactory<?> playerAdapter;

    public UserManager(SchedulerAdapter schedulerAdapter, CacheProvider cacheProvider, StorageProvider storageProvider, PunishmentFactory punishmentFactory, PlayerFactory<?> playerAdapter) {
        this.schedulerAdapter = schedulerAdapter;
        this.cacheProvider = cacheProvider;
        this.storageProvider = storageProvider;
        this.punishmentFactory = punishmentFactory;
        this.playerAdapter = playerAdapter;
    }

    /**
     * Get console user, can be used as operator for punishments.
     *
     * @return {@link #CONSOLE} instance.
     */
    public Console getConsole() {
        return CONSOLE;
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
        return schedulerAdapter.toFuture(() -> getUserSync(uniqueId));
    }

    /**
     * Get a user from cache or database, if user is not in cache then we'll get it from {@link StorageProvider}
     * and add it to our {@link CacheProvider} until it gets cleaned up.
     *
     * @param uniqueId User uniqueId to query from cache or database.
     * @return User if an entry exists, otherwise null.
     */
    public CompletableFuture<@Nullable User> loadUser(UUID uniqueId) {
        // get from cache, if it doesn't exist, then load from database and cache
        return cacheProvider.getUser(uniqueId, uuid -> getUserSync(uniqueId));
    }

    @Nullable
    private User getUserSync(UUID uniqueId) {
        User user = null;
        StoredUser storedUser = storageProvider.getUserData(uniqueId);
        if (storedUser != null) {
            user = new User(punishmentFactory, storedUser);
        } else { // user is not stored
            PlatformPlayer<?> player = playerAdapter.getPlatformPlayer(uniqueId);
            if (player != null) {
                user = new User(punishmentFactory, uniqueId, player.getName(), player.getAddress());
                storageProvider.saveUser(user);
            }
        }
        return user;
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
