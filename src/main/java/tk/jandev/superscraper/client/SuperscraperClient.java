package tk.jandev.superscraper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import tk.jandev.superscraper.scraper.Checker;
import tk.jandev.superscraper.scraper.DataPointTracker;
import tk.jandev.superscraper.scraper.TickDataPoint;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class SuperscraperClient implements ClientModInitializer {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private boolean lastTickInGame = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(this::tick);
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            try {
                shutdown(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void shutdown(MinecraftClient client) throws IOException {
        String path = (FabricLoader.getInstance().getGameDir() + File.separator + "data.txt");
        DataPointTracker.safe(path);
    }

    private void tick(MinecraftClient client) {
        if (mc.world == null) return; // nul check cuz broke
        boolean inSwordGame = Checker.inSwordGame();
        if (!inSwordGame && lastTickInGame) {
            mc.player.sendMessage(Text.of("[RECORDER] §rDetected game ending.."));
            DataPointTracker.add(null); // null acts as a game separator
            lastTickInGame = false;
            return;
        }
        lastTickInGame = inSwordGame;

        PlayerEntity enemy = mc.world.getClosestPlayer(mc.player, 300);
        if (enemy == null) return;
        TickDataPoint dataPoint = new TickDataPoint();
        dataPoint.playerX = mc.player.getX();
        dataPoint.playerY = mc.player.getY();
        dataPoint.playerZ = mc.player.getZ();
        dataPoint.playerHealth = mc.player.getHealth();
        dataPoint.attackTime = 1.0F - mc.player.getItemCooldownManager().getCooldownProgress(Items.DIAMOND_SWORD, 0.0F);

        dataPoint.yaw = mc.player.getYaw();
        dataPoint.pitch = mc.player.getPitch();

        dataPoint.enemyX = enemy.getX();
        dataPoint.enemyY = enemy.getY();
        dataPoint.enemyZ = enemy.getZ();
        dataPoint.enemyHealth = enemy.getHealth();

        dataPoint.w = mc.options.forwardKey.isPressed();
        dataPoint.a = mc.options.leftKey.isPressed();
        dataPoint.s = mc.options.backKey.isPressed();
        dataPoint.d = mc.options.rightKey.isPressed();
        dataPoint.sprint = mc.player.isSprinting();
        dataPoint.hit = mc.options.attackKey.isPressed();
        dataPoint.jump = mc.options.jumpKey.isPressed();

        DataPointTracker.add(dataPoint);
    }
}