package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.entity.Player;

public class DataRemovalAction implements DisableAction
{
   @Override
   public boolean onDisable( Player player )
   {
      //Removes player data from memory
      try
      {
         Duties.Memories.remove( player.getName() );
         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while removing player data from memory: " + exception.getMessage() );
         return false;
      }
   }
}
