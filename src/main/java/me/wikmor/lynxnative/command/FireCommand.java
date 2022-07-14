package me.wikmor.lynxnative.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class FireCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("lynx.command.fire")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");

			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /fire <target>");

			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			sender.sendMessage(ChatColor.RED + "That player is not online on this server!");

			return true;
		}

		if (target.hasPermission("lynx.bypass.fire")) {
			sender.sendMessage(ChatColor.RED + "You can't set that player on fire!");

			return true;
		}

		target.setFireTicks(20 * 2);

		return true;
	}
}
