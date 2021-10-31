package me.leoko.advancedban.common.storage.model.history;

/**
 * @author Beelzebu
 */
public abstract class AbstractHistory <T> {

    private final int userId;
    private final T last;

    public AbstractHistory(int userId, T last) {
        this.userId = userId;
        this.last = last;
    }

    public final int getUserId() {
        return userId;
    }

    public final T getLast() {
        return last;
    }
}
