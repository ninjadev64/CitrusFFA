package com.amansprojects.citrusdev.ffa;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;

public class VaultHandler implements Listener {
    private final Main plugin;
    private static Economy economy = null;

    public VaultHandler(Main main) { this.plugin = main; }

    @EventHandler
    public void onServiceRegister(ServiceRegisterEvent event) {
        if (event.getProvider().getService() == Economy.class) {
            economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
            this.plugin.getLogger().info("Successfully hooked into Vault for economy!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFfaDeath(FFADeathEvent event) {
        if (economy != null) {
            if (event.getVictim() != event.getKiller()) {
                economy.depositPlayer(event.getKiller(), this.plugin.getConfig().getInt("vault money for kill"));
            }
        }
    }
}
