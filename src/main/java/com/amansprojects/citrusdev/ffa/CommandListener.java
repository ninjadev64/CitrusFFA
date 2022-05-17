package com.amansprojects.citrusdev.ffa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class CommandListener implements CommandExecutor {
	private final Main plugin;
	private FileConfiguration config;
	
	public CommandListener(Main main) {
		this.plugin = main;
		this.config = this.plugin.getConfig();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (args.length >= 1) if (args[0].equalsIgnoreCase("leave")) {
				ConfigurationSection leaveConfig = config.getConfigurationSection("leave command");
				if (!leaveConfig.getBoolean("enabled")) {
					player.sendMessage("§cYou are not allowed to use this command!");
					return false;
				}

				player.getInventory().clear();
				player.getInventory().setArmorContents(null);
				ConfigurationSection lobbyLocationConfig = leaveConfig.getConfigurationSection("location");
				player.teleport(new Location(Bukkit.getWorld(
					lobbyLocationConfig.getString("world")),
					lobbyLocationConfig.getInt("x"), lobbyLocationConfig.getInt("y"), lobbyLocationConfig.getInt("z"),
					lobbyLocationConfig.getInt("pitch"), lobbyLocationConfig.getInt("yaw")
				));
				return true;
			}

			sendPlayerToGame(player);
            return true;
		}
		return false;
	}

	public void sendPlayerToGame(Player player) {
        for(PotionEffect effect : player.getActivePotionEffects()) { player.removePotionEffect(effect.getType()); }
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
        
        player.setGameMode(GameMode.ADVENTURE);
		player.teleport(pickRandomLocation());
	}

	public Location pickRandomLocation() {
		Random rand = new Random();
		int randomInt = rand.nextInt(8);
		FileConfiguration config = this.config;
		String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight"};
		List<Location> locations = new ArrayList<Location>();
		for (String number : numbers){
				locations.add(new Location(
						Bukkit.getWorld(config.getString("world")),
						config.getInt("spawnpoints." + number + ".x"),
						config.getInt("spawnpoints." + number + ".y"),
						config.getInt("spawnpoints." + number + ".z"),
						config.getInt("spawnpoints." + number + ".pitch"),
						config.getInt("spawnpoints." + number + ".yaw")
				));
		}
		return locations.get(randomInt);
	}
}