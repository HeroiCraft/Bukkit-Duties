package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.entity.Player;

public class CommandsAction implements Action
{
   @Override
   public boolean onEnable( Player player )
   {
      //Performs onEnable commands
      if( Duties.Config.GetStringList( "Actions.onEnable.Commands" ) == null ) return true;
      try
      {
         for( String command : Duties.Config.GetStringList( "Actions.onEnable.Commands" ) )
         {
            String parsedCommand = ( "/".equals( command.charAt( 0 ) ) ? command.substring( 1 ) : command )
                    .replaceAll( "%PLAYER_NAME%", player.getName() )
                    .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() );
            player.performCommand( parsedCommand );
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while performing onEnable commands: " + exception.getMessage() );
         return false;
      }
   }

   @Override
   public boolean onDisable( Player player )
   {
      //Performs onDisable commands
      if( Duties.Config.GetStringList( "Actions.onDisable.Commands" ) == null ) return true;
      try
      {
         for( String command : Duties.Config.GetStringList( "Actions.onDisable.Commands" ) )
         {
            String parsedCommand = ( "/".equals( command.charAt( 0 ) ) ? command.substring( 1 ) : command )
                    .replaceAll( "%PLAYER_NAME%", player.getName() )
                    .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() );
            player.performCommand( parsedCommand );
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while performing onDisable commands: " + exception.getMessage() );
         return false;
      }
   }
}
