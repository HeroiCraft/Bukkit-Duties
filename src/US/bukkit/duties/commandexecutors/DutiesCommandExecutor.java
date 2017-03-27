package US.bukkit.duties.commandexecutors;

import US.bukkit.duties.Configuration;
import US.bukkit.duties.Duties;
import US.bukkit.duties.adapters.VaultAdapter;
import US.bukkit.duties.events.ReloadedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class DutiesCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String subCommand = "help";
        String[] subArgs = new String[0];

        if (args.length >= 1) {
            subCommand = args[0];

            subArgs = new String[args.length - 1];

            System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        }

        switch (subCommand) {
            case "help":
            case "?":
                executeHelp(sender, subArgs);
                break;
            case "reload":
                executeReload(sender, subArgs);
                break;
            case "disable":
                executeDisable(sender, subArgs);
                break;
            case "updateconfig":
                executeUpdateConfig(sender, subArgs);
                break;

            default:
                TellSender(sender, updates.CommandExtensionNotFound, false);
        }

        return true;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        if (Duties.Config.GetBoolean("Vault.Permissions"))
            return Duties.VaultAdapter.permission.has(sender, permission);

        return sender.hasPermission(permission);
    }

    private void executeHelp(CommandSender sender, String[] args) {
        if (!hasPermission(sender, "duties.help"))
            return;

        int page = 1;

        if (args.length >= 1)
            page = Integer.parseInt(args[0]);

        sender.sendMessage(ChatColor.BLUE + "----------------------" + ChatColor.GOLD + "[" + ChatColor.YELLOW + "Duties" + ChatColor.GOLD + "]" + ChatColor.BLUE + "---------" + ChatColor.YELLOW + "[Page: " + page + "/2]" + ChatColor.BLUE + "------");

        if (page == 0 || page == 1) {
            sender.sendMessage(ChatColor.GREEN + "/dutymode");
            sender.sendMessage("    Toggles the duty mode for yourself");
            sender.sendMessage(ChatColor.GREEN + "/dutymode toggle [Player]");
            sender.sendMessage("    Toggles the duty mode for yourself [or other player]");
            sender.sendMessage(ChatColor.GREEN + "/dutymode enable [Player]");
            sender.sendMessage("    Enables the duty mode for yourself [or other player]");
            sender.sendMessage(ChatColor.GREEN + "/dutymode disable [Player]");
            sender.sendMessage("    Disables the duty mode for yourself [or other player]");
            sender.sendMessage(ChatColor.GREEN + "/dutymode list");
            sender.sendMessage("    Shows a list of the staff players that have duty mode on");
            sender.sendMessage(ChatColor.GREEN + "/dutymode listall");
            sender.sendMessage("    Shows a list of all the players that have duty mode on");
            sender.sendMessage(ChatColor.YELLOW + "/duties help");
            sender.sendMessage("    Shows the help for the plugin");
            sender.sendMessage(ChatColor.RED + "/duties reload");
            sender.sendMessage("    Reloads the plugin");
            sender.sendMessage(ChatColor.RED + "/duties disable");
            sender.sendMessage("    Disables the plugin");
        } else if (page == 2) {
            sender.sendMessage(ChatColor.RED + "/duties purge");
            sender.sendMessage("    Forces every player off duty mode");
            sender.sendMessage(ChatColor.GREEN + "/hidebroadcast [Player]");
            sender.sendMessage("    Disables duty mode changes broadcasting");
            sender.sendMessage(ChatColor.GREEN + "/hidebroadcast [Player]");
            sender.sendMessage("    Disables duty mode changes broadcasting");
            sender.sendMessage(ChatColor.YELLOW + "/duties updateconfig");
            sender.sendMessage("    Updates the config file to include all config options");
        }

        sender.sendMessage(ChatColor.BLUE + "-----------------------------------------------------");
    }

    private void executeReload(CommandSender sender, String[] args) {
        if (!hasPermission(sender, "duties.reload")) {
            TellSender(sender, updates.MissingPermission, false);

            return;
        }

        Duties.GetInstance().LogMessage("The 'KeepStateOffline' setting requires a server restart to be changed.");

        File configFile = new File(Duties.GetInstance().getDataFolder().getAbsoluteFile(), "config.yml");

        if (!configFile.exists())
            new Configuration().new Main(configFile).Reload();

        File messagesFile = new File(Duties.GetInstance().getDataFolder().getAbsoluteFile(), "messages.yml");

        if (!messagesFile.exists())
            new Configuration().new Messages(messagesFile).Reload();

        Duties.GetInstance().reloadConfig();
        Duties.Config.Reload();
        Duties.Messages.Reload();

        Duties.VaultAdapter = new VaultAdapter();

        Bukkit.getPluginManager().callEvent(new ReloadedEvent());

        if (sender instanceof Player)
            TellSender(sender, "Configuration reloaded!");

        Duties.GetInstance().LogMessage("Configuration reloaded!");
    }

    private void executeDisable(CommandSender sender, String[] args) {
        if (!hasPermission(sender, "duties.disable"))
            TellSender(sender, updates.MissingPermission, false);
        else
            Bukkit.getPluginManager().disablePlugin(Duties.GetInstance());
    }

    private void executeUpdateConfig(CommandSender sender, String[] args) {
        if (!hasPermission(sender, "duties.updateconfig")) {
            TellSender(sender, updates.MissingPermission, false);

            return;
        }

        Duties.Config.Reload();
        Duties.Messages.Reload();

        LinkedHashMap<String, Object> configDefaults = Duties.Config.initializeConfigDefaults();

        for (Map.Entry<String, Object> entry : configDefaults.entrySet()) {
            if (!Duties.Config.GetHandle().contains(entry.getKey())) {
                Duties.GetInstance().LogMessage("Adding: '" + entry.getKey() + "' to 'config.yml'");
                Duties.Config.GetHandle().set(entry.getKey(), entry.getValue());
            }
        }

        configDefaults.clear();

        configDefaults = Duties.Messages.initializeConfigDefaults();

        for (Map.Entry<String, Object> entry : configDefaults.entrySet()) {
            if (!Duties.Messages.GetHandle().contains(entry.getKey())) {
                Duties.GetInstance().LogMessage("Adding: '" + entry.getKey() + "' to 'messages.yml'");
                Duties.Messages.GetHandle().set(entry.getKey(), entry.getValue());
            }
        }

        try {
            Duties.Config.GetHandle().save(new File(Duties.GetInstance().getDataFolder().getAbsolutePath(), "config.yml"));
            Duties.Messages.GetHandle().save(new File(Duties.GetInstance().getDataFolder().getAbsolutePath(), "messages.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Duties.GetInstance().LogMessage("Configuration reloaded & updated!");
    }

    private void TellSender(CommandSender sender, updates update, boolean success) {
        TellSender(sender, update.getMessage());
    }

    private void TellSender(CommandSender sender, String message) {
        sender.sendMessage(Duties.Messages.GetString("Client.Tag") + message);
    }

    public enum updates {
        MissingPermission("Client.MissingPermission"),
        CommandExtensionNotFound("Client.CommandExtensionNotFound");

        private final String key;

        updates(String key) {
            this.key = key;
        }

        public String getMessage() {
            return Duties.Messages.GetString(key);
        }
    }
}
