package com.amansprojects.moonphase.ffa;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	CommandListener commandListener = new CommandListener(this);
	DeathListener deathListener = new DeathListener(this);
	PlaceholderAPIExpansion placeholderAPIexpansion = new PlaceholderAPIExpansion(this);

	@Override
	public void onEnable() {
		saveDefaultConfig();
		getCommand("ffa").setExecutor(commandListener);
		getServer().getPluginManager().registerEvents(deathListener, this);
		if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
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