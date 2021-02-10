package me.ivanyf.optifinezoom;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.literal;
import static io.github.cottonmc.clientcommands.ArgumentBuilders.argument;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;

public class OptifineZoom implements ModInitializer, ClientCommandPlugin {
    public static final String MOD_ID = "optifinezoom";
    // control and camera to change back
    public static boolean flag = false;
    // control defaultFov
    public static boolean flag1 = true;
    public static double defaultFov = 100;
    public static double targetFov = 30;
    @Override
    public void onInitialize() {
        KeyBinding key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "optifinezoom.key.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "optifinezoom.category.optifinezoom"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (key.isPressed()) {
                if (flag1) {
                    defaultFov = client.options.fov;
                    flag1 = false;
                }
                client.options.smoothCameraEnabled = true;
                client.options.fov = targetFov;
                flag = true;
            } else if (flag) {
                client.options.smoothCameraEnabled = false;
                MinecraftClient.getInstance().options.fov = defaultFov;
                flag = false;
                flag1 = true;
            }
        });
    }

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        dispatcher.register(literal(MOD_ID)
                .then(literal("changefov")
                        .then(argument("fov", integer())
                                .executes(context -> {
                                    int fov = getInteger(context, "fov");
                                    targetFov = fov;
                                    context.getSource().sendFeedback(new TranslatableText("optifinezoom.message.change_fov_success", fov));
                                    return 1;
                                }))));
    }
}
