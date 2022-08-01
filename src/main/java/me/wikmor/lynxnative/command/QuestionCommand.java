package me.wikmor.lynxnative.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ExactMatchConversationCanceller;
import org.bukkit.entity.Player;

import me.wikmor.lynxnative.LynxNative;
import me.wikmor.lynxnative.prompt.NamePrompt;
import me.wikmor.lynxnative.prompt.NamePrompt.BoardingQuestion;
import me.wikmor.lynxnative.util.Log;

/**
 * An example of creating a command that displays server-to-player conversation.
 */
public class QuestionCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("lynxnative.command.question")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");

			return true;
		}

		Player player = (Player) sender;

		Conversation conversation = new ConversationFactory(LynxNative.getInstance())
				.addConversationAbandonedListener(event -> {
					Conversable conversable = event.getContext().getForWhom();

					if (event.gracefulExit()) {
						String name = (String) event.getContext().getSessionData(BoardingQuestion.NAME);

						conversable.sendRawMessage(ChatColor.GOLD + "Conversation finished! Your name is: " + name);
					} else
						conversable.sendRawMessage(ChatColor.RED + "Conversation has been cancelled!");
				})
				.withConversationCanceller(new ExactMatchConversationCanceller("quit"))
				.withFirstPrompt(new NamePrompt())
				.withTimeout(30)
				.withLocalEcho(false)
				.withPrefix(context -> Log.colorize("&8[&6Boarding&8] &7"))
				.buildConversation(player);

		player.beginConversation(conversation);

		return true;
	}
}
