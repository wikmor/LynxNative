package me.wikmor.lynxnative;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleRenderer extends BukkitRunnable {

	@Override
	public void run() {
		long now = System.currentTimeMillis();

		for (Player player : Bukkit.getOnlinePlayers()) {
			Location center = player.getEyeLocation();
			double radius = 2;
			double step = 8;

			for (double degree = 0; degree <= 180; degree += step) {
				double radians = Math.toRadians(degree);

				double x = Math.cos(radians) * radius;
				double z = Math.sin(radians) * radius;

				this.drawCircle(center, step, x, z);
			}
		}

		System.out.println("Showing animations took " + (System.currentTimeMillis() - now) + " ms");
	}

	private void drawCircle(Location center, double step, double height, double radius) {
		World world = center.getWorld();

		for (double degree = 0; degree <= 360; degree += step) {
			double radians = Math.toRadians(degree);

			double x = Math.cos(radians) * radius;
			double z = Math.sin(radians) * radius;

			world.spawnParticle(Particle.REDSTONE, center.clone().add(x, height, z), 1, new Particle.DustOptions(Color.RED, 0.15F));
		}
	}
}
