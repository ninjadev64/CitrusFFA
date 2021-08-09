package com.amansprojects.citrusdev.ffa;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class DeathListener implements Listener {
	
	private Main plugin;
	private FileConfiguration config;
	public FileConfiguration statsConfig;
	public ConfigManager statsConfigManager;
	private static Economy econ = null;
	public DeathListener(Main main) {
		this.plugin = main;
		this.config = this.plugin.getConfig();
		this.statsConfigManager = new ConfigManager(this.plugin, "stats");
		this.statsConfigManager.createConfig();
		this.statsConfig = statsConfigManager.getConfig();
		setupEconomy();
	}
	
	private boolean setupEconomy() {
        if (this.plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = this.plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
	    if (event.getEntity() instanceof Player){
	        Player player = (Player)event.getEntity();
	        if (player.getLocation().getWorld().getName().equalsIgnoreCase(config.getString("world"))) {
	        	PlayerDeathEvent playerEvent = (PlayerDeathEvent)event;
	        	String deathConfigNode = new String("messages.death.natural.default");
	        	if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player){
	        		Player killer = event.getEntity().getKiller();
	        		switch (player.getLastDamageCause().getCause()) {
	        		case ENTITY_ATTACK:
	        			deathConfigNode = "messages.death.player.melee"; break;
	        		case PROJECTILE:
	        			deathConfigNode = "messages.death.player.projectile"; break;
	        		case FALL:
	        			deathConfigNode = "messages.death.player.fall"; break;
	        		case FIRE:
	        			deathConfigNode = "messages.death.player.fire"; break;
	        		case VOID:
	        			deathConfigNode = "messages.death.player.void"; break;
					default:
						deathConfigNode = "messages.death.player.other"; break;
	        		}
	        		playerEvent.setDeathMessage(String.format(config.getString(deathConfigNode), player.getName(), killer.getName()));
	        		if (killer != player) {
	        			if (config.getBoolean("gapple on kill")) {
	        				killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
	        			}
	        			if (econ != null && config.getInt("vault money for kill") != 0) {
	        				econ.depositPlayer(killer, config.getInt("vault money for kill"));
	        			}
	        			statsConfig.set(killer.getUniqueId() + ".kills", statsConfig.getInt(killer.getUniqueId() + ".kills") + 1);
	        		}
	        	} else {
	        		switch (player.getLastDamageCause().getCause()) {
	        		case FALL:
	        			deathConfigNode = "messages.death.natural.fall"; break;
	        		case FIRE:
	        			deathConfigNode = "messages.death.natural.fire"; break;
	        		case VOID:
	        			deathConfigNode = "messages.death.natural.void"; break;
	        		default:
	        			deathConfigNode = "messages.death.natural.default"; break;
	        		}
	        		playerEvent.setDeathMessage(String.format(config.getString(deathConfigNode), player.getName()));
	        	}
	        	statsConfig.set(player.getUniqueId() + ".deaths", statsConfig.getInt(player.getUniqueId() + ".deaths") + 1);
	        	player.spigot().respawn();
	        	this.plugin.getCommandListener().sendPlayerToGame(player);
	        }
	    }
	}
}
