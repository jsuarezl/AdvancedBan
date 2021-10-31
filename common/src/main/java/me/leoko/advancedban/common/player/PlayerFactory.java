package me.leoko.advancedban.common.player;

import java.net.InetAddress;
import java.util.Objects;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public abstract class PlayerFactory <T> {

    protected abstract @Nullable T getPlayer(UUID uniqueId);

    protected abstract @Nullable T getPlayer(String name);

    protected abstract @NotNull UUID getUniqueId(T player);

    protected abstract @NotNull String getName(T player);

    protected abstract @NotNull InetAddress getAddress(T player);

    protected abstract boolean isOnline(T player);

    public final @NotNull PlatformPlayer<T> getPlatformPlayer(@NotNull T player) {
        Objects.requireNonNull(player, "player can't be null");
        return new PlatformPlayer<>(this, player);
    }

    public final @Nullable PlatformPlayer<T> getPlatformPlayer(@NotNull UUID uniqueId) {
        T player = getPlayer(uniqueId);
        if (player == null) {
            return null;
        }
        return getPlatformPlayer(player);
    }

    public final @Nullable PlatformPlayer<T> getPlatformPlayer(@NotNull String name) {
        T player = getPlayer(name);
        if (player == null) {
            return null;
        }
        return getPlatformPlayer(player);
    }
}
