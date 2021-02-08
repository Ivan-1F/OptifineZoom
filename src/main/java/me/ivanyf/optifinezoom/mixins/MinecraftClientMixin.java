package me.ivanyf.optifinezoom.mixins;

import me.ivanyf.optifinezoom.OptifineZoom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Final public GameOptions options;
    private static boolean flag = true;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (OptifineZoom.enable && flag) {
            options.fov = 90;
            flag = false;
        } else if (!OptifineZoom.enable) {
            flag = true;
        }
    }
}
