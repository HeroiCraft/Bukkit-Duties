package US.bukkit.duties.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class DutyModePreEnabledEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private Player player;
    private List<String> actions;

    public DutyModePreEnabledEvent(Player player, List<String> actions) {
        this.player = player;
        this.actions = actions;
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

    public List<String> getActions() {
        return this.actions;
    }

    public boolean getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}