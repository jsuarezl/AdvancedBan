package me.leoko.advancedban.common;

import me.leoko.advancedban.common.cache.CacheProvider;
import me.leoko.advancedban.common.scheduler.SchedulerAdapter;
import me.leoko.advancedban.common.storage.AbstractStorageProvider;
import me.leoko.advancedban.common.user.UserManager;

/**
 * @author Beelzebu
 */
public class AdvancedBan {

    private final SchedulerAdapter schedulerAdapter; // this field shouldn't be exposed
    private final AbstractStorageProvider storageProvider; // this field shouldn't be exposed
    private final CacheProvider cacheProvider;
    private final UserManager userManager;

    public AdvancedBan(SchedulerAdapter schedulerAdapter) {
        this.schedulerAdapter = schedulerAdapter;
        this.storageProvider = null; // TODO add implementation
        this.cacheProvider = new CacheProvider(schedulerAdapter, storageProvider::getUser);
        userManager = new UserManager(schedulerAdapter, cacheProvider, storageProvider);
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
