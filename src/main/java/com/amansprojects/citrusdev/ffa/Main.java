package com.amansprojects.citrusdev.ffa;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	ConfigManager statConfigManager = new ConfigManager(this, "stats");
	CommandListener commandListener = new CommandListener(this);
	DeathListener deathListener = new DeathListener(this);

	@Override
	public void onEnable() {
		saveDefaultConfig();
		statConfigManager.createConfig();
		deathListener.reloadStatsConfig();
		getCommand("ffa").setExecutor(commandListener);
		getServer().getPluginManager().registerEvents(deathListener, this);
		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PlaceholderAPIExpansion(this).register();
		}
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			getServer().getPluginManager().registerEvents(new VaultHandler(this), this);
		}
		new Metrics(this, 13107);
		getLogger().info("CitrusFFA Enabled");
	}
	
	@Override
	public void onDisable() {
		statConfigManager.saveConfig();
		getLogger().info("CitrusFFA Disabled");
	}
	
	CommandListener getCommandListener() { return commandListener; }
}