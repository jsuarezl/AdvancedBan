package me.leoko.advancedban.common.user;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public class Console extends AbstractUser {

    public Console() {
        super(0, new UUID(0, 0), "CONSOLE");
    }

    @Override
    public @Nullable InetAddress getLastAddress() {
        return null;
    }

    @Override
    public void setLastAddress(InetAddress lastAddress) {
        throw new UnsupportedOperationException("console can't have an address");
    }

    @Override
    public @NotNull Set<InetAddress> getAddressHistory() {
        return new HashSet<>();
    }

    @Override
    public long getLastLogin() {
        return 0;
    }

    @Override
    public void setLastLogin(long lastLogin) {
        throw new UnsupportedOperationException("console can't have a login time");
    }

    @Override
    public @Nullable Punishment getBan() {
        throw new UnsupportedOperationException("console can't be banned");
    }

    @Override
    public @Nullable Punishment getMute() {
        throw new UnsupportedOperationException("console can't be muted");
    }

    @Override
    public Set<Punishment> getNotes() {
        throw new UnsupportedOperationException("console can't have notes");
    }

    @Override
    public Set<Punishment> getWarns() {
        throw new UnsupportedOperationException("console can't be warned");
    }

    @Override
    public Set<Punishment> getHistory() {
        throw new UnsupportedOperationException("console doesn't have history");
    }

    @Override
    public Punishment ban(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        throw new UnsupportedOperationException("console can't be banned");
    }

    @Override
    public Punishment mute(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        throw new UnsupportedOperationException("console can't be muted");
    }

    @Override
    public Punishment warn(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        throw new UnsupportedOperationException("console can't be warned");
    }

    @Override
    public Punishment note(@NotNull AbstractUser operator, boolean ip, @Nullable String reason, @Nullable String layout) {
        throw new UnsupportedOperationException("console can't have notes");
    }
}
