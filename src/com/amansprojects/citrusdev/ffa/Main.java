package com.amansprojects.citrusdev.ffa;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	CommandListener commandListener = new CommandListener(this);
	DeathListener deathListener = new DeathListener(this);
	PlaceholderAPIExpansion placeholderAPIexpansion;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		getCommand("ffa").setExecutor(commandListener);
		getServer().getPluginManager().registerEvents(deathListener, this);
		if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			placeholderAPIexpansion = new PlaceholderAPIExpansion(this);
            placeholderAPIexpansion.register();
		}
		getLogger().info("CitrusFFA Enabled");
	}
	
	@Override
	public void onDisable() {
		deathListener.statsConfigManager.saveConfig();
		getLogger().info("CitrusFFA Disabled");
	}
	
	CommandListener getCommandListener() {
		return commandListener;
	}
}