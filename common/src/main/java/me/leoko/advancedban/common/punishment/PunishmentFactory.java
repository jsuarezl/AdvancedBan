package me.leoko.advancedban.common.punishment;

import java.util.Date;
import me.leoko.advancedban.common.user.AbstractUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public class PunishmentFactory {

    public static Punishment getPunishment(@NotNull AbstractUser user, @NotNull AbstractUser operator, @Nullable Date end, @NotNull PunishmentType punishmentType, boolean ip, @Nullable String reason, @Nullable String layout) {
        return new Punishment(-1, user, operator, new Date(), end, punishmentType, ip, reason, layout, false);
    }
}
