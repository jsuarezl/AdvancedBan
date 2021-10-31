package me.leoko.advancedban.common.user;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import me.leoko.advancedban.common.punishment.PunishmentFactory;
import me.leoko.advancedban.common.punishment.PunishmentType;
import me.leoko.advancedban.common.storage.model.user.StoredUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public final class User implements Operator, Punishable {

    private final PunishmentFactory punishmentFactory;
    private int id;
    private UUID uniqueId;
    private String name;
    private final Set<String> nameHistory;
    private InetAddress lastAddress;
    private final Set<InetAddress> addressHistory;
    private long lastLogin;
    private Punishment ban;
    private Punishment mute;
    private final Set<Punishment> notes;
    private final Set<Punishment> warns;
    private final Set<Punishment> history; // every revoked or expired punishment is stored on history
    // TODO: set with users related to address or address history
    // TODO: may be keep track of datetime when an address was used by an user

    public User(PunishmentFactory punishmentFactory, int id, UUID uniqueId, String name, Set<String> nameHistory, InetAddress lastAddress, Set<InetAddress> addressHistory, long lastLogin, Punishment ban, Punishment mute, Set<Punishment> notes, Set<Punishment> warns, Set<Punishment> history) {
        this.punishmentFactory = punishmentFactory;
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.nameHistory = nameHistory;
        this.lastAddress = lastAddress;
        this.addressHistory = addressHistory;
        this.lastLogin = lastLogin;
        this.ban = ban;
        this.mute = mute;
        this.notes = notes;
        this.warns = warns;
        this.history = history;
    }

    public User(PunishmentFactory punishmentFactory, UUID uniqueId, String name, InetAddress lastAddress) {
        this(punishmentFactory, -1, uniqueId, name, new HashSet<>(), lastAddress, new HashSet<>(), System.currentTimeMillis(), null, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public User(PunishmentFactory punishmentFactory, StoredUser storedUser) {
        this(punishmentFactory, storedUser.getId(), storedUser.getUniqueId(), storedUser.getName(), storedUser.getNameHistory().getHistory().keySet(), storedUser.getAddressHistory().getLast(), storedUser.getAddressHistory().getHistory(), storedUser.getLastLogin(), storedUser.getBan(), storedUser.getMute(), storedUser.getNotes().getHistory(), storedUser.getWarns().getHistory(), storedUser.getPunishmentHistory().getHistory());
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(@NotNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Set<String> getNameHistory() {
        return nameHistory;
    }

    public InetAddress getLastAddress() {
        return lastAddress;
    }

    public void setLastAddress(InetAddress lastAddress) {
        this.lastAddress = lastAddress;
    }

    public @NotNull Set<InetAddress> getAddressHistory() {
        return Set.copyOf(addressHistory);
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
    public Set<@NotNull Punishment> getNotes() {
        return Set.copyOf(notes);
    }

    @Override
    public Set<@NotNull Punishment> getWarns() {
        return Set.copyOf(warns);
    }

    @Override
    public Set<@NotNull Punishment> getHistory() {
        return Set.copyOf(history);
    }

    @Override
    public @NotNull Punishment ban(@NotNull Operator operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        if (this.ban != null) {
            // TODO: check implementation, may be extend expiration, override or let admins configure behaviour
            throw new IllegalArgumentException("player is already banned");
        }
        return punish(operator, end, PunishmentType.BAN, ip, reason, layout);
    }

    @Override
    public Punishment mute(@NotNull Operator operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        if (this.mute != null) {
            // TODO: check implementation, may be extend expiration, override or let admins configure behaviour
            throw new IllegalArgumentException("player is already banned");
        }
        return punish(operator, end, PunishmentType.BAN, ip, reason, layout);
    }

    @Override
    public Punishment warn(@NotNull Operator operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout) {
        if (this.mute != null) {
            // TODO: check implementation, may be extend expiration, override or let admins configure behaviour
            throw new IllegalArgumentException("player is already banned");
        }
        return punish(operator, end, PunishmentType.BAN, ip, reason, layout);
    }

    @Override
    public Punishment note(@NotNull Operator operator, boolean ip, @Nullable String reason, @Nullable String layout) {
        return punish(operator, null, PunishmentType.NOTE, ip, reason, layout);
    }

    private Punishment punish(@NotNull Operator operator, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout) {
        Punishment punishment = punishmentFactory.getPunishment(this, operator, end, punishmentType, ip, reason, layout);
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
