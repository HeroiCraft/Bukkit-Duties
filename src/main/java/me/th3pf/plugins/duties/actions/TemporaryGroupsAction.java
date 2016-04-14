package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TemporaryGroupsAction implements Action
{
   @Override
   public boolean onEnable( Player player )
   {
      if( !Duties.Config.GetBoolean( "Vault.Permissions" ) || !Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "Vault" ) )
         return true;

      try
      {
         if( Duties.Config.GetStringList( "Actions.TemporaryGroups" ) == null ) return true;
         for( String group : Duties.Config.GetStringList( "Actions.TemporaryGroups" ) )
         {
            try
            {
               //Adding group
               if( Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "bPermissions" ) )
               {
                  for( World world : Duties.GetInstance().getServer().getWorlds() )
                     Duties.VaultAdapter.permission.playerAddGroup( world, player.getName(), group.replaceAll( "%PLAYER_NAME%", player.getName() ) );
               }
               else if( !Duties.VaultAdapter.permission.playerInGroup( (String) null, player.getName(), group.replaceAll( "%PLAYER_NAME%", player.getName() ) ) )
                  Duties.VaultAdapter.permission.playerAddGroup( (String) null, player.getName(), group.replaceAll( "%PLAYER_NAME%", player.getName() ) );
               else
               {
                  //Already in group

               }
            }
            catch( Exception exception )
            {
               Duties.GetInstance().LogMessage( "Failed while enabling temporary groups: Not a valid group: " + group.replaceAll( "%PLAYER_NAME%", player.getName() ) );
               Duties.GetInstance().LogMessage( "Error occured: " + exception.getMessage() + ". Ignoring it!" );

            }
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while enabling temporary groups: " + exception.getMessage() );
         return false;
      }
   }

   @Override
   public boolean onDisable( Player player )
   {
      if( !Duties.Config.GetBoolean( "Vault.Permissions" ) || !Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "Vault" ) )
         return true;

      try
      {
         if( Duties.Config.GetStringList( "Actions.TemporaryGroups" ) == null ) return true;
         for( String group : Duties.Config.GetStringList( "Actions.TemporaryGroups" ) )
         {
            try
            {
               //Removing group
               if( Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "bPermissions" ) )
               {
                  for( World world : Duties.GetInstance().getServer().getWorlds() )
                     Duties.VaultAdapter.permission.playerRemoveGroup( world, player.getName(), group.replaceAll( "%PLAYER_NAME%", player.getName() ) );
               }
               else if( Duties.VaultAdapter.permission.playerInGroup( (String) null, player.getName(), group.replaceAll( "%PLAYER_NAME%", player.getName() ) ) )
                  Duties.VaultAdapter.permission.playerRemoveGroup( (String) null, player.getName(), group.replaceAll( "%PLAYER_NAME%", player.getName() ) );
               else
               {
                  //Already not in group

               }
            }
            catch( Exception exception )
            {
               Duties.GetInstance().LogMessage( "Failed while disabling temporary groups: Not a valid group: " + group.replaceAll( "%PLAYER_NAME%", player.getName() ) );
               Duties.GetInstance().LogMessage( "Error occured: " + exception.getMessage() + ". Ignoring it!" );
            }
         }

         return true;
      }
      catch( Exception exception )
      {
         Duties.GetInstance().LogMessage( "Failed while disabling temporary groups: " + exception.getMessage() );
         return false;
      }
   }
}
