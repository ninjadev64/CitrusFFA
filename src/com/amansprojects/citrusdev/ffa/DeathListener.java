package com.amansprojects.citrusdev.ffa;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {
	private final Main plugin;
	private FileConfiguration config;
	private FileConfiguration statsConfig;

	public DeathListener(Main main) {
		this.plugin = main;
		this.config = this.plugin.getConfig();
		this.statsConfig = this.plugin.statConfigManager.getConfig();
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
	    if (event.getEntity() instanceof Player){
	        Player player = (Player)event.getEntity();
	        if (player.getLocation().getWorld().getName().equalsIgnoreCase(config.getString("world"))) {
	        	PlayerDeathEvent playerEvent = (PlayerDeathEvent) event;
	        	String deathConfigNode = "messages.death.natural.default";
	        	if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player){
	        		Player killer = event.getEntity().getKiller();
					FFADeathEvent ffaDeathEvent = new FFADeathEvent(player, killer, event);
					Bukkit.getServer().getPluginManager().callEvent(ffaDeathEvent);
	        		switch (player.getLastDamageCause().getCause()) {
	        			case ENTITY_ATTACK: deathConfigNode = "messages.death.player.melee"; break;
	        			case PROJECTILE: deathConfigNode = "messages.death.player.projectile"; break;
	        			case FALL: deathConfigNode = "messages.death.player.fall"; break;
	        			case FIRE: deathConfigNode = "messages.death.player.fire"; break;
	        			case VOID: deathConfigNode = "messages.death.player.void"; break;
						default: deathConfigNode = "messages.death.player.other"; break;
	        		}
	        		playerEvent.setDeathMessage(String.format(config.getString(deathConfigNode), player.getName(), killer.getName()));
	        		if (killer != player) {
	        			if (config.getBoolean("gapple on kill")) { killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1)); }
	        			statsConfig.set(killer.getUniqueId() + ".kills", statsConfig.getInt(killer.getUniqueId() + ".kills") + 1);
	        			if (config.getBoolean("sounds.kill.enabled")) {
							killer.playSound(
									killer.getLocation(),
									Sound.valueOf(config.getString("sounds.kill.name")),
									config.getInt("sounds.kill.volume"),
									config.getInt("sounds.kill.pitch")
							);
						}
	        		}
	        	} else {
	        		switch (player.getLastDamageCause().getCause()) {
	        			case FALL: deathConfigNode = "messages.death.natural.fall"; break;
	        			case FIRE: deathConfigNode = "messages.death.natural.fire"; break;
	        			case VOID: deathConfigNode = "messages.death.natural.void"; break;
	        			default: deathConfigNode = "messages.death.natural.default"; break;
	        		}
	        		playerEvent.setDeathMessage(String.format(config.getString(deathConfigNode), player.getName()));
	        	}
	        	statsConfig.set(player.getUniqueId() + ".deaths", statsConfig.getInt(player.getUniqueId() + ".deaths") + 1);
				if (config.getBoolean("sounds.death.enabled")) {
					player.playSound(
							player.getLocation(),
							Sound.valueOf(config.getString("sounds.death.name")),
							config.getInt("sounds.death.volume"),
							config.getInt("sounds.death.pitch")
					);
				}
	        	player.spigot().respawn();
	        	this.plugin.getCommandListener().sendPlayerToGame(player);
	        }
	    }
	}
}