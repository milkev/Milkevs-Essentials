package net.milkev.milkevsessentials.common;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.milkev.milkevsessentials.common.items.trinkets.CharmWithTooltip;
import net.milkev.milkevsessentials.common.items.trinkets.ExtendoGrip;
import net.milkev.milkevsessentials.common.items.trinkets.FlightCharm;
import net.milkev.milkevsessentials.common.items.trinkets.ToolBelt;
import net.milkev.milkevsessentials.common.network.ToolBeltNetworking;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";

	public static FlightCharm FLIGHT_CHARM = null;
	public static Identifier FLIGHT_CHARM_FLIGHT_ID =new Identifier(MOD_ID + "milkevsessentials_flight_charm");
	public static AbilitySource FLIGHT_CHARM_FLIGHT_ABILITYSOURCE = Pal.getAbilitySource(FLIGHT_CHARM_FLIGHT_ID);

	public static ToolBelt TOOL_BELT = null;

	public static final Identifier TOOLBELT_PICKUP_ID = new Identifier(MOD_ID, "toolbelt_pickup");
	public static SoundEvent TOOLBELT_PICKUP = SoundEvent.of(TOOLBELT_PICKUP_ID);

	public static Item CONDENSED_ROTTEN_FLESH = null;

	public static CharmWithTooltip GLUTTONY_CHARM = null;
	public static CharmWithTooltip OP_GLUTTONY_CHARM = null;

	/*
	public static final AmethystLauncher AMETHYST_LAUNCHER = new AmethystLauncher(new FabricItemSettings().maxCount(1).group(ItemGroup.COMBAT));
	public static final EntityType<AmethystShot> AMETHYST_SHOT_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MOD_ID, "amethyst_shot"),
			FabricEntityTypeBuilder.<AmethystShot>create(SpawnGroup.MISC, AmethystShot::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);*/


	@Override
	public void onInitialize() {

		//AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		//Config registered in MilkevsEssentialsMixinCondition.java
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		if(config.enableExtendoGrips) {
			DynamicDatapacks("extendo_grips");
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "extendo_grip_low"),
					setExtendoGrips(config.extendoGripsLowBlockReach, config.extendoGripsLowAttackReach, Rarity.UNCOMMON));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "extendo_grip_normal"),
					setExtendoGrips(config.extendoGripsNormalBlockReach, config.extendoGripsNormalAttackReach, Rarity.RARE));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "extendo_grip_high"),
					setExtendoGrips(config.extendoGripsHighBlockReach, config.extendoGripsHighAttackReach, Rarity.EPIC));
		} else if(!config.itemDisableSetting) {
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "extendo_grip_low"),
					setExtendoGrips(config.extendoGripsLowBlockReach, config.extendoGripsLowAttackReach, Rarity.UNCOMMON));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "extendo_grip_normal"),
					setExtendoGrips(config.extendoGripsNormalBlockReach, config.extendoGripsNormalAttackReach, Rarity.RARE));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "extendo_grip_high"),
					setExtendoGrips(config.extendoGripsHighBlockReach, config.extendoGripsHighAttackReach, Rarity.EPIC));
		}

		if(config.enableFlightCharm) {
			DynamicDatapacks("flight_charm");
			FLIGHT_CHARM = new FlightCharm(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "flight_charm"), FLIGHT_CHARM);
		} else if(!config.itemDisableSetting) {
			FLIGHT_CHARM = new FlightCharm(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "flight_charm"), FLIGHT_CHARM);
		}

		/*
		if(config.enableAmethystLauncher) {
			//DynamicDataRecipe("amethyst_launcher"); //recipe doesnt exist yet
			TagFactory.ITEM.create(new Identifier(MOD_ID, "amethyst_shard"));
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "amethyst_launcher"), AMETHYST_LAUNCHER);
		}*/

		if(config.enableToolBelt) {
			DynamicDatapacks("toolbelt");
			TOOL_BELT = new ToolBelt(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "toolbelt"), TOOL_BELT);
			ToolBeltNetworking.init();
			Registry.register(Registries.SOUND_EVENT, TOOLBELT_PICKUP_ID, TOOLBELT_PICKUP);
		} else if(!config.itemDisableSetting) {
			TOOL_BELT = new ToolBelt(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "toolbelt"), TOOL_BELT);
			ToolBeltNetworking.init();
			Registry.register(Registries.SOUND_EVENT, TOOLBELT_PICKUP_ID, TOOLBELT_PICKUP);
		}

		if(config.milkevsCustomRules) {
			DynamicDatapacks("milkevscustomrules");
		}

		if(config.rottenFleshToLeather) {
			DynamicDatapacks("rottenfleshtoleather");
			CONDENSED_ROTTEN_FLESH = new Item(new FabricItemSettings().maxCount(64));
			AddToGroup(ItemGroups.INGREDIENTS, CONDENSED_ROTTEN_FLESH);
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "condensed_rotten_flesh"), CONDENSED_ROTTEN_FLESH);
		} else if(!config.itemDisableSetting) {
			CONDENSED_ROTTEN_FLESH = new Item(new FabricItemSettings().maxCount(64));
			AddToGroup(ItemGroups.INGREDIENTS, CONDENSED_ROTTEN_FLESH);
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "condensed_rotten_flesh"), CONDENSED_ROTTEN_FLESH);
		}

		if(config.gluttonyCharm) {
			DynamicDatapacks("gluttony_charm");
			GLUTTONY_CHARM = new CharmWithTooltip(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "gluttony_charm.tooltip", "gluttony");
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "gluttony_charm"), GLUTTONY_CHARM);
		} else if(!config.itemDisableSetting) {
			GLUTTONY_CHARM = new CharmWithTooltip(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), "gluttony_charm.tooltip", "gluttony");
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "gluttony_charm"), GLUTTONY_CHARM);
		}

		if(config.opGluttonyCharm) {
			DynamicDatapacks("op_gluttony_charm");
			OP_GLUTTONY_CHARM = new CharmWithTooltip(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), "op_gluttony_charm.tooltip", "gluttony");
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "op_gluttony_charm"), OP_GLUTTONY_CHARM);
		} else if(!config.itemDisableSetting) {
			OP_GLUTTONY_CHARM = new CharmWithTooltip(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), "op_gluttony_charm.tooltip", "gluttony");
			Registry.register(Registries.ITEM, new Identifier(MOD_ID, "op_gluttony_charm"), OP_GLUTTONY_CHARM);
		}

		System.out.println(MOD_ID + " Initialized");
	}

	public void DynamicDatapacks(String datapackName) {
		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
			var added = ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, datapackName), modContainer, ResourcePackActivationType.ALWAYS_ENABLED);
		});

		//System.out.println("Datapack Added: " + datapackName);
	}

	public ExtendoGrip setExtendoGrips(int reach, int attack_reach, Rarity rarity) {
		ExtendoGrip grip = new ExtendoGrip(new FabricItemSettings().maxCount(1).rarity(rarity), reach, attack_reach);
		AddToGroup(ItemGroups.TOOLS, grip);
		return grip;
	}

	public void AddToGroup(RegistryKey<ItemGroup> group, ItemConvertible item) {
		ItemGroupEvents.modifyEntriesEvent(group).register(content -> {
			content.add(item);
		});
	}


}
