package net.milkev.milkevsessentials.common;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.milkev.milkevsessentials.common.trinkets.ExtendoGrip;
import net.milkev.milkevsessentials.common.trinkets.FlightRing;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";

	public static final ExtendoGrip EXTENDO_GRIP_LOW = new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), 3, 0);
	public static final ExtendoGrip EXTENDO_GRIP_NORMAL = new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), 5, 5);
	public static final ExtendoGrip EXTENDO_GRIP_HIGH = new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), 15, 15);

	public static final FlightRing FLIGHT_RING = new FlightRing(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS));

	@Override
	public void onInitialize() {

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_low"), EXTENDO_GRIP_LOW);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_normal"), EXTENDO_GRIP_NORMAL);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_high"), EXTENDO_GRIP_HIGH);

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "flight_ring"), FLIGHT_RING);

		System.out.println(MOD_ID + " Initialized");
	}



}