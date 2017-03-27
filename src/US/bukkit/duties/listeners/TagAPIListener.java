package US.bukkit.duties.listeners;

import US.bukkit.duties.Duties;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class TagAPIListener implements Listener
{

   @EventHandler( ignoreCancelled = true )
   public void onNameTag( AsyncPlayerReceiveNameTagEvent event )
   {
      if( Duties.Memories.containsKey( event.getNamedPlayer().getUniqueId() ) )
      {
         event.setTag(
                 Duties.Config.GetString( "Actions.NameTagPrefix" )
                         + event.getNamedPlayer().getName()
                         + Duties.Config.GetString( "Actions.NameTagSuffix" ) );
      }
   }
}
