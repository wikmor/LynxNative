package me.wikmor.lynxnative;

import org.bukkit.Bukkit;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import me.wikmor.lynxnative.api.CowExplodeEvent;
import me.wikmor.lynxnative.command.ChannelCommandGroup;
import me.wikmor.lynxnative.command.SpawnEntityCommand;
import me.wikmor.lynxnative.listener.PlayerListener;
import me.wikmor.lynxnative.util.Log;

public final class LynxNative extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		System.out.println("LynxNative is now ready to operate!");

		Bukkit.getLogger().info("Hey from bukkit logger!");

		getLogger().info("Information message");
		getLogger().warning("Warning!");
		getLogger().severe("lol your plugin failed");

		Log.log("&cThis message should be in red");

		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(PlayerListener.getInstance()/*new PlayerListener() creating new object each time, this is fine unless we reload the plugin*/, this);

		getCommand("spawnentity").setExecutor(new SpawnEntityCommand());
		getCommand("channel").setExecutor(new ChannelCommandGroup());
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		System.out.println("Message: " + event.getMessage());
	}

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent event) {

		try {
			if (event.getHand() == EquipmentSlot.OFF_HAND)
				return;
		} catch (Throwable t) {
			// Old MC version
		}

		Entity entity = event.getRightClicked();

		System.out.println("Entity clicked!");

		if (entity instanceof Cow) {
			CowExplodeEvent cowEvent = new CowExplodeEvent(entity);
			Bukkit.getPluginManager().callEvent(cowEvent);

			if (!cowEvent.isCancelled())
				entity.getWorld().createExplosion(entity.getLocation(), cowEvent.getPower());
		}
	}

	@EventHandler
	public void onCowExplode(CowExplodeEvent event) {
		System.out.println("Exploding cow, decreasing power from " + event.getPower() + " to / 2");

		event.setPower(event.getPower() / 2);
	}
}
