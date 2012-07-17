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
					sender.sendMessage(ChatColor.WHITE + "Command Help - " + ChatColor.GREEN + "FlatlandsBuilder "  + pdFile.getVersion());
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.GREEN + "/flb version" + ChatColor.WHITE + " - Gets Plugin Version.");
					sender.sendMessage(ChatColor.GREEN + "/flb reload" + ChatColor.WHITE + " - Reloads configuration file.");
					sender.sendMessage(ChatColor.GREEN + "/flb check <worldname>" + ChatColor.WHITE + " - Prints out world settings.");
					return true;
				}else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
					sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Reloading configuration, please wait...");
					if(plugin.reloadAll()){
						sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Done."); 
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Error loading configuration files."); 
					}
					return true;
				}else if(args.length == 2 && args[0].equalsIgnoreCase("check")) {
					if (args[1] != null){
						String msg = null, worldName = args[1].toLowerCase(), block1 = null, block2 = null, block3 = null, mode = null, pathblock = null, wallblock = null;
						int height = 0, plotsize = 0;
						boolean plots = false;
						
						try{
							if (plugin.conf.contains("worlds." + worldName)){
								if (plugin.conf.contains("worlds." + worldName + ".height")) height = plugin.conf.getInt("worlds." + worldName + ".height");
								if (plugin.conf.contains("worlds." + worldName + ".mode")) mode = plugin.conf.getString("worlds." + worldName + ".mode");
								if (plugin.conf.contains("worlds." + worldName + ".block1")) block1 = plugin.conf.getString("worlds." + worldName + ".block1");
								if (plugin.conf.contains("worlds." + worldName + ".block2")) block2 = plugin.conf.getString("worlds." + worldName + ".block2");
								if (plugin.conf.contains("worlds." + worldName + ".block3")) block3 = plugin.conf.getString("worlds." + worldName + ".block3");
								if (plugin.conf.contains("worlds." + worldName + ".plots")) plots = plugin.conf.getBoolean("worlds." + worldName + ".plots");
								if (plugin.conf.contains("worlds." + worldName + ".plotsize")) plotsize = plugin.conf.getInt("worlds." + worldName + ".plotsize");
								if (plugin.conf.contains("worlds." + worldName + ".pathblock")) pathblock = plugin.conf.getString("worlds." + worldName + ".pathblock");
								if (plugin.conf.contains("worlds." + worldName + ".wallblock")) wallblock = plugin.conf.getString("worlds." + worldName + ".wallblock");
								
								msg = "Height: " + height;  
								msg = msg + ", Generation Mode: " + mode;  
								msg = msg + ", Block 1: " + block1;  
								if (plugin.conf.contains("worlds." + worldName + ".block2")) msg = msg + ", Block 2: " + block2;  
								if (plugin.conf.contains("worlds." + worldName + ".block3")) msg = msg + ", Block 3: " + block3;  
								msg = msg + ", Plots Enabled: " + plots;  
								if (plugin.conf.contains("worlds." + worldName + ".plotsize")) msg = msg + ", Plot Size: " + plotsize;  
								if (plugin.conf.contains("worlds." + worldName + ".pathblock")) msg = msg + ", Path Block: " + pathblock;  
								if (plugin.conf.contains("worlds." + worldName + ".wallblock")) msg = msg + ", Wall Block: " + wallblock;  
								
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "The configuration for the world " + ChatColor.GREEN + worldName + ChatColor.WHITE +":");
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + msg);
							}else{
								sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Unable to check world selected, does it exist?");
							}
				        }catch (Exception e){
				            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Unable to check world selected, does it exist?");
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command.");
					}
					return true;
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /flb help for more information.");
				}
		}
		return true;
	}
}
