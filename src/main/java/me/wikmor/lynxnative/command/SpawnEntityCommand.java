package me.wikmor.lynxnative.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SpawnEntityCommand implements CommandExecutor, TabCompleter {

	// /spawne <mobType> <playerName> - only players can execute
	// /spawne <mobType> <world> <x> <y> <z> - players and console

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length < 2)
			return false;

		EntityType entityType;

		try {
			entityType = EntityType.valueOf(args[0].toUpperCase());

		} catch (Throwable t) {
			sender.sendMessage(ChatColor.RED + "No such entity by name '" + args[0] + "'!");

			return true;
		}

		if (!entityType.isAlive() || !entityType.isSpawnable()) {
			sender.sendMessage(ChatColor.RED + "Entity " + entityType.getName() + " is not alive or spawnable!");

			return true;
		}

		if (args.length == 5) {
			String worldName = args[1];
			World world = Bukkit.getWorld(worldName);
			int x = this.parseNumber(args, 2);
			int y = this.parseNumber(args, 3);
			int z = this.parseNumber(args, 4);

			if (world == null) {
				sender.sendMessage(ChatColor.RED + "Invalid world! You typed: " + worldName);

				return true;
			}

			if (x == -1 || y == -1 || z == -1) {
				sender.sendMessage(ChatColor.RED + "Please check your positions, XYZ coordinates must be a whole number! You typed: " + String.join(" ", args));

				return true;
			}

			Location location = new Location(world, x, y, z);
			world.spawnEntity(location, entityType);

			sender.sendMessage(ChatColor.GREEN + "Spawned " + entityType.toString().toLowerCase() + " at " + worldName + " " + x + " " + y + " " + z);
			return true;
		}

		else if (args.length == 2) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to run this command!");

				return true;
			}

			Player player = (Player) sender;
			Player targetPlayer = Bukkit.getPlayer(args[1]);

			if (targetPlayer == null) {
				player.sendMessage(ChatColor.RED + "Player '" + args[1] + "' is not online!");

				return true;
			}

			targetPlayer.getWorld().spawnEntity(targetPlayer.getLocation(), entityType);

			player.sendMessage(ChatColor.GREEN + "Spawned " + entityType.toString().toLowerCase() + " at " + targetPlayer.getName() + "'s location.");
			return true;
		}

		return false; // false = player gets to see the Usage message
	}

	private int parseNumber(String[] args, int index) {
		try {
			return Integer.parseInt(args[index]);

		} catch (Throwable t) {
			return -1;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> completions = new ArrayList<>();

		switch (args.length) {
			case 1:
				for (EntityType entityType : EntityType.values())
					if (entityType.isAlive() && entityType.isSpawnable()) {
						String name = entityType.toString().toLowerCase();

						if (name.startsWith(args[0]))
							completions.add(name);
					}

				break;

			case 2:
				if (sender instanceof Player) {
					for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						String name = onlinePlayer.getName();

						if (name.startsWith(args[1]))
							completions.add(name);
					}
				}

				for (World world : Bukkit.getWorlds()) {
					String worldName = world.getName();

					if (worldName.startsWith(args[1]))
						completions.add(worldName);
				}

				break;

			case 3:
				if (sender instanceof Player) {
					Player player = (Player) sender;

					completions.add(String.valueOf(player.getLocation().getBlockX()));
				}

				break;

			case 4:
				if (sender instanceof Player) {
					Player player = (Player) sender;

					completions.add(String.valueOf(player.getLocation().getBlockY()));
				}

				break;

			case 5:
				if (sender instanceof Player) {
					Player player = (Player) sender;

					completions.add(String.valueOf(player.getLocation().getBlockZ()));
				}

				break;
		}

		return completions;
		//return null; // null = tab complete all online player names, no tab complete = new ArrayList<>()
	}
}
