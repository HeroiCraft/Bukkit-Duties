package US.bukkit.duties.actions;

import org.bukkit.entity.Player;

public interface DisableAction
{
   void onDisable( Player player ) throws ActionException;
}
