package net.dkebnh.bukkit.FlatlandsBuilder;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class FlatlandsBuilder extends JavaPlugin {
	
	protected FLBLogger log;
	
	private File confFile;
	public YamlConfiguration conf;
	int height = 64;
	int genMode = 2;
	String block1 = "wool:15";
	String block2 = "wool:7";
	String block3 = "wool:8";

	public void onEnable(){
		
		File dFolder = getDataFolder();		
		if(!dFolder.exists()) dFolder.mkdirs();		
		confFile = new File(dFolder, "config.yml");        
	
		if (confFile.exists()) {
			conf = YamlConfiguration.loadConfiguration(confFile);
			height = conf.getInt("height");
			genMode = conf.getInt("mode");
			block1 = conf.getString("block1");
			block2 = conf.getString("block2");
			block3 = conf.getString("block3");
			
			String[] vars = new String[5];
			
			vars[0] = "[FlatlandsBuilder] Default height is: " + Integer.toString(height);
			vars[1] = "[FlatlandsBuilder] Default generation mode is: " + Integer.toString(genMode);
			vars[2] = "[FlatlandsBuilder] Default fill block is: " + block1;
			vars[3] = "[FlatlandsBuilder] Default border 1 block is: " + block2;
			vars[4] = "[FlatlandsBuilder] Default border 2 block is: " + block3;
			
			for(int s = 0; s < vars.length; s ++){
				System.out.println(vars[s]);
			}

		}else{        	
			conf = new YamlConfiguration();        	
			conf.set("height", 64);       
			conf.set("mode", 2); 
			conf.set("block1", "wool:15");        	
			conf.set("block2", "wool:7");     
			conf.set("block3", "wool:8");     
			saveSettings();
		}
		
		this.log = new FLBLogger(this);
		this.getCommand("flb").setExecutor(new FLBCommandExecutor(this));
	}
	
	public void onDisable(){
 
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
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		return new FLBGenerator(id);
	}
	
}
