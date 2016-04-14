package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.entity.Player;

public class DataRemovalAction implements DisableAction
{
   @Override
   public void onDisable( Player player ) throws ActionException
   {
      //Removes player data from memory
      try
      {
         Duties.Memories.remove( player.getName() );
      }
      catch( Exception exception )
      {
         throw new ActionException( "Failed while removing player data from memory: ", exception );
      }
   }
}
