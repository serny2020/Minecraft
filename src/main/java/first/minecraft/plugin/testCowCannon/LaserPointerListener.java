package first.minecraft.plugin.testCowCannon;

import org.bukkit.ChatColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

public class LaserPointerListener implements Listener {

    @EventHandler
    public void onClick(final PlayerInteractEvent event) {
        // MC 1.9+
        if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        Player player = event.getPlayer();
        ItemStack hand = player.getItemInHand();
        int distance = 100;


//        if (hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Laser Pointer")) {
//            RayTraceResult result = player.rayTraceBlocks(distance);
//                if (result != null && result.getHitBlock() != null && result.getHitBlock().isSolid()) {
//                player.getWorld().createExplosion(result.getHitBlock().getLocation(), 5F, true);
//            } else
//                player.sendMessage(ChatColor.LIGHT_PURPLE + "[Laser]" + ChatColor.WHITE + " Target is too far or not a solid block!");
//        }

        if (hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Laser Pointer")) {
            RayTraceResult entityResult = player.rayTraceEntities(distance);
            RayTraceResult blockResult = player.rayTraceBlocks(distance);

            double entityDist = entityResult != null ? entityResult.getHitPosition().distance(player.getEyeLocation().toVector()) : Double.MAX_VALUE;
            double blockDist = blockResult != null ? blockResult.getHitPosition().distance(player.getEyeLocation().toVector()) : Double.MAX_VALUE;

            if (entityDist < blockDist) {
                // Entity is hit first
                Entity target = entityResult.getHitEntity();
                if (target instanceof Damageable) {
                    ((Damageable) target).damage(30.0, player);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "[Laser]" + ChatColor.WHITE + " Target hit!");
                }
            } else if (blockResult != null && blockResult.getHitBlock() != null && blockResult.getHitBlock().isSolid()) {
                // Block is hit first
                player.getWorld().createExplosion(blockResult.getHitBlock().getLocation(), 5F, true);
            } else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "[Laser]" + ChatColor.WHITE + " No valid target in range!");
            }
        }
    }
}
