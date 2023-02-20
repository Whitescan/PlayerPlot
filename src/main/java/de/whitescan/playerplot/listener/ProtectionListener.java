package de.whitescan.playerplot.listener;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.config.Version;
import de.whitescan.playerplot.logic.Plot;
import de.whitescan.playerplot.plot.PlotCache;
import de.whitescan.playerplot.util.ProtectionUtil;
import de.whitescan.playerplot.util.xseries.XMaterial;
import de.whitescan.playerplot.util.xseries.XSound;

public class ProtectionListener implements Listener {

	private String PROTECTED_WARNING = ChatColor.RED + Language.WARN_PROTECTED.toString();
	private String PVP_WARNING = ChatColor.RED + Language.WARN_NO_PVP_ZONE.toString();

	public ProtectionListener() {
		Plugin plugin = PlayerPlot.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBuild(BlockPlaceEvent e) {
		Player builder = e.getPlayer();
		Block block = e.getBlock();
		if (!isAllowed(builder, block.getLocation())) {
			e.setCancelled(true);
			sendWarning(e.getPlayer(), PROTECTED_WARNING, block);
		}
	}

	private static void sendWarning(Player player, String message) {
		sendErrorEffect(player, player.getLocation());
		if (PluginConfig.isWarnMessage())
			player.sendMessage(message);
	}

	private static void sendWarning(Player player, String message, Entity entity) {
		Location location = entity.getLocation().add(0, entity.getHeight() / 2.0, 0);
		sendErrorEffect(player, location);
		if (PluginConfig.isWarnMessage())
			player.sendMessage(message);
	}

	private static void sendWarning(Player player, String message, Block block) {
		Location location = block.getLocation();
		location.add(0.5, 0, 0.5);
		Material material = block.getType();
		if (ProtectionUtil.isSmallBlock(material)) {
			location.add(0, 0.3, 0);
		} else if (ProtectionUtil.isMediumBlock(material)) {
			location.add(0, 0.8, 0);
		} else {
			location.add(0, 1.3, 0);
		}
		sendErrorEffect(player, location);
		if (PluginConfig.isWarnMessage())
			player.sendMessage(message);
	}

	private static void sendErrorEffect(Player player, Location location) {
		if (PluginConfig.isWarnSound()) {
			player.playSound(location, XSound.ENTITY_BLAZE_HURT.parseSound(), 1f, 0.7f);
		}
		if (PluginConfig.isWarnParticle()) {
			location.getWorld().spawnParticle(Particle.SMOKE_NORMAL, location, 2, 0, 0, 0, 0.05);
		}
	}

	@Deprecated
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPiston(BlockPistonExtendEvent e) {
		Block piston = e.getBlock();
		Vector direction = Version.isLegacy() ? getDirection(piston) : e.getDirection().getDirection();
		Plot pistonPlot = PlotCache.getPlot(piston.getLocation());
		for (Block block : e.getBlocks()) {
			Plot blockPlot = PlotCache.getPlot(block.getLocation().add(direction.getX(), 0, direction.getZ()));
			if (blockPlot != null && blockPlot != pistonPlot) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@Deprecated
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPistonRetract(BlockPistonRetractEvent e) {
		Block piston = e.getBlock();
		Vector direction = Version.isLegacy() ? getDirection(piston) : e.getDirection().getDirection();
		direction.multiply(-1);
		Plot pistonPlot = PlotCache.getPlot(piston.getLocation());
		for (Block block : e.getBlocks()) {
			Plot blockPlot = PlotCache.getPlot(block.getLocation().add(direction.getX(), 0, direction.getZ()));
			if (blockPlot != null && blockPlot != pistonPlot) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@Deprecated
	private Vector getDirection(Block piston) {
		byte data = piston.getData();
		if (data == (byte) 2) {
			return new Vector(0, 0, -1);
		} else if (data == (byte) 3) {
			return new Vector(0, 0, 1);
		} else if (data == (byte) 4) {
			return new Vector(-1, 0, 0);
		} else if (data == (byte) 5) {
			return new Vector(1, 0, 0);
		} else {
			return new Vector();
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFlow(BlockFromToEvent e) {
		Plot plotTo = PlotCache.getPlot(e.getToBlock().getLocation());
		if (plotTo != null) {
			Plot plotFrom = PlotCache.getPlot(e.getBlock().getLocation());
			if (plotTo != plotFrom) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityChangeBlock(EntityChangeBlockEvent e) {
		if (e.getEntity().getType() == EntityType.ENDERMAN) {
			if (PlotCache.hasPlot(e.getBlock().getLocation())) {
				e.setCancelled(true);
			}
		}

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreak(BlockBreakEvent e) {
		Player breaker = e.getPlayer();
		Block block = e.getBlock();
		if (!isAllowed(breaker, block.getLocation())) {
			e.setCancelled(true);
			sendWarning(breaker, PROTECTED_WARNING, block);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteractAt(PlayerInteractAtEntityEvent e) {
		if (ProtectionUtil.isInteractableAtEntity(e.getRightClicked().getType())) {
			Entity entity = e.getRightClicked();
			if (!isAllowed(e.getPlayer(), entity.getLocation())) {
				e.setCancelled(true);
				sendWarning(e.getPlayer(), PROTECTED_WARNING, entity);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		if (ProtectionUtil.isInteractableEntity(e.getRightClicked().getType())) {
			Entity entity = e.getRightClicked();
			if (!isAllowed(e.getPlayer(), entity.getLocation())) {
				e.setCancelled(true);
				sendWarning(e.getPlayer(), PROTECTED_WARNING, entity);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		Action action = e.getAction();
		Block block = e.getClickedBlock();
		Material blockMaterial = e.hasBlock() ? block.getType() : Material.AIR;
		Player player = e.getPlayer();
		if (action == Action.RIGHT_CLICK_BLOCK) {
			Material handMaterial = e.hasItem() ? e.getItem().getType() : Material.AIR;
			if (ProtectionUtil.isProtectedInteraction(handMaterial, blockMaterial)) {
				if (!isAllowed(player, block.getLocation())) {
					e.setCancelled(true);
					sendWarning(player, PROTECTED_WARNING, block);
				}
			}
		} else if (action == Action.PHYSICAL) {
			if (blockMaterial == farmMaterial) {
				if (!isAllowed(e.getPlayer(), block.getLocation())) {
					e.setCancelled(true);
					sendWarning(player, PROTECTED_WARNING, block);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDestroy(HangingBreakByEntityEvent e) {
		if (e.getRemover() instanceof Player) {
			Player player = (Player) e.getRemover();
			Entity entity = e.getEntity();
			if (!isAllowed(player, entity.getLocation())) {
				e.setCancelled(true);
				sendWarning(player, PROTECTED_WARNING, entity);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockExplode(BlockExplodeEvent e) {
		List<Block> blockList = e.blockList();
		for (int i = blockList.size() - 1; i >= 0; i--) {
			if (PlotCache.hasPlot(blockList.get(i).getLocation())) {
				blockList.remove(i);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityExplode(EntityExplodeEvent e) {
		List<Block> blockList = e.blockList();
		for (int i = blockList.size() - 1; i >= 0; i--) {
			if (PlotCache.hasPlot(blockList.get(i).getLocation())) {
				blockList.remove(i);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFire(BlockIgniteEvent e) {
		if (e.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
			if (PlotCache.hasPlot(e.getIgnitingBlock().getLocation())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFire(BlockBurnEvent e) {
		if (PlotCache.hasPlot(e.getBlock().getLocation())) {
			e.getBlock().setType(Material.AIR);
			e.setCancelled(true);
		}
	}

	private Material farmMaterial = XMaterial.FARMLAND.parseMaterial();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityInteract(EntityInteractEvent e) {
		if (!(e.getEntity() instanceof Villager)) {
			Block block = e.getBlock();
			if (block.getType() == farmMaterial) {
				if (PlotCache.hasPlot(block.getLocation())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHurt(EntityDamageByEntityEvent e) {
		Entity victim = e.getEntity();
		Player damager = getPlayer(e.getDamager());
		if (damager != null) {
			if (victim instanceof Player) {
				if (!PluginConfig.isPlotPvp()) {
					if (PlotCache.hasPlot(victim.getLocation())) {
						e.setCancelled(true);
						sendWarning(damager, PVP_WARNING, victim);
					} else if (PlotCache.hasPlot(damager.getLocation())) {
						e.setCancelled(true);
						sendWarning(damager, PVP_WARNING);
					}
				}
			} else if (!(ProtectionUtil.isMonster(victim) || ProtectionUtil.isFighting(victim))) {
				if (!isAllowed(damager, victim.getLocation())) {
					e.setCancelled(true);
					sendWarning(damager, PROTECTED_WARNING, victim);
				}
			}
		}
	}

	private boolean isAllowed(Player player, Location location) {
		Plot plot = PlotCache.getPlot(location);
		return plot != null ? plot.isAllowed(player) : true;
	}

	private Player getPlayer(Entity entity) {
		if (entity instanceof Player) {
			return (Player) entity;
		} else if (entity instanceof Projectile) {
			Projectile projectile = (Projectile) entity;
			if (projectile.getShooter() instanceof Player) {
				return (Player) projectile.getShooter();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
