package net.milkev.milkevsessentials.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.milkev.milkevsessentials.common.items.trinkets.ExtendoGrip;
import net.milkev.milkevsessentials.common.items.trinkets.FlightCharm;
import net.milkev.milkevsessentials.common.items.trinkets.ToolBelt;
import net.milkev.milkevsessentials.common.items.weapons.AmethystLauncher;
import net.milkev.milkevsessentials.common.network.ToolBeltNetworking;
import net.milkev.milkevsessentials.common.registry.MilkevRecipeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";

	public static final FlightCharm FLIGHT_CHARM = new FlightCharm(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS));
	public static final AmethystLauncher AMETHYST_LAUNCHER = new AmethystLauncher(new FabricItemSettings().maxCount(1).group(ItemGroup.COMBAT));

	public static final ToolBelt TOOL_BELT = new ToolBelt(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS));

	@Override
	public void onInitialize() {

		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		if(config.enableExtendoGrips) {
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_low"), setExtendoGrips(config.extendoGripsLowBlockReach, config.extendoGripsLowAttackReach));
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_normal"), setExtendoGrips(config.extendoGripsNormalBlockReach, config.extendoGripsNormalAttackReach));
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_high"), setExtendoGrips(config.extendoGripsHighBlockReach, config.extendoGripsHighAttackReach));
		}
		if(config.enableFlightCharm) {
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "flight_charm"), FLIGHT_CHARM);
		}
		if(config.enableAmethystLauncher) {
			TagFactory.ITEM.create(new Identifier(MOD_ID, "amethyst_shard"));
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "amethyst_launcher"), AMETHYST_LAUNCHER);
		}
		if(config.enableToolBelt) {
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "toolbelt"), TOOL_BELT);
			ToolBeltNetworking.init();
		}

		MilkevRecipeRegistry.init();

		System.out.println(MOD_ID + " Initialized");
	}

	public ExtendoGrip setExtendoGrips(int reach, int attack_reach) {
		return new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), reach, attack_reach);
	}


}
