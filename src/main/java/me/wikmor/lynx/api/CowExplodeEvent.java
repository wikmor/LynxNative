package me.wikmor.lynx.api;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CowExplodeEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private final Entity cow;
	private float power = 5;
	private boolean cancelled;

	public CowExplodeEvent(Entity cow) {
		this.cow = cow;
	}

	public Entity getCow() {
		return cow;
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
