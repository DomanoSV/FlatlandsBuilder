package net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dkebnh.bukkit.FlatlandsBuilder.FlatlandsBuilder;

public class CommandWizard implements CommandExecutor {
	
	private FlatlandsBuilder plugin;
	private String worldName = null, stepName = null, stepCommand = null;
	
	public CommandWizard(FlatlandsBuilder plugin) {
		this.plugin = plugin;
	}
	
	public boolean isValidBlock(String block){
		String materialTokens[] = block.split("[:]");		// Splits Block Type and Type ID into 2 so it can be parsed by the FlatlandsBuilder.
        
		Material mat = Material.matchMaterial(materialTokens[0]);
		
        if (materialTokens.length <= 2){
        	try{
        				if (materialTokens.length == 2){
        					int dataBlockValue = Integer.parseInt(materialTokens[1]);
        					if (dataBlockValue <= 15){
        					if (mat == null){
            					try{
            						mat = Material.getMaterial(Integer.parseInt(materialTokens[0]));
            					}catch (Exception e){
            		        
            					}
            		    	
            					if (mat == null){
            						return false;
            					}
            				}
            				
            				if (!mat.isBlock()){
            					return false;
            				}else{
            					return true;
            				}
        					}else{
        						return false;
        					}
        				}else{
        					if (mat == null){
            					try{
            						mat = Material.getMaterial(Integer.parseInt(materialTokens[0]));
            					}catch (Exception e){
            		        
            					}
            		    	
            					if (mat == null){
            						return false;
            					}
            				}
            				
            				if (!mat.isBlock()){
            					return false;
            				}else{
            					return true;
            				}	
        				}
        	}catch (Exception e){
            	plugin.log.warningMSG("[FlatlandsBuilder] Unable to validate block. New default block value not set.");
            	return false;
        	}
        }else{
        	plugin.log.warningMSG("[FlatlandsBuilder] Unable to validate block. New default block value not set.");
        	return false;
        }
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		
		if (!sender.hasPermission("flatlandsbuilder.wizard")){
			sender.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			sender.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "flatlandsbuilder.wizard");
			return true;
		}
		
		if (true) {
				if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.WHITE + "Command Wizard Help - " + ChatColor.GREEN + "FlatlandsBuilder");
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.RED + "Usage: /flbw create <WorldName>" + ChatColor.GREEN + " - Sets default generation height.");
					sender.sendMessage(ChatColor.RED + "Usage: /flbw cancel" + ChatColor.GREEN + " - Sets default generation mode.");
					return true;
				}else if(args.length == 2 && args[0].equalsIgnoreCase("create")){
					worldName = args[1];
					if (!plugin.wizardRunning){
						File worldFile = new File(plugin.getServer().getWorldContainer(), worldName);
						if (worldFile.exists() || plugin.conf.contains("worlds." + worldName)) {
							sender.sendMessage(ChatColor.RED + "A Folder/World already exists with this name!");
				            sender.sendMessage(ChatColor.RED + "If you are confident it is a world you can import with");
				        }else{
				        	plugin.wizard.set("wizard.isConfiguring", worldName);
                            plugin.wizard.set("global.wizardRunning", true);
                            plugin.wizard.set("global.wizardStage", 1);
                            plugin.wizardRunning = true;
                            plugin.wizardStage = 1;              
                            plugin.saveWizardState();
    						sender.sendMessage(ChatColor.WHITE + "FlatlandsBuilder command wizard has started for world");
    						sender.sendMessage(ChatColor.GREEN + worldName + ChatColor.WHITE + ". To continue please set " + ChatColor.GREEN + stepName + ChatColor.WHITE + " by using");				
    						sender.sendMessage(ChatColor.WHITE + "the command " + ChatColor.GREEN + stepCommand);
				        }
					}else{
						sender.sendMessage(ChatColor.WHITE + "It appears the FlatlandsBuilder command wizard is still");	
						sender.sendMessage(ChatColor.WHITE + "running to continue configuring " + ChatColor.GREEN + worldName + ChatColor.WHITE + " please set");				
						sender.sendMessage(ChatColor.GREEN + stepName + ChatColor.WHITE + " by using the command " + ChatColor.GREEN + stepCommand);
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /flbw help for more information.");
				}
		}
		return true;
	}
}
