package me.th3pf.plugins.duties.actions;

import me.th3pf.plugins.duties.Duties;
import me.th3pf.plugins.duties.Memory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class MemoryImportExportAction implements Action
{
   @Override
   public void onEnable( Player player ) throws ActionException
   {
      //Importing to memory
      try
      {
         Duties.Memories.put( player.getUniqueId(), new Memory( player, 0 ) );
      }
      catch( Exception exception )
      {
         throw new ActionException( "Failed while importing player data in to memory: ", exception );
      }
   }

   @Override
   public void onDisable( Player player ) throws ActionException
   {
      if( player.isInsideVehicle() )
         player.getVehicle().eject();

      Memory memory = Duties.Memories.get( player.getUniqueId() );

      if( Duties.Config.GetBoolean( "PreventTeleportCollision" ) ) //Teleporting in to blocks fix, suggested by @riddle
         memory.Location.add( 0, 1, 0 );

      player.teleport( memory.Location );

      if( memory.Vehicle != null )
         memory.Vehicle.setPassenger( player );

      player.setVelocity( memory.Velocity );
      player.setGameMode( memory.GameMode );

      PlayerInventory playerInventory = player.getInventory();

      playerInventory.clear();
      playerInventory.setContents( memory.Inventory );
      playerInventory.setArmorContents( memory.Armor );

      player.setExhaustion( memory.Exhaustion );
      player.setSaturation( memory.Saturation );
      player.setFoodLevel( memory.FoodLevel );
      player.setHealth( memory.Health );
      player.setLevel( memory.Level );
      player.setExp( memory.Experience );
      player.setRemainingAir( memory.RemainingAir );
      player.setFallDistance( memory.FallDistance );
      player.setFireTicks( memory.FireTicks );

      for( PotionEffect potionEffect : player.getActivePotionEffects() )
         player.removePotionEffect( potionEffect.getType() );

      player.addPotionEffects( memory.PotionEffects );

      if( memory.BedSpawnLocation != null && player.getBedSpawnLocation() != null && !memory.BedSpawnLocation.equals( player.getBedSpawnLocation() ) )
         player.setBedSpawnLocation( memory.BedSpawnLocation ); //Broken since CB 1.4.7-R*.* //TODO: check

      player.setTicksLived( memory.TicksLived );
   }
}
