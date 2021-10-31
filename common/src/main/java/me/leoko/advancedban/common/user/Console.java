package me.leoko.advancedban.common.user;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * @author Beelzebu
 */
public final class Console implements Operator {

    private static final int ID = 0;
    private static final UUID UNIQUE_ID = new UUID(0, 0);
    private static final String NAME = "CONSOLE";

    Console() {
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return UNIQUE_ID;
    }

    @Override
    public @NotNull String getName() {
        return NAME;
    }
}
