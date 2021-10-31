package me.leoko.advancedban.common.user;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import me.leoko.advancedban.common.punishment.Punishment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public interface Punishable extends Operator {

    @Override
    int getId();

    @Override
    @NotNull UUID getUniqueId();

    @Override
    @NotNull String getName();

    long getLastLogin();

    void setLastLogin(long lastLogin);

    @Nullable Punishment getBan();

    @Nullable Punishment getMute();

    Set<@NotNull Punishment> getNotes();

    Set<@NotNull Punishment> getWarns();

    Set<@NotNull Punishment> getHistory();

    @NotNull Punishment ban(@NotNull Operator operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout);

    Punishment mute(@NotNull Operator operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout);

    Punishment warn(@NotNull Operator operator, @Nullable Date end, boolean ip, @Nullable String reason, @Nullable String layout);

    Punishment note(@NotNull Operator operator, boolean ip, @Nullable String reason, @Nullable String layout);
}
