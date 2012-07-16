package net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dkebnh.bukkit.FlatlandsBuilder.FlatlandsBuilder;

public class EditCommands implements CommandExecutor{
	private FlatlandsBuilder plugin;

	public EditCommands(FlatlandsBuilder plugin) {
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
		if (!sender.hasPermission("flatlandsbuilder.edit")){
			sender.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			sender.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "flatlandsbuilder.edit");
			return true;
		}
		
		if (true) {
				if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.WHITE + "Edit Commands Help - " + ChatColor.GREEN + "FlatlandsBuilder");
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.RED + "Usage: /flbe height <height>" + ChatColor.GREEN + " - Sets default generation height.");
					sender.sendMessage(ChatColor.RED + "Usage: /flbe block1 <block_id>" + ChatColor.GREEN + " - Sets the default fill block.");
					sender.sendMessage(ChatColor.RED + "Usage: /flbe block2 <block_id>" + ChatColor.GREEN + " - Sets default border 1 block.");
					sender.sendMessage(ChatColor.RED + "Usage: /flbe block3 <block_id>" + ChatColor.GREEN + " - Sets default border 2 block.");
					sender.sendMessage(ChatColor.RED + "Usage: /flbe mode <mode>" + ChatColor.GREEN + " - Sets default generation mode.");
					return true;
				}else if(args.length == 2 && args[0].equalsIgnoreCase("height")){		// Sets height variable in the config file.
					if (args[1] != null){
						int height = 0;
						try{
							height = Integer.parseInt(args[1]);
							
							if (height <= 0 || height >= 128){		// May change max height later on, making it generate any higher seems impractical at this stage.
								plugin.log.warningMSG("Invalid height '" + height + "'. New height not set.");
								sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height '" + height + "'. New height not set.");
							}else{
								plugin.log.warningMSG("New height '" + height + "' set.");
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New height '" + height + "' set.");
								plugin.conf.set("global.defaults.height", height);
								plugin.saveSettings();
							}
				        }catch (Exception e){
				            plugin.log.warningMSG("Invalid height must be a number (Integer). New height not set.");
				            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height must be a number (Integer). New height not set.");
				        }
					}else{
						plugin.log.warningMSG("No value given ignoring command. New height not set.");
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New height not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("mode")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String modeSelect = args[1].toLowerCase();
			        		
							List<String> genModechk = Arrays.asList("normal","grid","grid2","grid3","grid4","grid5","schematic");
			        		
			        		if (genModechk.contains(modeSelect)){
								if (modeSelect.equalsIgnoreCase("schematic")){
									plugin.log.warningMSG("Generation Mode '" + modeSelect + "' NOT yet implemented . New Generation Mode not set.");
									sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Generation Mode '" + modeSelect + "' NOT yet implemented . New Generation Mode not set.");
								}else{
									plugin.log.warningMSG("New  Generation Mode '" + modeSelect + "' set.");
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New Generation Mode '" + modeSelect + "' set.");
									plugin.conf.set("global.defaults.mode", modeSelect);
									plugin.saveSettings();
								}
			        		}else{
			        			plugin.log.warningMSG("Invalid Generation Mode '" + modeSelect + "'. New Generation Mode not set.");
								sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid  Generation Mode '" + modeSelect + "'. New Generation Mode not set.");
			        		}
			
				        }catch (Exception e){
				            plugin.log.warningMSG("Invalid Generation Mode must be either [normal, grid, grid2, checkered, checkered2, schematic]. New Generation Mode not set.");
				            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Generation Mode must be either [normal, grid, grid2, grid3, grid4, grid5, schematic]. New Generation Mode not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New Generation Mode not set.");
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New Generation Mode not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block1")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								plugin.log.warningMSG("New  fill block '" + block + "' set.");
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New fill block '" + block + "' set.");
								plugin.conf.set("global.defaults.block1", block);
								plugin.saveSettings();
							}else{
								plugin.log.warningMSG("Invalid fill block. New fill block not set.");
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid fill block. New fill block not set.");
							}
	    				    
				        }catch (Exception e){
				        		plugin.log.warningMSG("Invalid fill block. New fill block not set.");
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid fill block. New fill block not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New fill block not set.");
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New fill block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block2")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								plugin.log.warningMSG("New  border a block '" + block + "' set.");
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New border a block '" + block + "' set.");
								plugin.conf.set("global.defaults.block2", block);
								plugin.saveSettings();
							}else{
								plugin.log.warningMSG("Invalid border a block. New border a block not set.");
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border a block. New border a block not set.");
							}
	    				    
				        }catch (Exception e){
				        		plugin.log.warningMSG("Invalid border a block. New border a block not set.");
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border a block. New border a block not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New border a block not set.");
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New border a block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block3")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								plugin.log.warningMSG("New border b block '" + block + "' set.");
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New border b block '" + block + "' set.");
								plugin.conf.set("global.defaults.block3", block);
								plugin.saveSettings();
							}else{
								plugin.log.warningMSG("Invalid border b block. New border b block not set.");
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border b block. New border b block not set.");
							}
	    				    
				        }catch (Exception e){
				        		plugin.log.warningMSG("Invalid border b block. New border b block not set.");
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border b block. New border b block not set.");
				        }
					}else{ 
						plugin.log.warningMSG("No value given ignoring command. New border b block not set.");
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New border b block not set.");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Usage: /flbe help for more information.");
				}
		}
		return true;
	}
}
