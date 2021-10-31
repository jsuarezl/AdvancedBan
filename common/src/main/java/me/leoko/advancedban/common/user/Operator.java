package me.leoko.advancedban.common.user;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * @author Beelzebu
 */
public interface Operator {

    int getId();

    @NotNull UUID getUniqueId();

    @NotNull String getName();

}
