package me.ivanyf.optifinezoom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OptifineZoom implements ModInitializer {
    // control and camera to change back
    public static boolean flag = false;
    // control defaultFov
    public static boolean flag1 = true;
    public static double defaultFov = 100;
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
                if (flag1) {
                    defaultFov = client.options.fov;
                    flag1 = false;
                }
                client.options.smoothCameraEnabled = true;
                client.options.fov = 30;
                flag = true;
            } else if (flag) {
                client.options.smoothCameraEnabled = false;
                MinecraftClient.getInstance().options.fov = defaultFov;
                flag = false;
                flag1 = true;
            }
        });
    }
}
