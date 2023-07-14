package tk.jandev.superscraper.scraper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Items;

import java.util.Objects;

public class Checker {
    static MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean inSwordGame() {
        if (!Objects.equals(mc.getNetworkHandler().getServerInfo().address, "eu.mcpvp.club")) {
            System.out.println("current server adress actually was "+mc.getNetworkHandler().getServerInfo().address);
            return false;
        }
        if (mc.player.getInventory().getArmorStack(0).getItem() != Items.DIAMOND_BOOTS) return false;
        if (mc.player.getInventory().getArmorStack(1).getItem() != Items.DIAMOND_LEGGINGS) return false;
        if (mc.player.getInventory().getArmorStack(2).getItem() != Items.DIAMOND_CHESTPLATE) return false;
        if (mc.player.getInventory().getArmorStack(3).getItem() != Items.DIAMOND_HELMET) return false;
        if (mc.player.world.getClosestPlayer(mc.player, 500) == null) return false;

        return true;
    }
}
