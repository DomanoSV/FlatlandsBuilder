package net.dkebnh.bukkit.FlatlandsBuilder.EventListeners;

import net.dkebnh.bukkit.FlatlandsBuilder.FlatlandsBuilder;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FLBPlayerLoginListener implements Listener {

	private FlatlandsBuilder plugin;
	
	public FLBPlayerLoginListener(FlatlandsBuilder plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (plugin.wizardRunning == true){
			if (player.hasPermission("flatlandsbuilder.notify")){
				player.sendMessage(ChatColor.WHITE + "Hello " + ChatColor.GREEN + playerName + ChatColor.WHITE + ", it appears the FlatlandsBuilder command");	
				player.sendMessage(ChatColor.WHITE + "wizard is still running to continue configuring " + ChatColor.GREEN + plugin.wizardWorld);				
				player.sendMessage(ChatColor.WHITE + "please set "+ ChatColor.GREEN + plugin.stepName.get(plugin.wizardStage) + ChatColor.WHITE + " by using the command:");
				player.sendMessage(ChatColor.GREEN + "  - " + plugin.stepCommand.get(plugin.wizardStage));
			}	
		}
	}

}
