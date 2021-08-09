package com.amansprojects.citrusdev.ffa;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	private Main plugin;
	private String name;
	private File configFile;
	private YamlConfiguration config;
	public ConfigManager(Main main, String name) {
		this.plugin = main;
		this.name = name;
	}
	public FileConfiguration getConfig() {
		return this.config;
	}
	public void createConfig() {
        this.configFile = new File(this.plugin.getDataFolder(), name+".yml");
        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            this.plugin.saveResource(name+".yml", false);
         }
        this.config = new YamlConfiguration();
        try {
            this.config.load(this.configFile);
        } catch (IOException | InvalidConfigurationException e) {}
    }
	public void saveConfig() {
		try {
			this.config.save(configFile);
		} catch (IOException e) {}
	}
}
