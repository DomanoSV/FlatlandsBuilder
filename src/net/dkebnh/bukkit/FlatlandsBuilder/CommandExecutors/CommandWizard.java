package net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import net.dkebnh.bukkit.FlatlandsBuilder.FlatlandsBuilder;

public class CommandWizard implements CommandExecutor {
	
	private FlatlandsBuilder plugin;
	private String worldName = null;
	
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
            	plugin.log.warningMSG("[FlatlandsBuilder] Unable to validate block. New block value not set.");
            	return false;
        	}
        }else{
        	plugin.log.warningMSG("[FlatlandsBuilder] Unable to validate block. New block value not set.");
        	return false;
        }
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		
		if (!sender.hasPermission("flatlandsbuilder.wizard")){
			sender.sendMessage(ChatColor.WHITE + "You do not have any of the required permission(s)");
			sender.sendMessage(ChatColor.WHITE + " - " + ChatColor.GREEN + "flatlandsbuilder.wizard");
			return true;
		}
		
		if (true) {
				if (args.length == 1 && args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.WHITE + "Command Wizard Help - " + ChatColor.GREEN + "FlatlandsBuilder " + pdFile.getVersion());
					sender.sendMessage(ChatColor.WHITE + "----------------------------------------------------");
					sender.sendMessage(ChatColor.GREEN +"/flbw create <world>" + ChatColor.WHITE + " - Starts Command Wizard.");
					sender.sendMessage(ChatColor.GREEN +"/flbw cancel" + ChatColor.WHITE + " - Cancels Command Wizard.");
					return true;
				}else if(args.length == 2 && args[0].equalsIgnoreCase("create")){
					worldName = args[1];
					if (!plugin.wizardRunning){
						File worldFile = new File(plugin.getServer().getWorldContainer(), worldName);
						if (worldFile.exists() || plugin.conf.contains("worlds." + worldName)) {
							sender.sendMessage(ChatColor.RED + "A world folder already exists or a world has been configured");
				            sender.sendMessage(ChatColor.RED + "with this name! If you are confident it is a world you can");
				            sender.sendMessage(ChatColor.RED + "generate or delete it.");
				        }else{
                            plugin.wizard.set("global.wizardRunning", true);
                            plugin.wizard.set("global.wizardStage", 1);
				        	plugin.wizard.set("wizard.isConfiguring", worldName);
                            plugin.wizardRunning = true;
                            plugin.wizardStage = 1;              
                            plugin.wizardWorld = worldName;
                            plugin.saveWizardState();
    						sender.sendMessage(ChatColor.WHITE + "FlatlandsBuilder command wizard has started for world");
    						sender.sendMessage(ChatColor.GREEN + worldName + ChatColor.WHITE + ". To continue please set " + ChatColor.GREEN + plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + " by using");				
    						sender.sendMessage(ChatColor.WHITE + "the command " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
				        }
					}else{
						sender.sendMessage(ChatColor.WHITE + "It appears the FlatlandsBuilder command wizard is still");	
						sender.sendMessage(ChatColor.WHITE + "running to continue configuring " + ChatColor.GREEN + plugin.wizardWorld + ChatColor.WHITE + " please set");				
						sender.sendMessage(ChatColor.GREEN + plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + " by using the command " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("height")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 1) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							int height = 0;
							
							try{
								height = Integer.parseInt(args[1]);
								
								if (height <= 0 || height >= 128){		// May change max height later on, making it generate any higher seems impractical at this stage.
									sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height '" + height + "'. New height not set.");
								}else{
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Height '" + height + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".height", height);
			                        plugin.wizard.set("global.wizardStage", 2);
			                        plugin.wizardStage = 2;              
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
								}
							}catch (Exception e){
								sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid height must be a number (Integer). New height not set.");
							}
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("mode")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 2) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								String modeSelect = args[1].toLowerCase();
								List<String> genModechk = Arrays.asList("normal","grid","grid2","grid3","grid4","grid5");
					        		
					        	if (genModechk.contains(modeSelect)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Generation Mode '" + modeSelect + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".mode", modeSelect);
				                    plugin.wizard.set("global.wizardStage", 3);
				                    plugin.wizardStage = 3;              
				                    plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
					        	}else{
									sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid Generation Mode must be either [normal, grid, grid2, grid3, grid4, grid5]. New Generation Mode not set.");
					        	}
					
							}catch (Exception e){
						            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid  Generation Mode. New Generation Mode not set.");
							}
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block1")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 3) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								String block = args[1].toLowerCase();
								String mode = plugin.wizard.getString("temp." + plugin.wizardWorld + ".mode");
									
								if (isValidBlock(block)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Block1 '" + block + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".block1", block);
									if (mode.equalsIgnoreCase("normal")){
				                        plugin.wizard.set("global.wizardStage", 6);
				                        plugin.wizardStage = 6; 
									}else{
				                        plugin.wizard.set("global.wizardStage", 4);
				                        plugin.wizardStage = 4; 
									}
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
								}else{
						        	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Block1 not set.");
								}
			    				    
							}catch (Exception e){
						        sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Block1 not set.");
						    }
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block2")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 4) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								String block = args[1].toLowerCase();
								String mode = plugin.wizard.getString("temp." + plugin.wizardWorld + ".mode");
									
								if (isValidBlock(block)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Block2 '" + block + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".block2", block);
									if (mode.equalsIgnoreCase("grid") || mode.equalsIgnoreCase("grid3")){
				                        plugin.wizard.set("global.wizardStage", 6);
				                        plugin.wizardStage = 6; 
									}else{
				                        plugin.wizard.set("global.wizardStage", 5);
				                        plugin.wizardStage = 5; 
									}            
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
								}else{
						        	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Block2 not set.");
								}
							}catch (Exception e){
						        sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Block2 not set.");
						    }
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("block3")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 5) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								String block = args[1].toLowerCase();
									
								if (isValidBlock(block)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Block3 '" + block + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".block3", block);
			                        plugin.wizard.set("global.wizardStage", 6);
			                        plugin.wizardStage = 6;              
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
								}else{
						        	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Block3 not set.");
								}
			    				    
							}catch (Exception e){
						        sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Block3 not set.");
						    }
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("plots")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 6) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								String check = args[1].toLowerCase();
									
								if (check.equalsIgnoreCase("true")){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Plots enabled = " + check);
									plugin.wizard.set("temp." + plugin.wizardWorld + ".plots", true);
			                        plugin.wizard.set("global.wizardStage", 7);
			                        plugin.wizardStage = 7;              
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
								}else if (check.equalsIgnoreCase("false")){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Plots enabled = " + check);
									plugin.wizard.set("temp." + plugin.wizardWorld + ".plots", false);
			                        plugin.wizard.set("global.wizardStage", 10);
			                        plugin.wizardStage = 10;              
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard is now " + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + " to continue please use:");
									sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
								}else{
									sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid. Enable plots not set.");
								}    				    
							}catch (Exception e){
						        sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid. Enable plots not set.");
						    }
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("plotsize")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 7) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								int plotsize = Integer.parseInt(args[1]);
								List<Integer> plotSizechk = Arrays.asList(64,128,256,512,1024);
					        		
					        	if (plotSizechk.contains(plotsize)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Plot size '" + plotsize + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".plotsize", plotsize);
				                    plugin.wizard.set("global.wizardStage", 8);
				                    plugin.wizardStage = 8;              
				                    plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
					        	}else{
									sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid plot size must be either [64, 128, 256, 512, 1024]. Plot size not set.");
					        	}
					
							}catch (Exception e){
						            sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid plot size. Plot size not set.");
							}
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("pathblock")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 8) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								String block = args[1].toLowerCase();
									
								if (isValidBlock(block)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Path Block '" + block + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".pathblock", block);
			                        plugin.wizard.set("global.wizardStage", 9);
			                        plugin.wizardStage = 9;              
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting " + ChatColor.WHITE + ":");
									sender.sendMessage(ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + "  - " + ChatColor.GREEN + plugin.stepCommand.get(plugin.wizardStage));
								}else{
						        	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Path Block not set.");
								}
			    				    
							}catch (Exception e){
						        sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Path Block not set.");
						    }
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 2 && args[0].equalsIgnoreCase("wallblock")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 9) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								String block = args[1].toLowerCase();
									
								if (isValidBlock(block)){
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Wall Block '" + block + "' set.");
									plugin.wizard.set("temp." + plugin.wizardWorld + ".wallblock", block);
			                        plugin.wizard.set("global.wizardStage", 10);
			                        plugin.wizardStage = 10;              
			                        plugin.saveWizardState();
									sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard is now " + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + " to continue please use:");
									sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
								}else{
						        	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Wall Block not set.");
								}
			    				    
							}catch (Exception e){
						        sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Invalid block. Wall Block not set.");
						    }
				        }
					}else{
						sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 1 && args[0].equalsIgnoreCase("done")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 10) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								plugin.conf.set("worlds." + plugin.wizardWorld + ".height", plugin.wizard.getInt("temp." + plugin.wizardWorld + ".height"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".mode", plugin.wizard.getString("temp." + plugin.wizardWorld + ".mode"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".block1", plugin.wizard.getString("temp." + plugin.wizardWorld + ".block1"));
								if (plugin.wizard.contains("temp." + plugin.wizardWorld + ".block2")) plugin.conf.set("worlds." + plugin.wizardWorld + ".block2", plugin.wizard.getString("temp." + plugin.wizardWorld + ".block2"));
								if (plugin.wizard.contains("temp." + plugin.wizardWorld + ".block3")) plugin.conf.set("worlds." + plugin.wizardWorld + ".block3", plugin.wizard.getString("temp." + plugin.wizardWorld + ".block3"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".plots", plugin.wizard.getBoolean("temp." + plugin.wizardWorld + ".plots"));
								if (plugin.wizard.contains("temp." + plugin.wizardWorld + ".plotsize")) plugin.conf.set("worlds." + plugin.wizardWorld + ".plotsize", plugin.wizard.getInt("temp." + plugin.wizardWorld + ".plotsize"));
								if (plugin.wizard.contains("temp." + plugin.wizardWorld + ".pathblock")) plugin.conf.set("worlds." + plugin.wizardWorld + ".pathblock", plugin.wizard.getString("temp." + plugin.wizardWorld + ".pathblock"));
								if (plugin.wizard.contains("temp." + plugin.wizardWorld + ".wallblock")) plugin.conf.set("worlds." + plugin.wizardWorld + ".wallblock", plugin.wizard.getString("temp." + plugin.wizardWorld + ".wallblock"));
								plugin.saveSettings();
								
	                        	plugin.wizardRunning = false;
	                        	plugin.wizardStage = 0;      
	                        	plugin.wizard = new YamlConfiguration();
	                        	plugin.wizard.set("global.wizardRunning", false);   	
	                        	plugin.wizard.set("global.wizardStage", 0);  
	                        	plugin.saveWizardState();
	                        	sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard succesfully completed.");
	                        	sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Configuration Saved. Please use:");
	                        	sender.sendMessage(ChatColor.GREEN + "/mv create " + plugin.wizardWorld + " NORMAL -g FlatlandsBuilder -t FLAT");
							}catch (Exception e){
	            				e.printStackTrace();
	            				sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard has not been succesfully completed.");
						    }
				        }
                    }else{
                    	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 1 && args[0].equalsIgnoreCase("usedefaults")){
					if (plugin.wizardRunning){
						if (plugin.wizardStage != 1) {
							sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Please continue the wizard by setting "  + ChatColor.GREEN+ plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + ".");
							sender.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
				        }else{
							try{
								plugin.conf.set("worlds." + plugin.wizardWorld + ".height", plugin.conf.getInt("global.defaults.height"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".mode", plugin.conf.getString("global.defaults.mode"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".block1", plugin.conf.getString("global.defaults.block1"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".block2", plugin.conf.getString("global.defaults.block2"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".block3", plugin.conf.getString("global.defaults.block3"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".plots", plugin.conf.getBoolean("global.defaults.plots"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".plotsize", plugin.conf.getInt("global.defaults.plotsize"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".pathblock", plugin.conf.getString("global.defaults.pathblock"));
								plugin.conf.set("worlds." + plugin.wizardWorld + ".wallblock", plugin.conf.getString("global.defaults.wallblock"));
								plugin.saveSettings();
								
	                        	plugin.wizardRunning = false;
	                        	plugin.wizardStage = 0;      
	                        	plugin.wizard = new YamlConfiguration();
	                        	plugin.wizard.set("global.wizardRunning", false);   	
	                        	plugin.wizard.set("global.wizardStage", 0);  
	                        	plugin.saveWizardState();
	                        	sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard succesfully completed.");
	                        	sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Configuration Saved. Please use:");
	                        	sender.sendMessage(ChatColor.GREEN + "/mv create " + plugin.wizardWorld + " NORMAL -g FlatlandsBuilder -t FLAT");
							}catch (Exception e){
	            				e.printStackTrace();
	            				sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard has not been succesfully completed.");
						    }
				        }
                    }else{
                    	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else if(args.length == 1 && args[0].equalsIgnoreCase("cancel")){
					if (plugin.wizardRunning){
                        try{
                        	plugin.wizardRunning = false;
                        	plugin.wizardStage = 0;      
                        	plugin.wizard = new YamlConfiguration();
                        	plugin.wizard.set("global.wizardRunning", false);   	
                        	plugin.wizard.set("global.wizardStage", 0);  
                        	plugin.saveWizardState();
                        	sender.sendMessage(ChatColor.GREEN + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard succesfully cancelled.");
            			}catch (Exception e){
            				e.printStackTrace();
            				sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Command wizard has not been succesfully cancelled.");
            			}
                    }else{
                    	sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "There is no instance of the command wizard running, command ignored.");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "[FlatlandsBuilder] " + ChatColor.WHITE + "Usage: " + ChatColor.GREEN + "/flbw help" + ChatColor.WHITE + " for more information.");
				}
		}
		return true;
	}
}
