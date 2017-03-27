package US.bukkit.duties.actions;

import US.bukkit.duties.Duties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastAction implements Action {
    private void broadcast(Player player, String message) {
        if (!Duties.Config.GetBoolean("Broadcast-duty-changes"))
            return;

        if (!player.hasPermission("duties.broadcast"))
            return;

        if (Duties.Hidden.contains(player))
            return;

        String formattedName = player.getName();

        if (Duties.Config.GetBoolean("Vault.NameFormatting") && Bukkit.getPluginManager().isPluginEnabled("Vault"))
            formattedName = Duties.VaultAdapter.chat.getPlayerPrefix(player) + formattedName + Duties.VaultAdapter.chat.getPlayerSuffix(player);

        for (Player receivingPlayer : Bukkit.getOnlinePlayers()) {
            if (receivingPlayer == player)
                continue;

            if (receivingPlayer.hasPermission("duties.getbroadcasts"))
                receivingPlayer.sendMessage(message.replaceAll("%PLAYER_NAME%", formattedName));
        }
    }

    @Override
    public void onEnable(Player player) {
        broadcast(player, Duties.Messages.GetString("Client.Broadcast.Enabled"));
    }

    @Override
    public void onDisable(Player player) {
        broadcast(player, Duties.Messages.GetString("Client.Broadcast.Disabled"));
    }
}
