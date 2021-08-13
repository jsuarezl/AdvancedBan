package me.leoko.advancedban.common.user;

import java.net.InetAddress;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import me.leoko.advancedban.common.punishment.PunishmentFactory;
import me.leoko.advancedban.common.punishment.PunishmentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public class User extends AbstractUser {

    private InetAddress lastAddress;
    private final Set<InetAddress> addressHistory;
    private long lastLogin;
    private Punishment ban;
    private Punishment mute;
    private final Set<Punishment> notes;
    private final Set<Punishment> warns;
    private final Set<Punishment> history;
    // TODO: set with users related to address or address history
    // TODO: may be keep track of datetime when an address was used by an user

    public User(int id, UUID uniqueId, String name, InetAddress lastAddress, Set<InetAddress> ipHistory, long lastLogin, Punishment ban, Punishment mute, Set<Punishment> notes, Set<Punishment> warns, Set<Punishment> history) {
        super(id, uniqueId, name);
        this.lastAddress = lastAddress;
        this.addressHistory = ipHistory;
        this.lastLogin = lastLogin;
        this.ban = ban;
        this.mute = mute;
        this.notes = notes;
        this.warns = warns;
        this.history = history;
    }


    public User(int id, UUID uniqueId, String name, InetAddress lastAddress, Set<InetAddress> addressHistory, long lastLogin, Punishment ban, Punishment mute) {
        this(id, uniqueId, name, lastAddress, addressHistory, lastLogin, ban, mute, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    @Override
    public InetAddress getLastAddress() {
        return lastAddress;
    }

    @Override
    public void setLastAddress(InetAddress lastAddress) {
        this.lastAddress = lastAddress;
    }

    @Override
    public @NotNull Set<InetAddress> getAddressHistory() {
        return Collections.unmodifiableSet(addressHistory);
    }

    @Override
    public long getLastLogin() {
        return lastLogin;
    }

    @Override
    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public @Nullable Punishment getBan() {
        return ban;
    }

    @Override
    public @Nullable Punishment getMute() {
        return mute;
    }

    @Override
    public Set<Punishment> getNotes() {
        return Collections.unmodifiableSet(notes);
    }

    @Override
    public Set<Punishment> getWarns() {
        return Collections.unmodifiableSet(warns);
    }

    @Override
    public Set<Punishment> getHistory() {
        return Collections.unmodifiableSet(history);
    }

    public Punishment ban(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        if (this.ban != null) {
            // TODO: check implementation, may be extend expiration, override or let admins configure behaviour
            throw new IllegalArgumentException("player is already banned");
        }
        return punish(operator, end, PunishmentType.BAN, ip, reason, layout);
    }

    public Punishment mute(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        if (this.mute != null) {
            // TODO: check implementation, may be extend expiration, override or let admins configure behaviour
            throw new IllegalArgumentException("player is already banned");
        }
        return punish(operator, end, PunishmentType.BAN, ip, reason, layout);
    }

    public Punishment warn(@NotNull AbstractUser operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        if (this.mute != null) {
            // TODO: check implementation, may be extend expiration, override or let admins configure behaviour
            throw new IllegalArgumentException("player is already banned");
        }
        return punish(operator, end, PunishmentType.BAN, ip, reason, layout);
    }

    public Punishment note(@NotNull AbstractUser operator, boolean ip, @Nullable String reason, @Nullable String layout) {
        return punish(operator, null, PunishmentType.NOTE, ip, reason, layout);
    }

    private Punishment punish(@NotNull AbstractUser operator, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout) {
        Punishment punishment = PunishmentFactory.getPunishment(this, operator, end, punishmentType, ip, reason, layout);
        switch (punishmentType) {
            case BAN:
                this.ban = punishment;
                break;
            case MUTE:
                this.mute = punishment;
                break;
            case KICK:
                // TODO: kick player from server/proxy
                break;
            case NOTE:
                notes.add(punishment);
                break;
            case WARN:
                warns.add(punishment);
                break;
            default:
                history.add(punishment);
                break;
        }
        return punishment;
    }
}
