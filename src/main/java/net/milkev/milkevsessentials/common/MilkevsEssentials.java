package net.milkev.milkevsessentials.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.milkev.milkevsessentials.common.trinkets.ExtendoGrip;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";

	public static final ExtendoGrip EXTENDO_GRIP = new ExtendoGrip(new FabricItemSettings(), 10, 10);


	@Override
	public void onInitialize() {

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip"), EXTENDO_GRIP);

		System.out.println(MOD_ID + " Initialized");
	}



}
