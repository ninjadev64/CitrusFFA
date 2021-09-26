package com.amansprojects.citrusdev.ffa;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;

public class FFADeathEvent extends Event {
    private final Player victim, killer;
    private final EntityDeathEvent spigotDeathEvent;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public FFADeathEvent(Player victim, Player killer, EntityDeathEvent spigotDeathEvent) {
        this.victim = victim;
        this.killer = killer;
        this.spigotDeathEvent = spigotDeathEvent;
    }

    @Override
    public HandlerList getHandlers() { return HANDLERS_LIST; }

    public static HandlerList getHandlerList() { return HANDLERS_LIST; }

    public Player getKiller() { return killer; }
    public Player getVictim() { return victim; }

    public EntityDeathEvent getSpigotDeathEvent() { return spigotDeathEvent; }
}
