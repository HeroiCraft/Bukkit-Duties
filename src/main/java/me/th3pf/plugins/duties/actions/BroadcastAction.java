package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.entity.Player;

public class BroadcastAction implements Action //TODO: Cleanup
{
   @Override
   public boolean onEnable( Player player )
   {
      //Broadcasts duty change
      try
      {
         if( Duties.Config.GetBoolean( "Broadcast-duty-changes" ) && player.hasPermission( "duties.broadcast" ) && !Duties.Hidden.contains( player ) )
         {
            String FormattedName = player.getName();

            if( Duties.Config.GetBoolean( "Vault.NameFormatting" ) && Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "Vault" ) )
            {
               FormattedName = ( Duties.VaultAdapter.chat.getPlayerPrefix( player ) + FormattedName + Duties.VaultAdapter.chat.getPlayerSuffix( player ) );
            }

            for( Player receivingPlayer : Duties.GetInstance().getServer().getOnlinePlayers() )
            {
               if( receivingPlayer != player && receivingPlayer.hasPermission( "duties.getbroadcasts" ) )
                  receivingPlayer.sendMessage( Duties.Messages.GetString( "Client.Broadcast.Enabled" ).replaceAll( "%PLAYER_NAME%", FormattedName ) );
            }
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while broadcasting duty mode change: " + exception.getMessage() );
         return false;
      }
   }

   @Override
   public boolean onDisable( Player player )
   {
      //Broadcasts duty mode change
      try
      {
         if( Duties.Config.GetBoolean( "Broadcast-duty-changes" ) && player.hasPermission( "duties.broadcast" ) && !Duties.Hidden.contains( player ) )
         {
            String FormattedName = player.getName();

            if( Duties.Config.GetBoolean( "Vault.NameFormatting" ) && Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "Vault" ) )
            {
               FormattedName = ( Duties.VaultAdapter.chat.getPlayerPrefix( player ) + FormattedName + Duties.VaultAdapter.chat.getPlayerSuffix( player ) );
            }

            for( Player receivingPlayer : Duties.GetInstance().getServer().getOnlinePlayers() )
            {
               if( receivingPlayer != player && receivingPlayer.hasPermission( "duties.getbroadcasts" ) )
                  receivingPlayer.sendMessage( Duties.Messages.GetString( "Client.Broadcast.Disabled" ).replaceAll( "%PLAYER_NAME%", FormattedName ) );
            }
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while broadcasting duty mode change: " + exception.getMessage() );
         return false;
      }
   }
}
