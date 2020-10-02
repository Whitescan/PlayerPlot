package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.sys.Version;
import com.eclipsekingdom.playerplot.util.ProtectionUtil;
import com.eclipsekingdom.playerplot.util.X.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
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

import java.util.List;

import static com.eclipsekingdom.playerplot.sys.Language.WARN_PROTECTED;

public class ProtectionListener implements Listener {

    private String PROTECTED_WARNING = ChatColor.DARK_PURPLE + "[PlayerPlot] " + ChatColor.RED + WARN_PROTECTED;

    public ProtectionListener() {
        Plugin plugin = PlayerPlot.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBuild(BlockPlaceEvent e) {
        Player builder = e.getPlayer();
        if (!isAllowed(builder, e.getBlock().getLocation())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(PROTECTED_WARNING);
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
        if (!isAllowed(breaker, e.getBlock().getLocation())) {
            e.setCancelled(true);
            breaker.sendMessage(PROTECTED_WARNING);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractAt(PlayerInteractAtEntityEvent e) {
        if (ProtectionUtil.isInteractableAtEntity(e.getRightClicked().getType())) {
            if (!isAllowed(e.getPlayer(), e.getRightClicked().getLocation())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(PROTECTED_WARNING);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        if (ProtectionUtil.isInteractableEntity(e.getRightClicked().getType())) {
            if (!isAllowed(e.getPlayer(), e.getRightClicked().getLocation())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(PROTECTED_WARNING);
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
                    player.sendMessage(PROTECTED_WARNING);
                }
            }
        } else if (action == Action.PHYSICAL) {
            if (blockMaterial == farmMaterial) {
                if (!isAllowed(e.getPlayer(), block.getLocation())) {
                    e.setCancelled(true);
                    player.sendMessage(PROTECTED_WARNING);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDestroy(HangingBreakByEntityEvent e) {
        if (e.getRemover() instanceof Player) {
            Player player = (Player) e.getRemover();
            if (!isAllowed(player, e.getEntity().getLocation())) {
                e.setCancelled(true);
                player.sendMessage(PROTECTED_WARNING);
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
        if (!(victim instanceof Player || victim instanceof Monster)) {
            Player damager = getPlayer(e.getDamager());
            if (damager != null) {
                if (!isAllowed(damager, victim.getLocation())) {
                    e.setCancelled(true);
                    damager.sendMessage(PROTECTED_WARNING);
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
