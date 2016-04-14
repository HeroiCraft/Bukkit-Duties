package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.entity.Player;

public class MessagesAction implements Action
{
   @Override
   public boolean onEnable( Player player )
   {
      //Chats onEnable messages
      if( Duties.Config.GetStringList( "Actions.onEnable.Messages" ) == null ) return true;
      try
      {
         for( String message : Duties.Config.GetStringList( "Actions.onEnable.Messages" ) )
         {
            String parsedMessage = ( "/".equals( message.charAt( 0 ) ) ? message.substring( 1 ) : message )
                    .replaceAll( "%PLAYER_NAME%", player.getName() )
                    .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() );
            player.sendMessage( parsedMessage );
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while chatting onEnable messages: " + exception.getMessage() );
         return false;
      }
   }

   @Override
   public boolean onDisable( Player player )
   {
      //Chats onDisable messages
      if( Duties.Config.GetStringList( "Actions.onDisable.Messages" ) == null ) return true;
      try
      {
         for( String message : Duties.Config.GetStringList( "Actions.onDisable.Messages" ) )
         {
            String parsedMessage = ( "/".equals( message.charAt( 0 ) ) ? message.substring( 1 ) : message )
                    .replaceAll( "%PLAYER_NAME%", player.getName() )
                    .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() );
            player.sendMessage( parsedMessage );
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while chatting onDisable messages: " + exception.getMessage() );
         return false;
      }
   }
}
