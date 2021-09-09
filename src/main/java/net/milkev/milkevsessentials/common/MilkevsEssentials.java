package net.milkev.milkevsessentials.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.milkev.milkevsessentials.common.trinkets.ExtendoGrip;
import net.milkev.milkevsessentials.common.trinkets.FlightCharm;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";
/*
	public static final ExtendoGrip EXTENDO_GRIP_LOW = new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), 0);
	public static final ExtendoGrip EXTENDO_GRIP_NORMAL = new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), 1);
	public static final ExtendoGrip EXTENDO_GRIP_HIGH = new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), 2);
*/
	public static final FlightCharm FLIGHT_CHARM = new FlightCharm(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS));

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

		System.out.println(MOD_ID + " Initialized");
	}

	public ExtendoGrip setExtendoGrips(int reach, int attack_reach) {
		return new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), reach, attack_reach);
	}


}
