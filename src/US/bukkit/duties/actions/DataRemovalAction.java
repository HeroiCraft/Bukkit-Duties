package US.bukkit.duties.actions;

import US.bukkit.duties.Duties;
import org.bukkit.entity.Player;

public class DataRemovalAction implements DisableAction
{
   @Override
   public void onDisable( Player player ) throws ActionException
   {
      //Removes player data from memory
      try
      {
         Duties.Memories.remove( player.getUniqueId() );
      }
      catch( Exception exception )
      {
         throw new ActionException( "Failed while removing player data from memory: ", exception );
      }
   }
}
