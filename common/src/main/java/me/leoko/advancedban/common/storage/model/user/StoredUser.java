package me.leoko.advancedban.common.storage.model.user;

import java.net.InetAddress;
import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import me.leoko.advancedban.common.storage.model.history.History;
import me.leoko.advancedban.common.storage.model.history.TimestampedHistory;
import org.jetbrains.annotations.NotNull;

/**
 * Wrapper for user data stored in the database.
 *
 *
 * TODO: delete this class and implement it on {@link me.leoko.advancedban.common.user.User}
 *
 * @author Beelzebu
 * @deprecated Might be removed
 */
@Deprecated
public final class StoredUser {

    private final int id;
    private final UUID uniqueId;
    private final String name;
    private final long lastLogin;
    private final TimestampedHistory<String> nameHistory;
    private final History<InetAddress> addressHistory;
    private final Punishment ban;
    private final Punishment mute;
    private final History<Punishment> notes;
    private final History<Punishment> warns;
    private final History<Punishment> punishmentHistory;
    private final TimestampedHistory<Punishment> revokedPunishment;

    public StoredUser(int id, UUID uniqueId, String name, long lastLogin, TimestampedHistory<String> nameHistory, History<InetAddress> addressHistory, Punishment ban, Punishment mute, History<Punishment> notes, History<Punishment> warns, History<Punishment> punishmentHistory, TimestampedHistory<Punishment> revokedPunishment) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.lastLogin = lastLogin;
        this.nameHistory = nameHistory;
        this.addressHistory = addressHistory;
        this.ban = ban;
        this.mute = mute;
        this.notes = notes;
        this.warns = warns;
        this.punishmentHistory = punishmentHistory;
        this.revokedPunishment = revokedPunishment;
    }

    public int getId() {
        return id;
    }

    public @NotNull UUID getUniqueId() {
        return uniqueId;
    }

    public @NotNull String getName() {
        return name;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public TimestampedHistory<String> getNameHistory() {
        return nameHistory;
    }

    public History<InetAddress> getAddressHistory() {
        return addressHistory;
    }

    public Punishment getBan() {
        return ban;
    }

    public Punishment getMute() {
        return mute;
    }

    public History<Punishment> getNotes() {
        return notes;
    }

    public History<Punishment> getWarns() {
        return warns;
    }

    public History<Punishment> getPunishmentHistory() {
        return punishmentHistory;
    }

    public TimestampedHistory<Punishment> getRevokedPunishment() {
        return revokedPunishment;
    }
}
