package com.amansprojects.citrusdev.ffa;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {
	private final Main plugin;

	public PlaceholderAPIExpansion(Main main) { this.plugin = main; }

	@Override public String getAuthor() { return "ninjadev64 (IGN ninjagamer64)"; }
    
	@Override public String getIdentifier() { return "citrusffa"; }

    @Override public String getVersion() { return this.plugin.getDescription().getVersion(); }
    
    @Override public boolean persist() { return true; }
    
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("kills")) {
        	int kills = this.plugin.statConfigManager.getConfig().getInt(player.getUniqueId() + ".kills");
        	if (kills == 0) { return "0"; }
        	else {
        		return Integer.toString(kills);
        	}
        }
        
        if(params.equalsIgnoreCase("deaths")) {
        	int deaths = this.plugin.statConfigManager.getConfig().getInt(player.getUniqueId() + ".deaths");
        	if (deaths == 0) { return "0"; }
        	else {
        		return Integer.toString(deaths);
        	}
        }

        /* code that was for leaderboards but i gave up on it
        if (params.startsWith("leaderboard_")) {
            if (params.toLowerCase().startsWith("leaderboard_kills_amount_")) {
                int position = Integer.valueOf(params.replace("leaderboard_kills_amount_",""));
                if (position == 1) { return this.plugin.statManager.player1killsValue; }
                if (position == 2) { return this.plugin.statManager.player2killsValue; }
                if (position == 3) { return this.plugin.statManager.player3killsValue; }
                if (position == 4) { return this.plugin.statManager.player4killsValue; }
                if (position == 5) { return this.plugin.statManager.player5killsValue; }
            } else if (params.toLowerCase().startsWith("leaderboard_deaths_amount")) {
                int position = Integer.valueOf(params.replace("leaderboard_deaths_amount_",""));
                if (position == 1) { return this.plugin.statManager.player1deathsValue; }
                if (position == 2) { return this.plugin.statManager.player2deathsValue; }
                if (position == 3) { return this.plugin.statManager.player3deathsValue; }
                if (position == 4) { return this.plugin.statManager.player4deathsValue; }
                if (position == 5) { return this.plugin.statManager.player5deathsValue; }
            } else if (params.toLowerCase().startsWith("leaderboard_kills_player")) {
                int position = Integer.valueOf(params.replace("leaderboard_kills_player_",""));
                String UUID = null;
                if (position == 1) { UUID = this.plugin.statManager.player1killsUUID; }
                if (position == 2) { UUID = this.plugin.statManager.player2killsUUID; }
                if (position == 3) { UUID = this.plugin.statManager.player3killsUUID; }
                if (position == 4) { UUID = this.plugin.statManager.player4killsUUID; }
                if (position == 5) { UUID = this.plugin.statManager.player5killsUUID; }
                if (UUID != null) { return Bukkit.getOfflinePlayer(UUID).getName(); }
            } else if (params.toLowerCase().startsWith("leaderboard_deaths_player")) {
                int position = Integer.valueOf(params.replace("leaderboard_deaths_player_",""));
                String UUID = null;
                if (position == 1) { UUID = this.plugin.statManager.player1deathsUUID; }
                if (position == 2) { UUID = this.plugin.statManager.player2deathsUUID; }
                if (position == 3) { UUID = this.plugin.statManager.player3deathsUUID; }
                if (position == 4) { UUID = this.plugin.statManager.player4deathsUUID; }
                if (position == 5) { UUID = this.plugin.statManager.player5deathsUUID; }
                if (UUID != null) { return Bukkit.getOfflinePlayer(UUID).getName(); }
            }
        }*/
        return null;
    }
}