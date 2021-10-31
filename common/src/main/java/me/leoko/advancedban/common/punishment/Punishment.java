package me.leoko.advancedban.common.punishment;

import java.util.Date;
import java.util.Objects;
import me.leoko.advancedban.common.user.Operator;
import me.leoko.advancedban.common.user.Punishable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public final class Punishment {

    private int id;
    private final int user;
    private final int operator;
    private final Date start;
    private final Date end;
    private final PunishmentType punishmentType;
    private final boolean ip;
    private final String reason;
    private final String layout;
    private final boolean expired;
    private transient boolean stored;

    Punishment(int id, int user, int operator, @NotNull Date start, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout, boolean expired) {
        this.id = id;
        this.stored = id > 0;
        this.user = user;
        this.operator = operator;
        this.start = Objects.requireNonNull(start, "start");
        this.end = end;
        this.punishmentType = Objects.requireNonNull(punishmentType, "punishmentType");
        this.ip = ip;
        this.reason = reason != null && reason.isBlank() ? reason.strip() : reason;
        this.layout = layout;
        this.expired = expired;
    }

    public Punishment(@NotNull Punishable user, @NotNull Operator operator, @NotNull Date start, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout) {
        this(0, user.getId(), operator.getId(), start, end, punishmentType, ip, reason, layout, false);
    }

    public Punishment(@NotNull Punishable user, @NotNull Operator operator, @NotNull Date start, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout) {
        this(0, user.getId(), operator.getId(), start, null, punishmentType, ip, reason, layout, false);
    }

    public Punishment(@NotNull Punishable user, @NotNull Operator operator, @NotNull Date start, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason) {
        this(0, user.getId(), operator.getId(), start, end, punishmentType, ip, reason, null, false);
    }

    public Punishment(@NotNull Punishable user, @NotNull Operator operator, @NotNull Date start, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip) {
        this(0, user.getId(), operator.getId(), start, end, punishmentType, ip, null, null, false);
    }

    public Punishment(@NotNull Punishable user, @NotNull Operator operator, @NotNull Date start, @NotNull PunishmentType punishmentType, boolean ip) {
        this(0, user.getId(), operator.getId(), start, null, punishmentType, ip, null, null, false);
    }

    public Punishment(@NotNull Punishable user, @NotNull Operator operator, @NotNull Date start, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason) {
        this(0, user.getId(), operator.getId(), start, null, punishmentType, ip, reason, null, false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id > 0 || stored) {
            throw new IllegalStateException("id already set");
        }
        if (id < 0) {
            throw new IllegalArgumentException("negative id");
        }
        stored = true;
        this.id = id;
    }

    /**
     * Player being punished, note that only real players can be punished.
     *
     * @return punished {@link Punishable}
     */
    public int getUserId() {
        return user;
    }

    public int getOperatorId() {
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
        return expired || (end != null && end.after(start));
    }
}
