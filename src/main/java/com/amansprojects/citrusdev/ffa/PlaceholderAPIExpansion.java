package com.amansprojects.citrusdev.ffa;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {
	private final Main plugin;

	public PlaceholderAPIExpansion(Main main) { this.plugin = main; }

	@Override public String getAuthor() { return "ninjadev64 (IGN ninjagamer64)"; }
    
	@Override public String getIdentifier() { return "citrusffa"; }

    @Override public String getVersion() { return this.plugin.getDescription().getVersion(); }
    
    @Override public boolean persist() { return true; }
    
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("kills")) {
        	int kills = this.plugin.statConfigManager.getConfig().getInt(player.getUniqueId() + ".kills");
        	if (kills == 0) { return "0"; }
        	else { return Integer.toString(kills); }
        }
        
        if (params.equalsIgnoreCase("deaths")) {
        	int deaths = this.plugin.statConfigManager.getConfig().getInt(player.getUniqueId() + ".deaths");
        	if (deaths == 0) { return "0"; }
        	else { return Integer.toString(deaths); }
        }

        if (params.startsWith("leaderboard_")) {
            try {
                FileConfiguration statsConfig = this.plugin.statConfigManager.getConfig();
                ArrayList<StatisticCase> values = new ArrayList<StatisticCase>();
                for (String path : statsConfig.getKeys(false)) {
                    values.add(new StatisticCase(path, statsConfig.getInt(path + ".kills"), statsConfig.getInt(path + ".deaths")));
                }
                values.sort(Collections.reverseOrder());
                if (params.toLowerCase().startsWith("leaderboard_amount_")) {
                    int position = Integer.parseInt(params.replace("leaderboard_amount_", ""));
                    return String.valueOf(values.get(position - 1).KILLS);
                } else if (params.toLowerCase().startsWith("leaderboard_player")) {
                    int position = Integer.parseInt(params.replace("leaderboard_player_", ""));
                    return Bukkit.getOfflinePlayer(UUID.fromString(values.get(position - 1).UUID)).getName();
                }
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                return null;
            }
        }

        return null;
    }
}
