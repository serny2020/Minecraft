package first.minecraft.plugin.testCowCannon;

import org.bukkit.entity.Cow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent e) {
        System.out.println("Right Click: " + e.getRightClicked().getClass().getName());
        if (e.getRightClicked() instanceof Cow) {
            Cow cow = (Cow) e.getRightClicked();
            cow.getWorld().createExplosion(cow.getLocation(), 2.5f);
        }
    }
}
