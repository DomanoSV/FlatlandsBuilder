package net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import net.dkebnh.bukkit.FlatlandsBuilder.FlatlandsBuilder;

public class PluginCommands implements CommandExecutor {
	private FlatlandsBuilder plugin;
	
	public PluginCommands(FlatlandsBuilder plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		
		if (!sender.hasPermission("flatlandsbuilder.admin")){
			sender.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			sender.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "flatlandsbuilder.admin");
			return true;
		}
		
		if (true) {
				if(args.length == 1 && args[0].equalsIgnoreCase("version")) {
					sender.sendMessage(ChatColor.GREEN + "The FlatlandsBuilder plugin is version " + pdFile.getVersion());
					return true;
				}else if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.WHITE + "Command Help - " + ChatColor.GREEN + "FlatlandsBuilder");
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.RED + "Usage: /flb version" + ChatColor.GREEN + " - Gets Plugin Version.");
					sender.sendMessage(ChatColor.RED + "Usage: /flb height <height>" + ChatColor.GREEN + " - Sets default generation height.");
					sender.sendMessage(ChatColor.RED + "Usage: /flb block1 <block_id>" + ChatColor.GREEN + " - Sets the default fill block.");
					sender.sendMessage(ChatColor.RED + "Usage: /flb block2 <block_id>" + ChatColor.GREEN + " - Sets default border 1 block.");
					sender.sendMessage(ChatColor.RED + "Usage: /flb block3 <block_id>" + ChatColor.GREEN + " - Sets default border 2 block.");
					sender.sendMessage(ChatColor.RED + "Usage: /flb mode <mode>" + ChatColor.GREEN + " - Sets default generation mode.");
					return true;
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /flb help for more information.");
				}
		}
		return true;
	}
}
