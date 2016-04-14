package me.th3pf.plugins.duties;

import me.th3pf.plugins.duties.actions.*;
import me.th3pf.plugins.duties.events.DutyModeDisabledEvent;
import me.th3pf.plugins.duties.events.DutyModeEnabledEvent;
import me.th3pf.plugins.duties.events.DutyModePreDisabledEvent;
import me.th3pf.plugins.duties.events.DutyModePreEnabledEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

//import me.th3pf.plugins.duties.temporaryfixes.PotionEffectRemoval;

public class ModeSwitcher
{
   private Player player;

   public ModeSwitcher( Player player )
   {
      this.player = player;
   }

   public boolean EnableDutyMode()
   {
      try
      {
         DutyModePreEnabledEvent event = new DutyModePreEnabledEvent( this.player );
         Bukkit.getServer().getPluginManager().callEvent( event );
         if( event.getCancelled() ) return false;

         for( String plugin : Duties.Config.GetStringList( "Actions.Requirements.Dependencies" ) )
         {
            try
            {
               if( Duties.GetInstance().pluginManager.getPlugin( plugin ).isEnabled() ) continue;
               Duties.GetInstance().pluginManager.enablePlugin( Duties.GetInstance().pluginManager.getPlugin( plugin ) );
            }
            catch( Exception exception )
            {
            }
         }

         boolean fail = false;

         for( String module : Duties.Config.GetStringList( "Actions.onEnable.Order" ) )
         {
            if( module.equalsIgnoreCase( "MemoryImport" ) )
            {
               if( !new MemoryImportAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "TemporaryPermissions" ) )
            {
               if( !new TemporaryPermissionsAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "TemporaryGroups" ) )
            {
               if( !new TemporaryGroupsAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "Cleanups" ) )
            {
               if( !new CleanupsAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "CommandsByConsole" ) )
            {
               if( !new CommandsByConsoleAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "Commands" ) )
            {
               if( !new CommandsAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "Messages" ) )
            {
               if( !new MessagesAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "Broadcast" ) )
            {
               if( !new BroadcastAction().onEnable( player ) )
               {
                  fail = true;
               }
            }
         }

         if( Duties.GetInstance().pluginManager.isPluginEnabled( "TagAPI" ) && this.player.isOnline() )
            TagAPI.refreshPlayer( this.player );

         //Returns that duty mode activation failed
         if( fail == true )
         {
            return false;
         }
         else
         {
            Duties.GetInstance().LogMessage( Duties.Messages.GetString( "Server.Enabled" ).replaceAll( "%PLAYER_NAME%", player.getName() ) );

            Bukkit.getServer().getPluginManager().callEvent( new DutyModeEnabledEvent( this.player ) );
            return true;
         }
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( Duties.Messages.GetString( "Server.Fail.Enable" ).replaceAll( "%PLAYER_NAME%", player.getName().replaceAll( "%REASON%", exception.getMessage() ) ) );
         return false;
      }
   }

   public boolean DisableDutyMode()
   {
      try
      {
         DutyModePreDisabledEvent event = new DutyModePreDisabledEvent( this.player );
         Bukkit.getServer().getPluginManager().callEvent( event );
         if( event.getCancelled() ) return false;

         for( String plugin : Duties.Config.GetStringList( "Actions.Requirements.Dependencies" ) )
         {
            try
            {
               if( Duties.GetInstance().pluginManager.getPlugin( plugin ).isEnabled() ) continue;
               Duties.GetInstance().pluginManager.enablePlugin( Duties.GetInstance().pluginManager.getPlugin( plugin ) );
            }
            catch( Exception exception )
            {
            }
         }

         boolean fail = false;

         for( String module : Duties.Config.GetStringList( "Actions.onDisable.Order" ) )
         {
            if( module.equalsIgnoreCase( "MemoryExport" ) )
            {
               if( !new MemoryExportAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "CommandsByConsole" ) )
            {
               if( !new CommandsByConsoleAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "Commands" ) )
            {
               if( !new CommandsAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "TemporaryGroups" ) )
            {
               if( !new TemporaryGroupsAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "TemporaryPermissions" ) )
            {
               if( !new TemporaryPermissionsAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "DataRemoval" ) )
            {
               if( !new DataRemovalAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "Messages" ) )
            {
               if( !new MessagesAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
            else if( module.equalsIgnoreCase( "Broadcast" ) )
            {
               if( !new BroadcastAction().onDisable( player ) )
               {
                  fail = true;
               }
            }
         }

         if( Duties.GetInstance().pluginManager.isPluginEnabled( "TagAPI" ) && this.player.isOnline() )
            TagAPI.refreshPlayer( this.player );

         //Returns that duty mode inactivation failed
         if( fail == true )
         {
            return false;
         }
         else
         {
            Duties.GetInstance().LogMessage( Duties.Messages.GetString( "Server.Disabled" ).replaceAll( "%PLAYER_NAME%", player.getName() ) );

            Bukkit.getServer().getPluginManager().callEvent( new DutyModeDisabledEvent( this.player ) );
            return true;
         }
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( Duties.Messages.GetString( "Server.Fail.Disable" ).replaceAll( "%PLAYER_NAME%", player.getName().replaceAll( "%REASON%", exception.getMessage() ) ) );
         return false;
      }
   }
}
