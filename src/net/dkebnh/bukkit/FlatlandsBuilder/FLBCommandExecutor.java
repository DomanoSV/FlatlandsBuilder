package net.dkebnh.bukkit.FlatlandsBuilder;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class FLBCommandExecutor implements CommandExecutor {

	private FlatlandsBuilder plugin;
	
	public FLBCommandExecutor(FlatlandsBuilder plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		if (sender instanceof Player == false){
			plugin.log.infoMSG("The FlatlandsBuilder commands can only be used in game.");
			return true;
		}
		
		final Player player = (Player) sender;
		
		if (!player.hasPermission("flatlandsbuilder.admin")){
			player.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			player.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "flatlandsbuilder.admin");
			return true;
		}
		
		if (true) {
				if(args.length == 1 && args[0].equalsIgnoreCase("version")) {
					player.sendMessage(ChatColor.GREEN + "The FlatlandsBuilder plugin is version " + pdFile.getVersion());
					return true;
				}else if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					player.sendMessage(ChatColor.WHITE + "Command Help - " + ChatColor.GREEN + "FlatlandsBuilder");
					player.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					player.sendMessage(ChatColor.RED + "Usage: /flb version" + ChatColor.GREEN + " - Gets Plugin Version.");
					player.sendMessage(ChatColor.RED + "Usage: /flb height <height>" + ChatColor.GREEN + " - Sets default generation height.");
					player.sendMessage(ChatColor.RED + "Usage: /flb block1 <block_id>" + ChatColor.GREEN + " - Sets the default fill block.");
					player.sendMessage(ChatColor.RED + "Usage: /flb block2 <block_id>" + ChatColor.GREEN + " - Sets default border 1 block.");
					player.sendMessage(ChatColor.RED + "Usage: /flb block3 <block_id>" + ChatColor.GREEN + " - Sets default border 2 block.");
					player.sendMessage(ChatColor.RED + "Usage: /flb mode <mode>" + ChatColor.GREEN + " - Sets default generation mode.");
				}else if(args.length == 2 && args[0].equalsIgnoreCase("height")){		// Sets height variable in the config file.
					if (args[1] != null){
						int height = 0;
						try{
							height = Integer.parseInt(args[1]);
							
							if (height <= 0 || height >= 128){		// May change max height later on, making it generate any higher seems impractical at this stage.
								plugin.log.warningMSG("Invalid height '" + height + "'. New height not set.");
								player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height '" + height + "'. New height not set.");
							}else{
								plugin.log.warningMSG("New height '" + height + "' set.");
								player.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New height '" + height + "' set.");
								plugin.conf.set("height", height);
								plugin.saveSettings();
							}
				        }catch (Exception e){
				            plugin.log.warningMSG("Invalid height must be a number (Integer). New height not set.");
				            player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height must be a number (Integer). New height not set.");
				        }
					}else{
						plugin.log.warningMSG("No value given ignoring command. New height not set.");
						player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New height not set.");
					}
				}else if (args.length < 1){
					player.sendMessage(ChatColor.RED + "Usage: /flb help for more information.");
				}else{
					player.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Command. Try again.");
				}
		}
		return true;
	}
}
