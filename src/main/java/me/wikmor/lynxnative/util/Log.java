package me.wikmor.lynxnative.util;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Log {

	private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

	public static void log(String message) {
		console.sendMessage("[Lynx] " + ChatColor.translateAlternateColorCodes('&', message));
	}

	public static void tell(Player player, String message) {
		player.sendMessage(colorize(message));
	}

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/*public static Logger getLogger() {
		return LynxNative.getPlugin(LynxNative.class).getLogger();
	}*/
}
