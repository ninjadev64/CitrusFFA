package com.amansprojects.moonphase.ffa;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandListener implements CommandExecutor {	
	private Main plugin;
	private FileConfiguration config;
	
	public CommandListener(Main main) {
		this.plugin = main;
		this.config = this.plugin.getConfig();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			sendPlayerToGame(player);
            return true;
		}
		return false;
	}
	
	public void sendPlayerToGame(Player player) {
		FileConfiguration config = this.config;
		player.teleport(pickRandomLocation());
        for(PotionEffect effect : player.getActivePotionEffects()) 
            player.removePotionEffect(effect.getType());
        
        player.getInventory().clear();
        if (config.get("armour.helmet") != null) { player.getInventory().setHelmet(config.getItemStack("armour.helmet")); }
        if (config.get("armour.chestplate") != null) { player.getInventory().setChestplate(config.getItemStack("armour.chestplate")); }
        if (config.get("armour.leggings") != null) { player.getInventory().setLeggings(config.getItemStack("armour.leggings")); }
        if (config.get("armour.boots") != null) { player.getInventory().setBoots(config.getItemStack("armour.boots")); }
        
        if (config.get("items.one") != null) { player.getInventory().addItem(config.getItemStack("items.one")); }
        if (config.get("items.two") != null) { player.getInventory().addItem(config.getItemStack("items.two")); }
        if (config.get("items.three") != null) { player.getInventory().addItem(config.getItemStack("items.three")); }
        if (config.get("items.four") != null) { player.getInventory().addItem(config.getItemStack("items.four")); }
        if (config.get("items.five") != null) { player.getInventory().addItem(config.getItemStack("items.five")); }
        if (config.get("items.six") != null) { player.getInventory().addItem(config.getItemStack("items.six")); }
        if (config.get("items.seven") != null) { player.getInventory().addItem(config.getItemStack("items.seven")); }
        if (config.get("items.eight") != null) { player.getInventory().addItem(config.getItemStack("items.eight")); }
        if (config.get("items.nine") != null) { player.getInventory().addItem(config.getItemStack("items.nine")); }
        
        
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999, 1));
        player.setGameMode(GameMode.ADVENTURE);
	}
	
	public Location pickRandomLocation() {
		Random rand = new Random();
		int randomint = rand.nextInt(8);
		FileConfiguration config = this.config;
		String worldname = config.getString("world");
		Location[] locs = {
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.one.x"),
						config.getInt("spawnpoints.one.y"),
						config.getInt("spawnpoints.one.z"),
						config.getInt("spawnpoints.one.pitch"),
						config.getInt("spawnpoints.one.yaw")
				),
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.two.x"),
						config.getInt("spawnpoints.two.y"),
						config.getInt("spawnpoints.two.z"),
						config.getInt("spawnpoints.two.pitch"),
						config.getInt("spawnpoints.two.yaw")
				),
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.three.x"),
						config.getInt("spawnpoints.three.y"),
						config.getInt("spawnpoints.three.z"),
						config.getInt("spawnpoints.three.pitch"),
						config.getInt("spawnpoints.three.yaw")
				),
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.four.x"),
						config.getInt("spawnpoints.four.y"),
						config.getInt("spawnpoints.four.z"),
						config.getInt("spawnpoints.four.pitch"),
						config.getInt("spawnpoints.four.yaw")
				),
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.five.x"),
						config.getInt("spawnpoints.five.y"),
						config.getInt("spawnpoints.five.z"),
						config.getInt("spawnpoints.five.pitch"),
						config.getInt("spawnpoints.five.yaw")
				),
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.six.x"),
						config.getInt("spawnpoints.six.y"),
						config.getInt("spawnpoints.six.z"),
						config.getInt("spawnpoints.six.pitch"),
						config.getInt("spawnpoints.six.yaw")
				),
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.seven.x"),
						config.getInt("spawnpoints.seven.y"),
						config.getInt("spawnpoints.seven.z"),
						config.getInt("spawnpoints.seven.pitch"),
						config.getInt("spawnpoints.seven.yaw")
				),
				new Location(Bukkit.getWorld(worldname), 
						config.getInt("spawnpoints.eight.x"),
						config.getInt("spawnpoints.eight.y"),
						config.getInt("spawnpoints.eight.z"),
						config.getInt("spawnpoints.eight.pitch"),
						config.getInt("spawnpoints.eight.yaw")
				),
		};
		return locs[randomint];
	}
}