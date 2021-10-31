package me.leoko.advancedban.common.storage;

import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import me.leoko.advancedban.common.storage.model.user.StoredUser;
import me.leoko.advancedban.common.user.Operator;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public interface StorageProvider {

    Punishment getPunishment(int id);

    Punishment savePunishment(Punishment punishment);

    @Nullable StoredUser getUserData(UUID uniqueId);

    @Nullable StoredUser getUserData(int id);

    void saveUser(Operator user);

    void shutdown();
}
