package me.leoko.advancedban.common.messaging;

import com.google.gson.JsonObject;
import java.util.UUID;

/**
 * A message that can be sent using an {@link AbstractMessagingService} implementation, it will be read by other
 * instances subscribed to the same service.
 *
 * @author Beelzebu
 */
public class Message {

    private final UUID uniqueId = UUID.randomUUID();
    private final MessageType type;
    private final JsonObject content;

    public Message(MessageType type, JsonObject content) {
        this.type = type;
        this.content = content;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public MessageType getType() {
        return type;
    }

    public JsonObject getContent() {
        return content;
    }
}
