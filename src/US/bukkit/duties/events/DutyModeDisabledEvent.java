package US.bukkit.duties.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DutyModeDisabledEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;

    public DutyModeDisabledEvent(Player player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }
}