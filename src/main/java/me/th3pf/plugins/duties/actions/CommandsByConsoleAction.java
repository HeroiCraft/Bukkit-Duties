package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandsByConsoleAction implements Action
{
   @Override
   public boolean onEnable( Player player )
   {
      //Performs onEnable console commands
      if( Duties.Config.GetStringList( "Actions.onEnable.CommandsByConsole" ) == null ) return true;
      try
      {
         for( String command : Duties.Config.GetStringList( "Actions.onEnable.CommandsByConsole" ) )
         {
            String parsedCommand = ( "/".equals( command.charAt( 0 ) ) ? command.substring( 1 ) : command )
                    .replaceAll( "%PLAYER_NAME%", player.getName() )
                    .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() );
            Duties.GetInstance().getServer().dispatchCommand( Bukkit.getConsoleSender(), parsedCommand );
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while performing onEnable console commands: " + exception.getMessage() );
         return false;
      }
   }

   @Override
   public boolean onDisable( Player player )
   {
      //Performs onDisable console commands
      if( Duties.Config.GetStringList( "Actions.onDisable.CommandsByConsole" ) == null ) return true;
      try
      {
         for( String command : Duties.Config.GetStringList( "Actions.onDisable.CommandsByConsole" ) )
         {
            String parsedCommand = ( "/".equals( command.charAt( 0 ) ) ? command.substring( 1 ) : command )
                    .replaceAll( "%PLAYER_NAME%", player.getName() )
                    .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() );
            Duties.GetInstance().getServer().dispatchCommand( Bukkit.getConsoleSender(), parsedCommand );
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while performing onDisable console commands: " + exception.getMessage() );
         return false;
      }
   }
}
