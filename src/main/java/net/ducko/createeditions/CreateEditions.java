package net.ducko.createeditions;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;

import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateEditions implements ModInitializer {
    public static final String ID = "createeditions";
    public static final String NAME = "Create Editions";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
        LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib from the client!",
                () -> () -> "{} is accessing Porting Lib from the server!"
        ), NAME);

        GogglesItem.addIsWearingPredicate(player -> {
            if (TrinketsApi.getTrinketComponent(player).isPresent()) {
                return TrinketsApi.getTrinketComponent(player).get().isEquipped(itemStack ->
                        itemStack.getItem() instanceof BacktankItem);
            }
            return false;
        });

        BacktankUtil.addBacktankSupplier(entity -> {
            List<ItemStack> stacks = new ArrayList<>();
            if (TrinketsApi.getTrinketComponent(entity).isPresent()) {
                TrinketComponent component = TrinketsApi.getTrinketComponent(entity).get();
                for (Tuple<SlotReference, ItemStack> tuple : component.getAllEquipped()) {
                    ItemStack itemStack = tuple.getB();
                    if (!itemStack.isEmpty() && AllTags.AllItemTags.PRESSURIZED_AIR_SOURCES.matches(itemStack))
                        stacks.add(itemStack);
                }
            }
            return stacks;
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }
}