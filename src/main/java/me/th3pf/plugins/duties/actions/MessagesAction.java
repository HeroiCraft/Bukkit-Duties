package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.entity.Player;

import java.util.List;

public class MessagesAction implements Action
{
   private void messages( Player player, List<String> messages )
   {
      if( messages == null )
         return;

      for( String message : messages )
         player.sendMessage( message.replaceAll( "%PLAYER_NAME%", player.getName() )
                 .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() ) );
   }

   @Override
   public boolean onEnable( Player player )
   {
      messages( player, Duties.Config.GetStringList( "Actions.onEnable.Messages" ) );

      return true;
   }

   @Override
   public boolean onDisable( Player player )
   {
      messages( player, Duties.Config.GetStringList( "Actions.onDisable.Messages" ) );

      return true;
   }
}
