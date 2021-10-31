package me.leoko.advancedban.common.punishment;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import me.leoko.advancedban.common.scheduler.SchedulerAdapter;
import me.leoko.advancedban.common.storage.StorageProvider;
import me.leoko.advancedban.common.user.Operator;
import me.leoko.advancedban.common.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public final class PunishmentFactory {

    private final SchedulerAdapter schedulerAdapter;
    private final StorageProvider storageProvider;

    public PunishmentFactory(@NotNull SchedulerAdapter schedulerAdapter, @NotNull StorageProvider storageProvider) {
        this.schedulerAdapter = schedulerAdapter;
        this.storageProvider = storageProvider;
    }

    public @NotNull Punishment getPunishment(@NotNull User user, @NotNull Operator operator, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout) {
        if (end == null) {
            return getPunishment(user, operator, punishmentType, ip, reason, layout);
        }
        return new Punishment(user, operator, new Date(), end, punishmentType, ip, reason, layout);
    }

    public Punishment getPunishment(@NotNull User user, @NotNull Operator operator, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout) {
        return new Punishment(user, operator, new Date(), punishmentType, ip, reason, layout);
    }

    public Punishment getPunishment(@NotNull User user, @NotNull Operator operator, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason) {
        return new Punishment(user, operator, new Date(), punishmentType, ip, reason);
    }

    public CompletableFuture<Punishment> getPunishment(int id) {
        return schedulerAdapter.toFuture(() -> storageProvider.getPunishment(id));
    }
}
