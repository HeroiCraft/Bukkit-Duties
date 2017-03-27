package US.bukkit.duties;

import US.bukkit.duties.adapters.VaultAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class API {
    private Plugin registredPlugin = null;

    public void RegisterAddon(Plugin plugin, String name, boolean hidden) {

        if (Duties.Addons.containsKey(plugin)) return;

        if (plugin == null || name == null) {
            return;
        }

        try {
            Duties.Addons.put(plugin, name);

            registredPlugin = plugin;
            if (!hidden) {
                Duties.GetInstance().LogMessage("Registered addon: " + name);
            }
        } catch (Exception exception) {
            if (!hidden) {
                Duties.GetInstance().LogMessage("Addon " + "'" + name + "'" + " failed to register addon " + "'" + name + "'" + "due to an unknown cause.");
            }
        }
    }

    public void UnregisterAddon(Plugin plugin, String name, boolean hidden) {
        if (!Duties.Addons.containsKey(registredPlugin)) return;

        if (plugin == null || name == null) {
            return;
        }

        try {
            Duties.Addons.remove(plugin);

            registredPlugin = null;
            if (!hidden) {
                Duties.GetInstance().LogMessage("Unregistered addon: " + name);
            }
        } catch (Exception exception) {
            if (!hidden) {
                Duties.GetInstance().LogMessage("Addon " + "'" + name + "'" + " failed to register addon " + "'" + name + "'" + "due to an unknown cause.");
            }
        }
    }

    public void EnableDutyModeForPlayer(Player player) {
        if (!Duties.Addons.containsKey(registredPlugin)) return;

        ModeSwitcher.EnableDutyMode(player);
    }

    public void DisableDutyModeForPlayer(Player player) {
        if (!Duties.Addons.containsKey(registredPlugin)) return;

        ModeSwitcher.DisableDutyMode(player);
    }

    @Deprecated
    public boolean IsPlayerInDutyMode(String player) {
        if (!Duties.Addons.containsKey(registredPlugin)) return false;

        UUID uuid = Bukkit.getPlayer(player).getUniqueId();

        return Duties.Memories.keySet().contains(uuid);
    }

    public boolean IsPlayerInDutyMode(UUID player) {
        if (!Duties.Addons.containsKey(registredPlugin))
            return false;

        return Duties.Memories.keySet().contains(player);
    }

    public boolean HasPlayerCompleteDutyRights(Player player) {
        if (!Duties.Addons.containsKey(registredPlugin)) return false;

        return player.hasPermission("duties.staff");
    }

    public Configuration.Main GetMainConfiguration() {
        return Duties.Config;
    }

    public Configuration.Messages GetMessagesConfiguration() {
        return Duties.Messages;
    }

    public String GetDataFolderPath() {
        return Duties.GetInstance().getDataFolder().getAbsolutePath();
    }

    public VaultAdapter GetVaultAdapter() {
        return Duties.VaultAdapter;
    }

    public void LogMessage(String message) {
        Duties.GetInstance().LogMessage(message);
    }

    public Duties GetInstance() {
        return Duties.GetInstance();
    }
}
