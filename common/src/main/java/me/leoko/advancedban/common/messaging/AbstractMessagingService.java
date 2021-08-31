package me.leoko.advancedban.common.messaging;

/**
 * @author Beelzebu
 */
public abstract class AbstractMessagingService {

    public abstract void sendMessage(Message message);

    public abstract void readMessage(Message message);
}
