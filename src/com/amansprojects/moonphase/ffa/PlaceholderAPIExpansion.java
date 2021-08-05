package com.amansprojects.moonphase.ffa;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {
	private Main plugin;
	public PlaceholderAPIExpansion(Main main) {
		this.plugin = main;
	}
	@Override
    public String getAuthor() {
        return "ninjadev64 (IGN ninjagamer64)";
    }
    
	@Override
    public String getIdentifier() {
        return "citrusffa";
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }
    
    @Override
    public boolean persist() {
        return true;
    }
    
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("kills")) {
        	Integer kills = this.plugin.deathListener.statsConfig.getInt(player.getUniqueId() + ".kills");
        	if (kills == 0) { return "0"; }
        	else {
        		return Integer.toString(kills);
        	}
        }
        
        if(params.equalsIgnoreCase("deaths")) {
        	Integer deaths = this.plugin.deathListener.statsConfig.getInt(player.getUniqueId() + ".deaths");
        	if (deaths == 0) { return "0"; }
        	else {
        		return Integer.toString(deaths);
        	}
        }
        
        return null;
    }
}