package me.leoko.advancedban.common.player;

import java.net.InetAddress;
import java.util.UUID;

/**
 * @author Beelzebu
 */
public final class PlatformPlayer <T> {

    private final PlayerFactory<T> playerAdapter;
    private final T player;

    PlatformPlayer(PlayerFactory<T> playerAdapter, T player) {
        this.playerAdapter = playerAdapter;
        this.player = player;
    }

    public UUID getUniqueId() {
        return playerAdapter.getUniqueId(player);
    }

    public String getName() {
        return playerAdapter.getName(player);
    }

    public InetAddress getAddress() {
        return playerAdapter.getAddress(player);
    }

    public boolean isOnline() {
        return playerAdapter.isOnline(player);
    }
}
