package me.ivanyf.optifinezoom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.StickyKeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OptifineZoom implements ModInitializer {
    public static boolean enable = false;
    @Override
    public void onInitialize() {
        KeyBinding key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.optifinezoom.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.optifinezoom.optifinezoom"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            
            if (key.isPressed()) {
                MinecraftClient.getInstance().options.fov = 30;
            } else {
                MinecraftClient.getInstance().options.fov = 100;
            }
        });
    }
}
