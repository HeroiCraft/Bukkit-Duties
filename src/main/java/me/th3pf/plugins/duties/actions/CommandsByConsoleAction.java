package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandsByConsoleAction implements Action
{
   private void consoleCommands( Player player, List<String> commands )
   {
      if( commands == null )
         return;

      for( String command : commands )
      {
         String parsedCommand = ( "/".equals( command.charAt( 0 ) ) ? command.substring( 1 ) : command )
                 .replaceAll( "%PLAYER_NAME%", player.getName() )
                 .replaceAll( "%PLAYER_GAMEMODE%", player.getGameMode().toString() );

         Bukkit.dispatchCommand( Bukkit.getConsoleSender(), parsedCommand );
      }
   }

   @Override
   public boolean onEnable( Player player )
   {
      consoleCommands( player, Duties.Config.GetStringList( "Actions.onEnable.CommandsByConsole" ) );

      return true;
   }

   @Override
   public boolean onDisable( Player player )
   {
      consoleCommands( player, Duties.Config.GetStringList( "Actions.onDisable.CommandsByConsole" ) );

      return true;
   }
}
