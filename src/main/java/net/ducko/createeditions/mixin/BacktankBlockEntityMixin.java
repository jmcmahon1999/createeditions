package net.ducko.createeditions.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.BacktankBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.equipment.armor.BacktankUtil.maxAir;

@Mixin(BacktankBlockEntity.class)
public abstract class BacktankBlockEntityMixin extends KineticBlockEntity implements Nameable  {

    public BacktankBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }


    @Shadow
    private int capacityEnchantLevel;

    @Shadow private Component defaultName;

    @Inject(
            method = "getDefaultName",
            at = @At(value = "HEAD"),
            remap = false,
            cancellable = true
    )
    private static void getDefaultName(BlockState state, CallbackInfoReturnable<Component> callback) {
        if (AllBlocks.NETHERITE_BACKTANK.has(state)) {
            callback.setReturnValue(AllItems.NETHERITE_BACKTANK.get().getDescription());
        }
    }

    @Inject(
        method = "tick",
        at = @At(value = "HEAD"),
        remap = false
    )
    private void injectTick(CallbackInfo ci) {
        if (this.defaultName.equals(AllItems.NETHERITE_BACKTANK.get().getDescription())) {
            this.capacityEnchantLevel += AllConfigs.server().equipment.airInBacktank.get()
                    / AllConfigs.server().equipment.enchantedBacktankCapacity.get() * 2;
        }
    }
}