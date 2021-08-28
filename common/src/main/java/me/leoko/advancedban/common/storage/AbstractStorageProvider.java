package me.leoko.advancedban.common.storage;

import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import me.leoko.advancedban.common.user.User;

/**
 * @author Beelzebu
 */
public abstract class AbstractStorageProvider {

    public abstract Punishment getPunishment(int id);

    public abstract User getUser(UUID uniqueId);

    public abstract Punishment savePunishment(Punishment punishment);
}
