package net.ducko.createeditions.mixin;

import com.simibubi.create.AllEnchantments;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.equipment.armor.BacktankUtil.maxAir;

@Mixin(BacktankUtil.class)
public abstract class BacktankUtilMixin {

    @Inject(
            method = "maxAir(Lnet/minecraft/world/item/ItemStack;)I",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void injectMaxAir(ItemStack backtank, CallbackInfoReturnable<Integer> callback) {
        if (backtank.getItem().getDescriptionId().equals("item.create.netherite_backtank")) {
            callback.setReturnValue(maxAir(backtank.getEnchantmentLevel(AllEnchantments.CAPACITY.get()))
                    + AllConfigs.server().equipment.airInBacktank.get() * 2);
        }
    }
}
