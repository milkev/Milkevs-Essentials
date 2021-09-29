package net.milkev.milkevsessentials.common.registry;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.ModConfig;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MilkevRecipeRegistry {

    public static JsonObject FLIGHT_CHARM = null;
    public static JsonObject EXTENDO_GRIP_HIGH = null;
    public static JsonObject EXTENDO_GRIP_NORMAL = null;
    public static JsonObject EXTENDO_GRIP_LOW = null;
    public static JsonObject TOOLBELT = null;
    private static final Gson GSON = new Gson();

    //DONT FORGET TO ADD RECIPES TO THE MIXIN AS WELL
    //MilkevRecipeRegistryMixin.java

    public static void init() {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        if (config.enableFlightCharm) {
            FLIGHT_CHARM = getRecipe("flight_charm");
        }
        if (config.enableExtendoGrips) {
            EXTENDO_GRIP_LOW = getRecipe("extendo_grip_low");
            EXTENDO_GRIP_NORMAL = getRecipe("extendo_grip_normal");
            EXTENDO_GRIP_HIGH = getRecipe("extendo_grip_high");
        }
        if (config.enableToolBelt) {
            TOOLBELT = getRecipe("toolbelt");
        }
    }

    public static @Nullable
    JsonObject getRecipe(final String name) {
        final String path = "/data/milkevsessentials/dynamic_recipes/" + name + ".json";
        final URL file = MilkevsEssentials.class.getResource(path);

        try (final var reader = Files.newBufferedReader(Paths.get(file.toURI()))) {
            return GSON.fromJson(reader, JsonObject.class);
        } catch (final IOException | URISyntaxException e) {
            System.out.println("recipe reader borked reading: " + path);
            return null;
        }
    }
}
