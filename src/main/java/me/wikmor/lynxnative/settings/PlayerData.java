package me.wikmor.lynxnative.settings;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.wikmor.lynxnative.LynxNative;

@Getter
public final class PlayerData {

	private static Map<UUID, PlayerData> playerData = new HashMap<>();

	private final String playerName;
	private final UUID uuid;
	private final YamlConfiguration config;
	private final File file;

	private double health;
	private String tabListName;

	private PlayerData(String playerName, UUID uuid) {
		this.playerName = playerName;
		this.uuid = uuid;
		this.file = this.loadFile();
		this.config = new YamlConfiguration();

		this.load();
		this.save();
	}

	private File loadFile() {
		LynxNative instance = LynxNative.getInstance();
		String path = "players/" + this.uuid + ".yml";

		File file = new File(instance.getDataFolder(), path);

		if (!file.getParentFile().exists()) // parent file = players folder
			file.getParentFile().mkdirs();

		if (!file.exists())
			try {
				file.createNewFile();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		return file;
	}

	private void load() {

		try {
			this.config.load(this.file);

		} catch (Throwable t) {
			t.printStackTrace();

			return;
		}

		this.health = this.config.getDouble("health", 20);
		this.tabListName = this.config.getString("tablist-name", this.playerName);
	}

	private void save() {

		this.config.set("health", this.health);
		this.config.set("tablist-name", this.tabListName);

		try {
			this.config.save(this.file);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void setHealth(double health) {
		this.health = health;

		this.save();
	}

	public void setTabListName(String tabListName) {
		this.tabListName = tabListName;

		this.save();
	}

	public static PlayerData from(Player player) {
		UUID uuid = player.getUniqueId();
		PlayerData data = playerData.get(uuid);

		if (data == null) {
			data = new PlayerData(player.getName(), uuid);

			playerData.put(uuid, data);
		}

		return data;
	}

	public static void remove(Player player) {
		playerData.remove(player.getUniqueId());
	}
}