package me.wikmor.lynxnative.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import net.md_5.bungee.api.ChatColor;

public class ChannelCommandGroup implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// /channel
		if (args.length == 0) {
			sender.sendMessage("Welcome to the /channel command. Type /channel ? for more info.");

			return true;
		}

		String firstArg = args[0].toLowerCase();

		if ("join".equals(firstArg)) {
			//

		} else if ("leave".equals(firstArg)) {
			//

		} else if ("?".equals(firstArg)) {
			sender.sendMessage(ChatColor.RED + "/channel join <channel> - Join a channel.");
			sender.sendMessage(ChatColor.RED + "/channel leave <channel> - Leave a channel.");

		} else
			sender.sendMessage(ChatColor.RED + "Invalid sub-argument, type /channel ? for help.");

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}
