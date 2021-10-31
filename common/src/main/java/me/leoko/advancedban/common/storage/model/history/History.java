package me.leoko.advancedban.common.storage.model.history;

import java.util.Set;

/**
 * @author Beelzebu
 */
public final class History <T> extends AbstractHistory<T> {

    private final Set<T> history;

    public History(int userId, T last, Set<T> history) {
        super(userId, last);
        this.history = history;
    }

    public Set<T> getHistory() {
        return Set.copyOf(history);
    }
}
