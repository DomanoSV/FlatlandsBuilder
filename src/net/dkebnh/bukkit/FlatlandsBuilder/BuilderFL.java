package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.logging.Logger;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BuilderFL extends JavaPlugin {
	
	private Logger log = Logger.getLogger("Minecraft");
	
	public void logMessage(String msg){
		PluginDescriptionFile pdFile = this.getDescription();
		this.log.info("[FlatlandsBuilder] " + pdFile.getName() + " " + pdFile.getVersion() + " - " + msg);
	}
	
	public void onEnable(){
		this.logMessage("Enabled."); 		
	}
	
	public void onDisable(){
		this.logMessage("Disabled."); 
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		return new FLBGenerator(id);
	}
	
}
