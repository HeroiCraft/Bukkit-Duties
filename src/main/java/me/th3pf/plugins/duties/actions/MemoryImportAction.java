package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import me.th3pf.plugins.duties.Memory;
import org.bukkit.entity.Player;

public class MemoryImportAction implements EnableAction
{
   @Override
   public boolean onEnable( Player player )
   {
      //Importing to memory
      try
      {
         Duties.Memories.put( player.getName(), new Memory( player, 0 ) );
         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while importing player data in to memory: " + exception.getMessage() );
         return false;
      }
   }
}
