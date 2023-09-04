package net.ducko.createeditions;

import com.simibubi.create.Create;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }
}