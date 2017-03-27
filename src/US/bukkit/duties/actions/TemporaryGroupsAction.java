package US.bukkit.duties.actions;

import US.bukkit.duties.Duties;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TemporaryGroupsAction implements Action {
    @Override
    public void onEnable(Player player) throws ActionException {
        if (!Duties.Config.GetBoolean("Vault.Permissions") || !Duties.GetInstance().getServer().getPluginManager().isPluginEnabled("Vault"))
            return;

        try {
            if (Duties.Config.GetStringList("Actions.TemporaryGroups") == null) return;
            for (String group : Duties.Config.GetStringList("Actions.TemporaryGroups")) {
                try {
                    //Adding group
                    if (Duties.GetInstance().getServer().getPluginManager().isPluginEnabled("bPermissions")) {
                        for (World world : Duties.GetInstance().getServer().getWorlds())
                            Duties.VaultAdapter.permission.playerAddGroup(world.getName(), player, group.replaceAll("%PLAYER_NAME%", player.getName()));
                    } else if (!Duties.VaultAdapter.permission.playerHas(player, group.replaceAll("%PLAYER_NAME%", player.getName())))
                        Duties.VaultAdapter.permission.playerAddGroup(player, group.replaceAll("%PLAYER_NAME%", player.getName()));
                    else {
                        //Already in group

                    }
                } catch (Exception exception) {
                    Duties.GetInstance().LogMessage("Failed while enabling temporary groups: Not a valid group: " + group.replaceAll("%PLAYER_NAME%", player.getName()));
                    Duties.GetInstance().LogMessage("Error occured: " + exception.getMessage() + ". Ignoring it!");

                }
            }
        } catch (Exception exception) {
            throw new ActionException("Failed while enabling temporary groups: ", exception);
        }
    }

    @Override
    public void onDisable(Player player) throws ActionException {
        if (!Duties.Config.GetBoolean("Vault.Permissions") || !Duties.GetInstance().getServer().getPluginManager().isPluginEnabled("Vault"))
            return;

        try {
            if (Duties.Config.GetStringList("Actions.TemporaryGroups") == null) return;
            for (String group : Duties.Config.GetStringList("Actions.TemporaryGroups")) {
                try {
                    //Removing group
                    if (Duties.GetInstance().getServer().getPluginManager().isPluginEnabled("bPermissions")) {
                        for (World world : Duties.GetInstance().getServer().getWorlds())
                            Duties.VaultAdapter.permission.playerRemove(world.getName(), player, group.replaceAll("%PLAYER_NAME%", player.getName()));
                    } else if (Duties.VaultAdapter.permission.playerHas(player, group.replaceAll("%PLAYER_NAME%", player.getName())))
                        Duties.VaultAdapter.permission.playerRemove(player, group.replaceAll("%PLAYER_NAME%", player.getName()));
                    else {
                        //Already not in group

                    }
                } catch (Exception exception) {
                    Duties.GetInstance().LogMessage("Failed while disabling temporary groups: Not a valid group: " + group.replaceAll("%PLAYER_NAME%", player.getName()));
                    Duties.GetInstance().LogMessage("Error occured: " + exception.getMessage() + ". Ignoring it!");
                }
            }
        } catch (Exception exception) {
            throw new ActionException("Failed while disabling temporary groups: ", exception);
        }
    }
}
