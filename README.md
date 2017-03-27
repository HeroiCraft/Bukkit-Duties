# Duties

Creates a toggle between player mode and "Staff mode"

# Description

This will provide staff members a command to toggle between player(with its own inventory) and staff(also with its own inventory). It will also save location and world data so you will be right back where you started when you switch between them.


**Features:**

- Save your state for later
  - Location (including where you are looking, and what world you are in...)
  - Inventory
  - Armor
  - Health
  - Hunger
  - Experience
  - Saturation
  - Exhaustion
  - Gamemode
  - Fire ticks
  - Potion Effects
  - More... velocity, vehicles, remaining air, bed spawn...
- Keep duty mode on while offline
- Set other players mode
- Run configurable commands when enabling and disabling the mode
- Add temporary permissions while in duty mode
- Add temporary groups while in duty mode
- Remind players that they have duty mode on when...
  - logging in while Keep-state-offline is on
  - opening chests
  - dropping items
- Broadcasting status changes for example: "ThePf went on duty." Fully customizable.
- Individual broadcast bypass
- Option to change every message that the user will get when using Duties
- Vault permissions and name formatting support
- List players with dutymode on
- Chest interact, item drop, death drops, kill drops prevention

# Usage

Commands: (Every /dutymode can be replaced by /duty and /dm)

- /duties help - Shows the help for the plugin.
- /duties reload - Reloads the plugin.
- /duties disable - Disables the plugin.
- /duties updateconfig - Updates the configuration without sacrificing already existing data
- /dutymode (toggle [Player]) - Toggles the duty mode for yourself [or for another player]
- /dutymode enable/on [Player] - Enables the duty mode for yourself [or for another player]
- /dutymode disable/off [Player] - Disables the duty mode for yourself [or for another player]
- /dutymode list - Shows a list of which staff players have duty mode on
- /dutymode listall - Shows a list of which players have duty mode on
- /dutymode hideb [Player] - Disables the broadcast feature for yourself [or for another player]
- /dutymode showb [Player] - Enables the broadcast feature for yourself [or for another player]
- /dutymode purge - Forces all players off dutymode

# Permissions

The easiest setup for this is to give the admins the duties.admin node, mods the duties.mod node and regular players the duties.regular node.

    duties.*:
        description: Gives access to every Duties command.
        default: op
        children:
            duties.help: true
            duties.purge: true           
            duties.list: true
            duties.listall: true
            duties.reload: true
            duties.disable: true
            duties.updateconfig: true
            duties.self.toggle: true
            duties.self.enable: true
            duties.self.disable: true
            duties.others.toggle: true
            duties.others.enable: true
            duties.others.disable: true
            duties.getreminder.*: true
            duties.belisted: true
            duties.broadcast: true
            duties.getbroadcasts: true
            duties.self.setbroadcasts.*: true
            duties.others.setbroadcasts.*: true
    duties.help:
        description: Shows the help for the plugin
    duties.list:
        description: Lists all players that have duty mode on
    duties.listall:
        description: Lists all players that have duty mode on
    duties.purge:
        description: Forces all player off duty mode
    duties.reload:
        description: Reloads the plugin
    duties.disable:
        description: Disables the plugin
    duties.admin:
        description: Gives access to every Duties command
        children:
            duties.help: true
            duties.purge: true
            duties.list: true
            duties.listall: true
            duties.reload: true
            duties.disable: true
            duties.updateconfig: true
            duties.self.toggle: true
            duties.self.enable: true
            duties.self.disable: true
            duties.others.toggle: true
            duties.others.enable: true
            duties.others.disable: true
            duties.getreminder.*: true
            duties.belisted: true
            duties.broadcast: true
            duties.getbroadcasts: true
            duties.self.setbroadcasts.*: true
            duties.others.setbroadcasts.*: true
    duties.mod:
        description: Gives access to set your own status
        children:
            duties.help: true
            duties.self.toggle: true
            duties.self.enable: true
            duties.self.disable: true
            duties.getreminder.*: true
            duties.list: true
            duties.listall: true
            duties.belisted: true
            duties.broadcast: true
            duties.getbroadcasts: true
            duties.self.setbroadcasts.*: true
    duties.regular:
        description: Gives access to list all players that have duty mode on
        children:
            duties.list: true
            duties.getbroadcasts: true
    duties.staff:
        description: Treats player as a part of the staff
        children:
            duties.belisted: true
            duties.broadcast: true
    duties.self.*:
        description: Gives access to set your own status
        children:
            duties.self.toggle: true
            duties.self.enable: true
            duties.self.disable: true
    duties.others.*:
        description: Gives access to set others status
        children:
            duties.others.toggle: true
            duties.others.enable: true
            duties.others.disable: true
    duties.broadcast:
        description: Gives access to broadcast duty mode changes
    duties.bypass.dropitems:
        description: Allows the player to bypass the on-duty item drop prevention feature
    duties.bypass.chestinteracts:
        description: Allows the player to bypass the on-duty chest interact prevention feature
    duties.getbroadcasts:
        description: Informs the player when someones duty mode status have been changed
    duties.self.setbroadcasts.*:
        description: Gives access to enable and disable broadcasts
        children:
            duties.self.setbroadcasts.shown: true
            duties.self.setbroadcasts.hidden: true
    duties.self.setbroadcasts.shown:
        description: Gives access to force your broadcasts visible
    duties.self.setbroadcasts.hidden:
        description: Gives access to force your broadcasts invisible
    duties.others.setbroadcasts.*:
        description: Gives access to enable and disable broadcasts
        children:
            duties.others.setbroadcasts.shown: true
            duties.others.setbroadcasts.hidden: true
    duties.others.setbroadcasts.shown:
        description: Gives access to force your broadcasts visible
    duties.others.setbroadcasts.hidden:
        description: Gives access to force someones broadcasts invisible
    duties.getreminder.*:
        description: Provides the player all of the reminders when duty mode is on
        children:
            duties.getreminder.onlogin: true
            duties.getreminder.onchestinteract: true
            duties.getreminder.onitemdrop: true
    duties.getreminder.onlogin:
        description: Provides the player a reminder when logging in when duty mode is on
    duties.getreminder.onchestinteract:
        description: Provides the player a reminder when opening a chest when duty mode is on
    duties.getreminder.onitemdrop:
        description: Provides the player a reminder when dropping an item when duty mode is on

