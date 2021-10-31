package me.leoko.advancedban.common;

import me.leoko.advancedban.common.cache.CacheProvider;
import me.leoko.advancedban.common.player.PlayerFactory;
import me.leoko.advancedban.common.punishment.PunishmentFactory;
import me.leoko.advancedban.common.scheduler.SchedulerAdapter;
import me.leoko.advancedban.common.storage.StorageProvider;
import me.leoko.advancedban.common.user.UserManager;

/**
 * @author Beelzebu
 */
public class AdvancedBan {

    private final StorageProvider storageProvider;
    private final CacheProvider cacheProvider;
    private final UserManager userManager;
    private final PunishmentFactory punishmentFactory;

    public AdvancedBan(SchedulerAdapter schedulerAdapter, PlayerFactory<?> playerAdapter) {
        storageProvider = null; // TODO add implementation
        punishmentFactory = new PunishmentFactory(schedulerAdapter, storageProvider);
        cacheProvider = new CacheProvider(schedulerAdapter, storageProvider, punishmentFactory);
        userManager = new UserManager(schedulerAdapter, cacheProvider, storageProvider, punishmentFactory, playerAdapter);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public PunishmentFactory getPunishmentFactory() {
        return punishmentFactory;
    }

    public void shutdown() {
        storageProvider.shutdown();
        cacheProvider.shutdown();
    }
}
