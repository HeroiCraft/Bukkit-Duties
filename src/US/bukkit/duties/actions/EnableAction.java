package US.bukkit.duties.actions;

import org.bukkit.entity.Player;

public interface EnableAction
{
   void onEnable( Player player ) throws ActionException;
}
