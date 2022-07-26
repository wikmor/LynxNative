package me.wikmor.lynxnative.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.wikmor.lynxnative.settings.PlayerData;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerListener implements Listener {

	@Getter
	private static final PlayerListener instance = new PlayerListener();

	/*private PlayerListener() {
	}*/

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String tabListName = PlayerData.from(player).getTabListName();

		player.setPlayerListName(tabListName);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		PlayerData.remove(event.getPlayer());
	}
}
