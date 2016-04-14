package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class CleanupsAction implements EnableAction
{
   @Override
   public void onEnable( Player player ) throws ActionException
   {
      //Cleanups
      if( Duties.Config.GetStringList( "Actions.onEnable.Cleanups" ) == null ) return;
      try
      {
         for( String task : Duties.Config.GetStringList( "Actions.onEnable.Cleanups" ) )
         {
            if( task.equalsIgnoreCase( "Location" ) )
               player.teleport( player.getWorld().getSpawnLocation() );
            else if( task.equalsIgnoreCase( "Vehicle" ) )
            {
               if( player.isInsideVehicle() )
               {
                  player.getVehicle().eject();
               }
            }
            else if( task.equalsIgnoreCase( "Velocity" ) )
               player.setVelocity( new Vector( 0, 0, 0 ) );
            else if( task.equalsIgnoreCase( "Inventory" ) )
               player.getInventory().clear();
            else if( task.equalsIgnoreCase( "Armor" ) )
            {
               player.getInventory().setHelmet( null );
               player.getInventory().setChestplate( null );
               player.getInventory().setLeggings( null );
               player.getInventory().setBoots( null );
            }
            else if( task.equalsIgnoreCase( "Saturation" ) )
               player.setSaturation( 0 );
            else if( task.equalsIgnoreCase( "Exhaustion" ) )
               player.setExhaustion( 0 );
            else if( task.equalsIgnoreCase( "FoodLevel" ) )
               player.setFoodLevel( 20 );
            else if( task.equalsIgnoreCase( "Health" ) )
               player.setHealth( 20.0 );
            else if( task.equalsIgnoreCase( "Experience" ) )
            {
               player.setLevel( 0 );
               player.setExp( (float) 0.0 );
            }
            else if( task.equalsIgnoreCase( "RemainingAir" ) )
               player.setRemainingAir( 20 );
            else if( task.equalsIgnoreCase( "FallDistance" ) )
               player.setFallDistance( 0 );
            else if( task.equalsIgnoreCase( "FireTicks" ) )
               player.setFireTicks( 0 );
            else if( task.equalsIgnoreCase( "PotionEffects" ) )
            {
               for( PotionEffect potionEffect : player.getActivePotionEffects() )
               {
                  player.removePotionEffect( potionEffect.getType() );

                  //Temporary fix for Potion Effect Removal
                  //PotionEffectRemoval.removeMobEffect(player, potionEffect.getType().getId());
               }
            }
            else if( task.equalsIgnoreCase( "BedSpawnLocation" ) )
               player.setBedSpawnLocation( player.getWorld().getSpawnLocation() );
            else if( task.equalsIgnoreCase( "TicksLived" ) )
               player.setTicksLived( 1 );
         }
      }
      catch( Exception exception )
      {
         throw new ActionException( "Failed while reading cleanup tasks: ", exception );
      }
   }
}
