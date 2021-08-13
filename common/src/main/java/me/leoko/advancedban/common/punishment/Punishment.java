package me.leoko.advancedban.common.punishment;

import java.util.Date;
import java.util.Objects;
import me.leoko.advancedban.common.user.AbstractUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public final class Punishment {

    private int id;
    private final AbstractUser user;
    private final AbstractUser operator;
    private final Date start;
    private final Date end;
    private final PunishmentType punishmentType;
    private final boolean ip;
    private final String reason;
    private final String layout;
    private final boolean expired;

    public Punishment(int id, @NotNull AbstractUser user, @NotNull AbstractUser operator, @NotNull Date start, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout, boolean expired) {
        this.id = id;
        this.user = Objects.requireNonNull(user, "user");
        this.operator = Objects.requireNonNull(operator, "operator");
        this.start = Objects.requireNonNull(start, "start");
        this.end = end;
        this.punishmentType = Objects.requireNonNull(punishmentType, "punishmentType");
        this.ip = ip;
        this.reason = Objects.requireNonNull(reason, "reason");
        this.layout = layout;
        this.expired = expired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id != -1) {
            throw new IllegalStateException("id already set");
        }
        if (id < 0) {
            throw new IllegalArgumentException("negative id");
        }
        this.id = id;
    }

    public AbstractUser getUser() {
        return user;
    }

    public AbstractUser getOperator() {
        return operator;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public PunishmentType getPunishmentType() {
        return punishmentType;
    }

    public boolean isIp() {
        return ip;
    }

    public String getReason() {
        return reason;
    }

    public String getLayout() {
        return layout;
    }

    public boolean isExpired() {
        return expired;
    }
}
