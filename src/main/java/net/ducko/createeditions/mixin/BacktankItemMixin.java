package net.ducko.createeditions.mixin;

import com.simibubi.create.content.equipment.armor.BacktankItem;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

@Mixin(BacktankItem.class)
public abstract class BacktankItemMixin {

    @Inject(
            method = "getWornBy",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void injectGetWornBy(Entity entity, CallbackInfoReturnable<BacktankItem> callback) {
        if (entity instanceof LivingEntity livingEntityInjected) {
            Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findFirstCurio(livingEntityInjected,
                    item -> item.getItem() instanceof BacktankItem);
            slotResult.ifPresent(result -> callback.setReturnValue((BacktankItem) result.stack().getItem()));
        }
    }

}
