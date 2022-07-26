package me.wikmor.lynxnative.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.wikmor.lynxnative.settings.PlayerData;
import net.md_5.bungee.api.ChatColor;

public final class SetTabCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");

			return true;
		}

		if (!sender.hasPermission("lynxnative.command.settab")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");

			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /settab <alias>");

			return true;
		}

		String tabName = args[0];
		Player player = (Player) sender;

		player.setPlayerListName(tabName);
		PlayerData.from(player).setTabListName(tabName);

		sender.sendMessage(ChatColor.GOLD + "Your name has been updated to '" + tabName + "'.");

		return true;
	}
}
