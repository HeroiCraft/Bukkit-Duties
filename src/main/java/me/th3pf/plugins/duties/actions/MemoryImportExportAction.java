package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import me.th3pf.plugins.duties.Memory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class MemoryImportExportAction implements Action
{
   @Override
   public void onEnable( Player player ) throws ActionException
   {
      //Importing to memory
      try
      {
         Duties.Memories.put( player.getName(), new Memory( player, 0 ) );
      }
      catch( Exception exception )
      {
         throw new ActionException( "Failed while importing player data in to memory: ", exception );
      }
   }

   @Override
   public void onDisable( Player player ) throws ActionException
   {
      //Resets player data from memory
      try
      {
         if( player.isInsideVehicle() ) player.getVehicle().eject();

         if( Duties.Config.GetBoolean( "PreventTeleportCollision" ) ) //Teleporting in to blocks fix, suggested by @riddle
            Duties.Memories.get( player.getName() ).Location.setY( Duties.Memories.get( player.getName() ).Location.getY() + 1 );

         player.teleport( Duties.Memories.get( player.getName() ).Location );
         if( Duties.Memories.get( player.getName() ).Vehicle != null )
         {
            Duties.Memories.get( player.getName() ).Vehicle.setPassenger( player );
         }
         player.setVelocity( Duties.Memories.get( player.getName() ).Velocity );
         player.setGameMode( Duties.Memories.get( player.getName() ).GameMode );
         player.getInventory().clear();
         player.getInventory().setContents( Duties.Memories.get( player.getName() ).Inventory );
         player.getInventory().setArmorContents( Duties.Memories.get( player.getName() ).Armor );
         player.setExhaustion( Duties.Memories.get( player.getName() ).Exhaustion );
         player.setSaturation( Duties.Memories.get( player.getName() ).Saturation );
         player.setFoodLevel( Duties.Memories.get( player.getName() ).FoodLevel );
         player.setHealth( Duties.Memories.get( player.getName() ).Health );
         player.setLevel( Duties.Memories.get( player.getName() ).Level );
         player.setExp( Duties.Memories.get( player.getName() ).Experience );
         player.setRemainingAir( Duties.Memories.get( player.getName() ).RemainingAir );
         player.setFallDistance( Duties.Memories.get( player.getName() ).FallDistance );
         player.setFireTicks( Duties.Memories.get( player.getName() ).FireTicks );
         for( PotionEffect potionEffect : player.getActivePotionEffects() )
         {
            player.removePotionEffect( potionEffect.getType() );

            //PotionEffectRemoval.removeMobEffect(player, potionEffect.getType().getId());
         }
         player.addPotionEffects( Duties.Memories.get( player.getName() ).PotionEffects );

         //Duties.GetInstance().LogMessage("Reached before bed spawn loc.");

         if( Duties.Memories.get( player.getName() ).BedSpawnLocation != null && player.getBedSpawnLocation() != null && !Duties.Memories.get( player.getName() ).BedSpawnLocation.equals( player.getBedSpawnLocation() ) )
         {
            //Broken since CB 1.4.7-RX.X
            player.setBedSpawnLocation(
                    Duties.Memories.get( player.getName() ).BedSpawnLocation );
         }

         player.setTicksLived( Duties.Memories.get( player.getName() ).TicksLived );
      }
      catch( Exception exception )
      {
         throw new ActionException( "Failed while reseting player data from memory: ", exception );
      }
   }
}
