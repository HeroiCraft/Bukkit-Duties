package US.bukkit.duties.listeners;

import US.bukkit.duties.Duties;
import US.bukkit.duties.ModeSwitcher;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!Duties.Memories.containsKey(event.getPlayer().getUniqueId())) {
            return;
        }

        ModeSwitcher.DisableDutyMode(event.getPlayer());
    }

}
