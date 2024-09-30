package net.milkev.milkevsessentials.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.milkev.milkevsessentials.common.items.trinkets.*;
import net.milkev.milkevsessentials.common.network.ToolBeltNetworking;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import javax.tools.Tool;
import java.util.List;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";

	public static FlightCharm FLIGHT_CHARM = null;
	public static Identifier FLIGHT_CHARM_FLIGHT_ID = Identifier.of(MOD_ID, "milkevsessentials_flight_charm");
	public static AbilitySource FLIGHT_CHARM_FLIGHT_ABILITYSOURCE = Pal.getAbilitySource(FLIGHT_CHARM_FLIGHT_ID);

	public static ToolBelt TOOL_BELT = null;

	public static final Identifier TOOLBELT_PICKUP_ID = Identifier.of(MOD_ID, "toolbelt_pickup");
	public static SoundEvent TOOLBELT_PICKUP = SoundEvent.of(TOOLBELT_PICKUP_ID);
	public static final ComponentType<ToolbeltComponent> TOOLBELT_COMPONENT = Registry.register(
			Registries.DATA_COMPONENT_TYPE,
			Identifier.of(MOD_ID, "toolbelt_inventory"),
			ComponentType.<ToolbeltComponent>builder().codec(ToolbeltComponent.CODEC).build()
	);



	public static Item CONDENSED_ROTTEN_FLESH = null;

	public static CharmWithTooltip GLUTTONY_CHARM = null;
	public static CharmWithTooltip OP_GLUTTONY_CHARM = null;
	
	public static CharmWithTooltip ALCHEMICAL_STASIS_SOOTHER = null;
	
	public static final TagKey<StatusEffect> ALCHEMICAL_STASIS_SOOTHER_BLACKLIST = TagKey.of(RegistryKeys.STATUS_EFFECT, Identifier.of(MOD_ID, "alchemical_stasis_soother_blacklist"));

	/*
	public static final AmethystLauncher AMETHYST_LAUNCHER = new AmethystLauncher(new FabricItemSettings().maxCount(1).group(ItemGroup.COMBAT));
	public static final EntityType<AmethystShot> AMETHYST_SHOT_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			Identifier.of(MOD_ID, "amethyst_shot"),
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
			register("extendo_grip_low", 
					setExtendoGrips(config.extendoGripsLowBlockReach, config.extendoGripsLowAttackReach, Rarity.UNCOMMON),
					ItemGroups.TOOLS);
			register("extendo_grip_normal",
					setExtendoGrips(config.extendoGripsNormalBlockReach, config.extendoGripsNormalAttackReach, Rarity.RARE),
					ItemGroups.TOOLS);
			register("extendo_grip_high",
					setExtendoGrips(config.extendoGripsHighBlockReach, config.extendoGripsHighAttackReach, Rarity.EPIC),
					ItemGroups.TOOLS);
		} else if(!config.itemDisableSetting) {
			register("extendo_grip_low",
					setExtendoGrips(config.extendoGripsLowBlockReach, config.extendoGripsLowAttackReach, Rarity.UNCOMMON),
					ItemGroups.TOOLS);
			register("extendo_grip_normal",
					setExtendoGrips(config.extendoGripsNormalBlockReach, config.extendoGripsNormalAttackReach, Rarity.RARE),
					ItemGroups.TOOLS);
			register("extendo_grip_high",
					setExtendoGrips(config.extendoGripsHighBlockReach, config.extendoGripsHighAttackReach, Rarity.EPIC),
					ItemGroups.TOOLS);
		}

		if(config.enableFlightCharm) {
			DynamicDatapacks("flight_charm");
			FLIGHT_CHARM = new FlightCharm(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
			register("flight_charm", FLIGHT_CHARM, ItemGroups.TOOLS);
		} else if(!config.itemDisableSetting) {
			FLIGHT_CHARM = new FlightCharm(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
			register("flight_charm", FLIGHT_CHARM, ItemGroups.TOOLS);
		}

		/*
		if(config.enableAmethystLauncher) {
			//DynamicDataRecipe("amethyst_launcher"); //recipe doesnt exist yet
			TagFactory.ITEM.create(Identifier.of(MOD_ID, "amethyst_shard"));
			Registry.register(Registry.ITEM, Identifier.of(MOD_ID, "amethyst_launcher"), AMETHYST_LAUNCHER);
		}*/

		if(config.enableToolBelt) {
			DynamicDatapacks("toolbelt");
			TOOL_BELT = new ToolBelt(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
			register("toolbelt", TOOL_BELT, ItemGroups.TOOLS);
			ToolBeltNetworking.init();
			Registry.register(Registries.SOUND_EVENT, TOOLBELT_PICKUP_ID, TOOLBELT_PICKUP);
		} else if(!config.itemDisableSetting) {
			TOOL_BELT = new ToolBelt(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
			register("toolbelt", TOOL_BELT, ItemGroups.TOOLS);
			ToolBeltNetworking.init();
			Registry.register(Registries.SOUND_EVENT, TOOLBELT_PICKUP_ID, TOOLBELT_PICKUP);
		}

		if(config.milkevsCustomRules) {
			DynamicDatapacks("milkevscustomrules");
		}

		if(config.rottenFleshToLeather) {
			DynamicDatapacks("rottenfleshtoleather");
			CONDENSED_ROTTEN_FLESH = new Item(new Item.Settings().maxCount(64));
			register("condensed_rotten_flesh", CONDENSED_ROTTEN_FLESH, ItemGroups.INGREDIENTS);
		} else if(!config.itemDisableSetting) {
			CONDENSED_ROTTEN_FLESH = new Item(new Item.Settings().maxCount(64));
			register("condensed_rotten_flesh", CONDENSED_ROTTEN_FLESH, ItemGroups.INGREDIENTS);
		}

		if(config.gluttonyCharm) {
			DynamicDatapacks("gluttony_charm");
			GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.RARE), "gluttony_charm.tooltip", "gluttony");
			register("gluttony_charm", GLUTTONY_CHARM, ItemGroups.TOOLS);
		} else if(!config.itemDisableSetting) {
			GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.RARE), "gluttony_charm.tooltip", "gluttony");
			register("gluttony_charm", GLUTTONY_CHARM, ItemGroups.TOOLS);
		}

		if(config.opGluttonyCharm) {
			DynamicDatapacks("op_gluttony_charm");
			OP_GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "op_gluttony_charm.tooltip", "gluttony");
			register("op_gluttony_charm", OP_GLUTTONY_CHARM, ItemGroups.TOOLS);
		} else if(!config.itemDisableSetting) {
			OP_GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "op_gluttony_charm.tooltip", "gluttony");
			register("op_gluttony_charm", OP_GLUTTONY_CHARM, ItemGroups.TOOLS);
		}
		
		if(config.alchemicalStasisSoother) {
			DynamicDatapacks("alchemical_stasis_soother");
			ALCHEMICAL_STASIS_SOOTHER = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "alchemical_stasis_soother_charm.tooltip", "face");
			register( "alchemical_stasis_soother", ALCHEMICAL_STASIS_SOOTHER, ItemGroups.TOOLS);
		} else if(!config.itemDisableSetting) {
			ALCHEMICAL_STASIS_SOOTHER = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "alchemical_stasis_soother_charm.tooltip", "face");
			register( "alchemical_stasis_soother", ALCHEMICAL_STASIS_SOOTHER, ItemGroups.TOOLS);
		}

		System.out.println(MOD_ID + " Initialized");
	}

	public void DynamicDatapacks(String datapackName) {

		ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of(MOD_ID, datapackName), FabricLoader.getInstance().getModContainer(MOD_ID).get(), ResourcePackActivationType.ALWAYS_ENABLED);
		
	}

	public ExtendoGrip setExtendoGrips(int reach, int attack_reach, Rarity rarity) {
        return new ExtendoGrip(new Item.Settings().maxCount(1).rarity(rarity), reach, attack_reach);
	}
	
	public Identifier id(String id) {
		return Identifier.of(MOD_ID, id);
	}

	public void AddToGroup(RegistryKey<ItemGroup> group, ItemConvertible item) {
		ItemGroupEvents.modifyEntriesEvent(group).register(content -> {
			content.add(item);
		});
	}
	
	public void register(String id, Item object, RegistryKey<ItemGroup> group) {
		Registry.register(Registries.ITEM, id(id), object);
		AddToGroup(group, object);
	}


}
