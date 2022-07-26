package me.wikmor.lynxnative.settings;

import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.SneakyThrows;
import me.wikmor.lynxnative.LynxNative;

public final class Settings {

	public static int PLAYER_HEALTH;
	public static String PLAYER_NAME;
	public static List<String> PLAYER_ACHIEVEMENTS;
	public static String[] MULTILINE_MESSAGE;

	public static final class Boss {
		public static double DAMAGE_MULTIPLIER;
		public static String ALIAS;
	}

	public static void load() {
		LynxNative instance = LynxNative.getInstance();
		String path = "settings.yml";
		File settingsFile = new File(instance.getDataFolder() /*plugin's folder*/, path);

		if (!settingsFile.exists())
			instance.saveResource(path, false);

		YamlConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(instance.getResource(path)));
		YamlConfiguration config = YamlConfiguration.loadConfiguration(settingsFile);

		config.addDefaults(defaults);

		PLAYER_HEALTH = config.getInt("player-health", 50);
		PLAYER_NAME = config.getString("player-name");
		PLAYER_ACHIEVEMENTS = config.getStringList("player-achievements");
		MULTILINE_MESSAGE = config.getString("multiline-message").split("\n");

		Boss.DAMAGE_MULTIPLIER = config.getDouble("boss.damage-multiplier");
		Boss.ALIAS = config.getString("boss.alias");

		printFields(Settings.class);
	}

	@SneakyThrows
	private static void printFields(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields())
			if (Modifier.isStatic(field.getModifiers()))
				System.out.println(clazz.getSimpleName() + "." + field.getName() + " --> " + field.get(null));

		for (Class<?> superClazz : clazz.getDeclaredClasses())
			printFields(superClazz);
	}
}
