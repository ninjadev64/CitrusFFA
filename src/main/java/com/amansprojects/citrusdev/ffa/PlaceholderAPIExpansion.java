package com.amansprojects.citrusdev.ffa;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {
	private final Main plugin;

	public PlaceholderAPIExpansion(Main main) { this.plugin = main; }

	@Override @NotNull public String getAuthor() { return "ninjadev64 (IGN ninjagamer64)"; }
    
	@Override @NotNull public String getIdentifier() { return "citrusffa"; }

    @Override @NotNull public String getVersion() { return this.plugin.getDescription().getVersion(); }
    
    @Override public boolean persist() { return true; }
    
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        UUID uuid = player.getUniqueId();
        FileConfiguration statsConfig = this.plugin.statConfigManager.getConfig();
        StatisticCase stats = new StatisticCase(
                uuid.toString(),
                statsConfig.getInt(uuid + ".kills"),
                statsConfig.getInt(uuid + ".deaths")
        );

        if (params.equalsIgnoreCase("kills")) { return Integer.toString(stats.KILLS); }
        else if (params.equalsIgnoreCase("deaths")) { return Integer.toString(stats.DEATHS); }

        else if (params.startsWith("leaderboard_")) {
            try {
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

        else if (params.equalsIgnoreCase("kdr")) {
            try { return String.valueOf(Math.round(((float)stats.KILLS / stats.DEATHS) * 100.0) / 100.0); }
            catch (ArithmeticException e) { return "Infinity"; }
        }

        return null;
    }
}