# Installation

- Place the jar file in your plugins folder
- Restart your server


# Configuration

Temp groups are added while duty mode is on, and removed while duty mode is off

    Enabled: true #Set to false to disable Duties
    KeepStateOffline: false #If true, the player will be able to leave the game without going off duty
    Actions:
      onEnable:
        Order: #The (order of) the onEnable modules to be executed
        - MemoryImport
        - TemporaryGroups
        - TemporaryPermissions
        - Cleanups
        - CommandsByConsole
        - Commands
        - Messages
        - Broadcast
        Cleanups: #Reset player states to default while on duty
    #    - Location #Disabled by default, will however teleport the player to spawn
    #    - BedSpawn #Disabled by default, removes the player's bed spawn location while on duty
        - Vehicle
        - Velocity
        - Inventory
        - Armor
        - Exhaustion
        - Saturation
        - FoodLevel
        - Health
        - Experience
        - RemaingAir
        - FallDistance
        - FireTicks
        - PotionEffects
        - TicksLived
        Messages: [] #Messages that will be delivered to the player
        Commands: [] #Commands that will be executed by the player.
        CommandsByConsole: #Commands that will be executed by the console
        - gamemode 1 %PLAYER_NAME%
      TemporaryPermissions: #Temporary permission nodes that should be added
      - herp.derp #Remove this line if you're having trouble with the default config
      TemporaryGroups: #Permissions groups to be added while on duty
      - Duty
      onDisable:
        Order: #The order of the onDisable modules to be executed
        - MemoryExport
        - CommandsByConsole
        - Commands
        - TemporaryPermissions
        - TemporaryGroups
        - DataRemoval
        - Messages
        - Broadcast
        Messages: [] #Messages that will be delivered to the player
        Commands: [] #Commands that will be executed by the player.
        CommandsByConsole: []
      DisableDeathDrops: true #Removes all death drops from players on duty
      DisableKillDrops: false #Removes all death drops from players/mobs killed by on-duty players.
      DenyDesiredDrops: false #Prevents on-duty players from dropping items with Q
      DenyChestInteracts: true #Prevents on-duty players from opening chests
      RemindPlayers: true #Reminds the player of item drops and chest interacts
      Requirements:
        Dependencies: #Plugins needed to enable and disable dutymode. (so they aren't disabled when Duties uses their commands)
        - Vault #You can remove this line if you're not using the Vault plugin (not recommended)
    Vault:
      Permissions: true #If true, Duties will use Vault's permissions system
      NameFormatting: false #If true, Duties will use Vault to format player names
    PreventTeleportCollision: false #Enable if players get stuck in the ground on dutymode teleports
    Broadcast-duty-changes: true #Set to true if the server should broadcast dutymode status updates
    ReminderCooldown: 2400 #The amount of tics between dutymode reminder messages