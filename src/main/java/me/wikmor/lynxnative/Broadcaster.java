package me.wikmor.lynxnative;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.wikmor.lynxnative.util.Log;

public class Broadcaster extends BukkitRunnable {

	private final List<String> messages = new ArrayList<>();

	private int index = 0;

	public Broadcaster() {
		this.messages.add("1) Please visit our website and purchase VIP to &6continue playing&7...");
		this.messages.add("2) wikmor is a really good coder bawls");
		this.messages.add("3) Please obey the rules otherwise you shall be &cpunished&7!");
	}

	@Override
	public void run() {

		if (this.index == this.messages.size())
			this.index = 0;

		for (Player player : Bukkit.getOnlinePlayers())
			Log.tell(player, "&8[&d!&8] &7" + this.messages.get(index));

		this.index++;
	}
}
