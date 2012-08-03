package net.dkebnh.bukkit.FlatlandsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors.CommandWizard;
import net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors.EditCommands;
import net.dkebnh.bukkit.FlatlandsBuilder.CommandExecutors.PluginCommands;
import net.dkebnh.bukkit.FlatlandsBuilder.EventListeners.FLBPlayerLoginListener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class FlatlandsBuilder extends JavaPlugin {
	
	private File confFile, saveWizard;
	
	public YamlConfiguration conf, wizard;
	public FLBLogger log;
	public boolean wizardRunning = false;
	public int wizardStage = 0;
	public String wizardWorld = null;
	public List<String> stepName = Arrays.asList("Wizard Not Running","Defaults or Height","Generation Mode","BlockA","BlockB","BlockC","Enable Plots","Plots Size","Path Block","Wall Block","Complete");
	public List<String> stepCommand = Arrays.asList("null","/flbw usedefaults or /flbw height <int>","/flbw mode <mode>","/flbw block1 <block>","/flbw block2 <block>","/flbw block3 <block>","/flbw plots <true/false>","/flbw plotsize <int>","/flbw pathblock <block>","/flbw wallblock <block>", "/flbw done");
	
	int height = 64;
	String genMode = "grid2";
	String block1 = "wool:15";
	String block2 = "wool:7";
	String block3 = "wool:8";
	boolean plotsEnabled = false;
	int plotSize = 64;
	String pathBlock = "wool";
	String wallBlock = "wool:3";
	List<String> blacklist = Arrays.asList("lava","water","tnt","bedrock","fire");

    public void onEnable(){
		this.log = new FLBLogger(this);
		
		File dFolder = getDataFolder();		
		if(!dFolder.exists()) dFolder.mkdirs();		
		confFile = new File(dFolder, "config.yml");       
		saveWizard = new File(dFolder, "wizard.yml");
		this.getServer().getWorldContainer();

		if (confFile.exists()) {
			conf = YamlConfiguration.loadConfiguration(confFile);
			plotsEnabled = conf.getBoolean("global.defaults.plots");   	
			plotSize = conf.getInt("global.defaults.plotsize");  
			height = conf.getInt("global.defaults.height");       
			genMode = conf.getString("global.defaults.mode"); 
			block1 = conf.getString("global.defaults.block1");        	
			block2 = conf.getString("global.defaults.block2");     
			block3 = conf.getString("global.defaults.block3");
			pathBlock = conf.getString("global.defaults.pathblock");     
			wallBlock = conf.getString("global.defaults.wallblock");
			blacklist = conf.getStringList("global.blacklist");
		}else{        	
			conf = new YamlConfiguration();        	
			conf.set("global.defaults.plots", false);   	
			conf.set("global.defaults.plotsize", 64);  
			conf.set("global.defaults.height", 64);       
			conf.set("global.defaults.mode", "grid2"); 
			conf.set("global.defaults.block1", "wool:15");        	
			conf.set("global.defaults.block2", "wool:7");     
			conf.set("global.defaults.block3", "wool:8");
			conf.set("global.defaults.pathblock", "wool:0");     
			conf.set("global.defaults.wallblock", "wool:3");
			conf.set("global.blacklist", blacklist);
			saveSettings();
		}
		
		if (saveWizard.exists()){
			wizard = YamlConfiguration.loadConfiguration(saveWizard);
			wizardRunning = wizard.getBoolean("global.wizardRunning");   	
			wizardStage = wizard.getInt("global.wizardStage"); 
			if(wizard.contains("wizard.isConfiguring")){
				wizardWorld = wizard.getString("wizard.isConfiguring"); 
			}
		}else{
			wizard = new YamlConfiguration();        	
			wizard.set("global.wizardRunning", false);   	
			wizard.set("global.wizardStage", 0);  
			saveWizardState();
		}
		
		log.infoMSG("Default height is: " + Integer.toString(height));
		log.infoMSG("Default generation mode is: " + genMode);
		log.infoMSG("Default BlockA is: " + block1);
		log.infoMSG("Default BlockB is: " + block2);
		log.infoMSG("Default BlockC is: " + block3);
		log.infoMSG("Default for plots enabled is: " + plotsEnabled);
		log.infoMSG("Default plot size is: " + plotSize);
		log.infoMSG("Default road block is: " + pathBlock);
		log.infoMSG("Default wall block is: " + wallBlock);
		log.infoMSG("Loading blacklist, please wait ...");
		
		for(int i = 0; i < blacklist.size(); i ++){
			log.infoMSGNullFormat("  - " + blacklist.get(i));
		}
		
		if(wizardRunning)log.infoMSG("Command Wizard is still running, please finish configuring " + wizardWorld + ", wizard is up to step " + stepName.get(wizardStage));
		log.infoMSG("Done, continuing to load bukkit ...");
		
		this.getCommand("flb").setExecutor(new PluginCommands(this));
		this.getCommand("flbd").setExecutor(new EditCommands(this));
		this.getCommand("flbw").setExecutor(new CommandWizard(this));
		this.getServer().getPluginManager().registerEvents(new FLBPlayerLoginListener(this), this);
	}
	
	public void onDisable(){
		this.blacklist = null;
		this.wizard = null;
		this.conf = null;
		this.log = null;
	}
	
	public boolean reloadAll(){
		conf = new YamlConfiguration();
		wizard = new YamlConfiguration();
		
		try {
			conf.load(confFile);
			plotsEnabled = conf.getBoolean("global.defaults.plots");   	
			plotSize = conf.getInt("global.defaults.plotsize");  
			height = conf.getInt("global.defaults.height");       
			genMode = conf.getString("global.defaults.mode"); 
			block1 = conf.getString("global.defaults.block1");        	
			block2 = conf.getString("global.defaults.block2");     
			block3 = conf.getString("global.defaults.block3");
			pathBlock = conf.getString("global.defaults.pathblock");     
			wallBlock = conf.getString("global.defaults.wallblock");
			blacklist = conf.getStringList("global.blacklist");

			wizard.load(saveWizard);
			wizardRunning = wizard.getBoolean("global.wizardRunning");   	
			wizardStage = wizard.getInt("global.wizardStage"); 
			if(wizard.contains("wizard.isConfiguring")){
				wizardWorld = wizard.getString("wizard.isConfiguring"); 
			}
			
			log.infoMSG("Reloading configuration, please wait...");
			log.infoMSG("Default height is: " + Integer.toString(height));
			log.infoMSG("Default generation mode is: " + genMode);
			log.infoMSG("Default BlockA is: " + block1);
			log.infoMSG("Default BlockB is: " + block2);
			log.infoMSG("Default BlockC is: " + block3);
			log.infoMSG("Default for plots enabled is: " + plotsEnabled);
			log.infoMSG("Default plot size is: " + plotSize);
			log.infoMSG("Default road block is: " + pathBlock);
			log.infoMSG("Default wall block is: " + wallBlock);
			log.infoMSG("Loading blacklist, please wait...");
			
			for(int i = 0; i < blacklist.size(); i ++){
				log.infoMSGNullFormat("  - " + blacklist.get(i));
			}
			
			if(wizardRunning)log.infoMSG("Command Wizard is still running, please finish configuring " + wizardWorld + ", wizard is up to step " + stepName.get(wizardStage));
			log.infoMSG("Done.");
			return true;
		} catch (FileNotFoundException e) {
			// do nothing
		} catch (Throwable e) {
			throw new IllegalStateException("Error loading configuration files.", e);
		}
		return false;
	}
	
	public boolean saveSettings() {
		if (!confFile.exists()) {			
			confFile.getParentFile().mkdirs();		
		}try{			
			conf.save(confFile);			
			return true;		
			}catch (IOException e){
				e.printStackTrace();		
			}			
		return false;
	}
	
	public boolean saveWizardState() {
		if (!saveWizard.exists()) {			
			saveWizard.getParentFile().mkdirs();		
		}try{			
			wizard.save(saveWizard);			
			return true;		
			}catch (IOException e){
				e.printStackTrace();		
			}			
		return false;
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		String idmode = null, idmodeparse = null, idblock1 = "wool:15", idblock2 = "wool:7", idblock3 = "wool:8";
		int idheight;
		boolean plotsold = false;
		
		if (id != null && conf.contains("worlds." + worldName) == false){
			log.infoMSG("Old configuration method detected converting to new configuration file method, please wait...");
			String[] parts = id.split("[;]");
        	id = parts.length == 0 ? "" : parts[0];
        	
        	if(parts.length >= 2) {
        		idmodeparse = parts[1];
        	}
        	
    		if (parts[0].length() > 0){
    			String tokens[] = parts[0].split("[,]");
                
                if (tokens.length > 4){			// Checks to see if a larger string has been provided, and adjusts accordingly.
					String tokenStore[] = new String[4];
					
						for (int tokenNumb = 0; tokenNumb < 4; tokenNumb ++){
							tokenStore[tokenNumb] = tokens[tokenNumb];
							}

					tokens = tokenStore;
					
				}
                
                idheight = Integer.parseInt(tokens[0]);
                
                if (tokens.length == 4){		// Sets generation format based on number of variables entered. 
                	idmode = "grid2";
                	idblock1 = tokens[1];
                	idblock2 = tokens[2];
                	idblock3 = tokens[3];
                    this.conf.set("worlds." + worldName + ".block1", idblock1);
                    this.conf.set("worlds." + worldName + ".block2", idblock2);
                    this.conf.set("worlds." + worldName + ".block3", idblock3);
            	}else if (tokens.length == 3){
            		idmode = "grid";
            		idblock1 = tokens[1];
            		idblock2 = tokens[2];
                    this.conf.set("worlds." + worldName + ".block1", idblock1);
                    this.conf.set("worlds." + worldName + ".block2", idblock2);
            	}else if (tokens.length == 2){
            		idmode = "normal";
            		idblock1 = tokens[1];
                    this.conf.set("worlds." + worldName + ".block1", idblock1);
            	}else if(tokens.length == 1){
                	idmode = "grid2";
                    this.conf.set("worlds." + worldName + ".block1", idblock1);
                    this.conf.set("worlds." + worldName + ".block2", idblock2);
                    this.conf.set("worlds." + worldName + ".block3", idblock3);
            	}
                
                if (idmodeparse != null){
                	if (tokens.length == 4){		// Sets generation format based on number of variables entered.
                		if(idmodeparse.equalsIgnoreCase("grid5")){
                			idmode = "grid5";
                		}else if(idmodeparse.equalsIgnoreCase("grid4")){
                			idmode = "grid4";
                		}else{
                			idmode = "grid2";
                		}
                	}else if (tokens.length == 3){
                		if(idmodeparse.equalsIgnoreCase("grid3")){
                			idmode = "grid3";
                		}else{
                			idmode = "grid";
                		}
                	}else if (tokens.length == 2){
    						idmode = "normal";
                	}
                }
                this.conf.set("worlds." + worldName + ".height", idheight);
                this.conf.set("worlds." + worldName + ".mode", idmode);
                this.conf.set("worlds." + worldName + ".plots", plotsold);
                saveSettings();
                log.infoMSG("Done. Saved world settings to configuration file. Continuing to load bukkit, please wait...");
    		}
		}
		
		return new FLBGenerator(this, worldName);
	}
	
}
