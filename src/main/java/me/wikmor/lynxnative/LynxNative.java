package me.wikmor.lynxnative;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.wikmor.lynxnative.api.CowExplodeEvent;
import me.wikmor.lynxnative.command.ChannelCommandGroup;
import me.wikmor.lynxnative.command.FireCommand;
import me.wikmor.lynxnative.command.QuestionCommand;
import me.wikmor.lynxnative.command.SetTabCommand;
import me.wikmor.lynxnative.command.SpawnEntityCommand;
import me.wikmor.lynxnative.listener.PlayerListener;
import me.wikmor.lynxnative.settings.Settings;
import me.wikmor.lynxnative.util.Log;

public final class LynxNative extends JavaPlugin implements Listener {

	private static LynxNative instance;

	private BukkitRunnable broadcasterTask;

	@Override
	public void onEnable() {

		instance = this;

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
		getCommand("fire").setExecutor(new FireCommand());
		getCommand("settab").setExecutor(new SetTabCommand());
		getCommand("question").setExecutor(new QuestionCommand());

		restartTasks();

		// This is how to run a task right after your plugin has been enabled.
		/*new BukkitRunnable() {
			@Override
			public void run() {
				// do whatever
			}
		}.runTask(this);*/

		Settings.load();
	}

	/*public void heavyCalculation() {

		new BukkitRunnable() {
			@Override
			public void run() {
				// do the operation right here, e.g.
				Object playerData = connectToInternetAndDownloadData();

				new BukkitRunnable() {
					@Override
					public void run() {
						playerData.toString();
					}
				}.runTask(LynxNative.getInstance());
			}

			private Object connectToInternetAndDownloadData() {
				return null;
			}
		}.runTaskAsynchronously(this);
	}*/

	/*public void reload() {
		restartTasks();

		Settings.load();
	}*/

	private void restartTasks() {
		if (this.broadcasterTask != null)
			this.broadcasterTask.cancel();

		this.broadcasterTask = new Broadcaster();
		this.broadcasterTask.runTaskTimer(this, 0, 20);

		new ParticleRenderer().runTaskTimer(this, 0, 1);
	}

	@EventHandler
	public void onArrowLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();

			new BukkitRunnable() {
				@Override
				public void run() {
					if (arrow.isDead() || arrow.isOnGround()) {
						cancel();

						return;
					}

					// won't work on MC 1.8.8
					arrow.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, arrow.getLocation(), 1);
				}
			}.runTaskTimer(this, 0, 1);
		}
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

	public static LynxNative getInstance() {
		return instance;
	}
}
