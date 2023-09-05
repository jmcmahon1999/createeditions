package net.ducko.createeditions.mixin;

import com.simibubi.create.content.equipment.armor.BacktankItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
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
            Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(livingEntityInjected);
            trinketComponent.ifPresent(component -> {
                    List<Tuple<SlotReference, ItemStack>> result = component.getEquipped(itemStack ->
                            itemStack.getItem() instanceof BacktankItem
                    );
                    if (!result.isEmpty()) {
                        callback.setReturnValue((BacktankItem) result.get(0).getB().getItem());
                    }
            });
        }
    }

}
