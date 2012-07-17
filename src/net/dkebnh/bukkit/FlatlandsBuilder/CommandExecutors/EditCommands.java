package net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

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
		PluginDescriptionFile pdFile = plugin.getDescription();
		
		if (!sender.hasPermission("flatlandsbuilder.edit")){
			sender.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			sender.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "flatlandsbuilder.edit");
			return true;
		}
		
		if (true) {
				if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.WHITE + "Edit Defaults Command Help - " + ChatColor.GREEN + "FlatlandsBuilder " + pdFile.getVersion());
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.GREEN + "/flbd height <int>" + ChatColor.WHITE + " - Sets default generation height.");
					sender.sendMessage(ChatColor.GREEN + "/flbd mode <mode>" + ChatColor.WHITE + " - Sets the default generation mode.");
					sender.sendMessage(ChatColor.GREEN + "/flbd block1 <block>" + ChatColor.WHITE + " - Sets default block 1.");
					sender.sendMessage(ChatColor.GREEN + "/flbd block2 <block>" + ChatColor.WHITE + " - Sets default block 2.");
					sender.sendMessage(ChatColor.GREEN + "/flbd help 2" + ChatColor.WHITE + " - Help page 2.");
					return true;
				}else if (args.length == 2 && args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("2")){
					sender.sendMessage(ChatColor.WHITE + "Edit Defaults Command Help - 2 - " + ChatColor.GREEN + "FlatlandsBuilder " + pdFile.getVersion());
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.GREEN + "/flbd block3 <block>" + ChatColor.WHITE + " - Sets default block 3.");
					sender.sendMessage(ChatColor.GREEN + "/flbd plots <true/false>" + ChatColor.WHITE + " - Enables/Disables plots by default.");
					sender.sendMessage(ChatColor.GREEN + "/flbd plotsize <int>" + ChatColor.WHITE + " - Sets default plot size.");
					sender.sendMessage(ChatColor.GREEN + "/flbd pathblock <block>" + ChatColor.WHITE + " - Sets default path block.");
					sender.sendMessage(ChatColor.GREEN + "/flbd wallblock <block>" + ChatColor.WHITE + " - Sets default wall block.");
					return true;
				}else if(args.length == 2 && args[0].equalsIgnoreCase("height")){		// Sets height variable in the config file.
					if (args[1] != null){
						int height = 0;
						try{
							height = Integer.parseInt(args[1]);
							
							if (height <= 0 || height >= 128){		// May change max height later on, making it generate any higher seems impractical at this stage.
								sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height '" + height + "'. New height not set.");
							}else{
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New height '" + height + "' set.");
								plugin.conf.set("global.defaults.height", height);
								plugin.saveSettings();
							}
				        }catch (Exception e){
				            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height must be a number (Integer). New height not set.");
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New height not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("mode")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String modeSelect = args[1].toLowerCase();
			        		
							List<String> genModechk = Arrays.asList("normal","grid","grid2","grid3","grid4","grid5","schematic");
			        		
			        		if (genModechk.contains(modeSelect)){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New Generation Mode '" + modeSelect + "' set.");
								plugin.conf.set("global.defaults.mode", modeSelect);
								plugin.saveSettings();
			        		}else{
								sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid  Generation Mode '" + modeSelect + "'. New Generation Mode not set.");
			        		}
				        }catch (Exception e){
				            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Generation Mode must be either [normal, grid, grid2, grid3, grid4, grid5]. New Generation Mode not set.");
				        }
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New Generation Mode not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block1")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New fill block '" + block + "' set.");
								plugin.conf.set("global.defaults.block1", block);
								plugin.saveSettings();
							}else{
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid fill block. New fill block not set.");
							}
				        }catch (Exception e){
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid fill block. New fill block not set.");
				        }
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New fill block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block2")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New border a block '" + block + "' set.");
								plugin.conf.set("global.defaults.block2", block);
								plugin.saveSettings();
							}else{
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border a block. New border a block not set.");
							}
				        }catch (Exception e){
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border a block. New border a block not set.");
				        }
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New border a block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block3")){		// Sets height variable in the config file.
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "New border b block '" + block + "' set.");
								plugin.conf.set("global.defaults.block3", block);
								plugin.saveSettings();
							}else{
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border b block. New border b block not set.");
							}
				        }catch (Exception e){
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid border b block. New border b block not set.");
				        }
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New border b block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("plots")){
					if (args[1] != null){
						try{
							String check = args[1].toLowerCase();
								
							if (check.equalsIgnoreCase("true")){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Plots enabled = " + check);
								plugin.conf.set("global.defaults.plots", true);             
		                        plugin.saveSettings();
							}else if (check.equalsIgnoreCase("false")){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Plots enabled = " + check);
								plugin.conf.set("global.defaults.plots", false);
								plugin.saveSettings();
							}else{
								sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid. Enable plots not set.");
							}    				    
						}catch (Exception e){
					        sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid. Enable plots not set.");
					    }
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. Enable plots not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("plotsize")){
					if (args[1] != null){
							try{
								int plotsize = Integer.parseInt(args[1]);
								List<Integer> plotSizechk = Arrays.asList(64,128,256,512,1024);
					        		
					        	if (plotSizechk.contains(plotsize)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Plot size '" + plotsize + "' set.");
									plugin.conf.set("global.defaults.plotsize", plotsize);
									plugin.saveSettings();
					        	}else{
									sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid plot size must be either [64, 128, 256, 512, 1024]. Plot size not set.");
					        	}
							}catch (Exception e){
						            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid plot size. Plot size not set.");
							}
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New Plot size not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("pathblock")){
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Path block '" + block + "' set.");
								plugin.conf.set("global.defaults.pathblock", block);
								plugin.saveSettings();
							}else{
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Path block. New Path block not set.");
							}    
				        }catch (Exception e){
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Path block. New Path block not set.");
				        }
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New Path block not set.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("wallblock")){
					if (args[1] != null){
						try{
							String block = args[1].toLowerCase();
							
							if (isValidBlock(block)){
								sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Wall block '" + block + "' set.");
								plugin.conf.set("global.defaults.wallblock", block);
								plugin.saveSettings();
							}else{
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Wall block. New Wall block not set.");
							} 
				        }catch (Exception e){
				        		sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Wall block. New Wall block not set.");
				        }
					}else{ 
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "No value given ignoring command. New Wall block not set.");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Usage: " + ChatColor.GREEN + "/flbd help" + ChatColor.WHITE + " for more information.");
				}
		}
		return true;
	}
}
