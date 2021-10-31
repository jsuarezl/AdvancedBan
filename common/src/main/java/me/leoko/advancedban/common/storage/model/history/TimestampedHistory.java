package me.leoko.advancedban.common.storage.model.history;

import java.util.Map;

/**
 * @author Beelzebu
 */
public final class TimestampedHistory <T> extends AbstractHistory<T> {

    private final Map<T, Long> history;

    public TimestampedHistory(int userId, T last, Map<T, Long> history) {
        super(userId, last);
        this.history = history;
    }

    public Map<T, Long> getHistory() {
        return Map.copyOf(history);
    }
}
