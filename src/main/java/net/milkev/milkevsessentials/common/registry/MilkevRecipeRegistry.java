package net.milkev.milkevsessentials.common.registry;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import net.milkev.milkevsessentials.common.ModConfig;
import net.minecraft.util.Identifier;

import java.io.Serializable;
import java.util.ArrayList;

public class MilkevRecipeRegistry {

    public static JsonObject FLIGHT_CHARM = null;
    public static JsonObject EXTENDO_GRIP_HIGH = null;
    public static JsonObject EXTENDO_GRIP_NORMAL = null;
    public static JsonObject EXTENDO_GRIP_LOW = null;

    //DONT FORGET TO ADD RECIPES TO THE MIXIN AS WELL
    //MilkevRecipeRegistryMixin.java

    public static void init() {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (config.enableFlightCharm) {
            FLIGHT_CHARM = createRecipeJson(
                    "minecraft:crafting_shaped",
                    Lists.newArrayList(
                            "",
                            "#B#",
                            "SNS",
                            "EBE"
                    ),
                    Lists.newArrayList(
                            "",
                            "#", "item", "minecraft:dragon_breath",
                            "B", "item", "minecraft:gray_terracotta",
                            "S", "item", "minecraft:shulker_shell",
                            "N", "item", "minecraft:nether_star",
                            "E", "item", "minecraft:elytra"
                    ),
                    "milkevsessentials:flight_charm"

            );
        }
        if (config.enableExtendoGrips) {
            EXTENDO_GRIP_HIGH = createRecipeJson(
                    "minecraft:crafting_shaped",
                    Lists.newArrayList("",
                            "#N#",
                            "DLD",
                            "/n/"
                    ),
                    Lists.newArrayList("",
                            "#", "item", "minecraft:ender_pearl",
                            "N", "item", "minecraft:nether_star",
                            "/", "item", "minecraft:end_rod",
                            "L", "item", "milkevsessentials:extendo_grip_normal",
                            "n", "item", "minecraft:netherite_ingot",
                            "D", "item", "minecraft:diamond"

                    ),
                    "milkevsessentials:extendo_grip_high"
            );
            EXTENDO_GRIP_NORMAL = createRecipeJson(
                    "minecraft:crafting_shaped",
                    Lists.newArrayList( "",
                            "# #",
                            "#N#",
                            " / "
                    ),
                    Lists.newArrayList("",
                            "#", "item", "minecraft:netherite_scrap",
                            "N", "item", "milkevsessentials:extendo_grip_low",
                            "/", "item", "minecraft:blaze_rod"
                    ),
                    "milkevsessentials:extendo_grip_normal"
            );
            EXTENDO_GRIP_LOW = createRecipeJson(
                    "minecraft:crafting_shaped",
                    Lists.newArrayList("",
                            "# #",
                            "IiI",
                            " / "
                    ),
                    Lists.newArrayList("",
                            "#", "item", "minecraft:piston",
                            "I", "item", "minecraft:iron_bars",
                            "i", "item", "minecraft:iron_ingot",
                            "/", "item", "minecraft:stick"
                    ),
                    "milkevsessentials:extendo_grip_low"

            );
        }
    }

    public static JsonObject createRecipeJson(String type, ArrayList<String> pattern, ArrayList<String> key, String result, int amount) {
        return createRecipeJsonActual(type, pattern, key, result, amount);
    }
    public static JsonObject createRecipeJson(String type, ArrayList<String> pattern, ArrayList<String> key, String result) {
        return createRecipeJsonActual(type, pattern, key, result, 1);
    }

    public static JsonObject createRecipeJsonActual(String type, ArrayList<String> pattern, ArrayList<String> key, String result, int amount) {
        JsonObject json = new JsonObject();

        //craft type
        json.addProperty("type", type);

        //shaped craft way. will expand for other types when they are needed
        if(type.equals("minecraft:crafting_shaped")) {
            JsonArray patternArray = new JsonArray();
            patternArray.add(pattern.get(1));
            patternArray.add(pattern.get(2));
            patternArray.add(pattern.get(3));
            json.add("pattern", patternArray);
        }

        //the items and keys used
        JsonObject keyObject = new JsonObject();
        for(int i = 1; i < key.size()-1; i+=3) {
            JsonObject keyObjectID = new JsonObject();
            keyObjectID.addProperty(key.get(i+1), key.get(i+2));
            keyObject.add(key.get(i), keyObjectID);
        }
        json.add("key", keyObject);

        //output
        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("item", result);
        resultObject.addProperty("count", amount);
        json.add("result", resultObject);

        //System.out.println(json);
        return json;
    }

}
