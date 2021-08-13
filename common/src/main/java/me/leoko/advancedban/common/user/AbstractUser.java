package me.leoko.advancedban.common.user;

import java.net.InetAddress;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public abstract class AbstractUser {

    private final int id;
    private UUID uniqueId;
    private String name;

    public AbstractUser(int id, UUID uniqueId, String name) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public final int getId() {
        return id;
    }

    public final @NotNull UUID getUniqueId() {
        return uniqueId;
    }

    public final void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public final @NotNull String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public abstract @Nullable InetAddress getLastAddress();

    public abstract void setLastAddress(InetAddress lastAddress);

    public abstract @NotNull Set<InetAddress> getAddressHistory();

    public abstract long getLastLogin();

    public abstract void setLastLogin(long lastLogin);

    public abstract @Nullable Punishment getBan();

    public abstract @Nullable Punishment getMute();

    public abstract Set<Punishment> getNotes();

    public abstract Set<Punishment> getWarns();

    public abstract Set<Punishment> getHistory();

    public abstract Punishment ban(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout);

    public abstract Punishment mute(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout);

    public abstract Punishment warn(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout);

    public abstract Punishment note(@NotNull AbstractUser operator, boolean ip, @Nullable String reason, @Nullable String layout);
}
