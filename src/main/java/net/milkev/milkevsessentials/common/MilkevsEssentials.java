package net.milkev.milkevsessentials.common;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.milkev.milkevsessentials.common.items.trinkets.*;
import net.milkev.milkevsessentials.common.network.ToolBeltNetworking;
import net.minecraft.component.ComponentType;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.lwjgl.opengl.GL;

import java.util.*;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";
	
	public static List<ShapedRecipeJsonBuilder> recipesShaped = new ArrayList<>();
	public static Map<List<ItemConvertible>, ItemConvertible> recipesSmelt = new HashMap<>();

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
	public static final TagKey<Item> TOOLBELT_BLACKLIST = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "toolbelt_blacklist"));

	public static Item CONDENSED_ROTTEN_FLESH = null;

	public static CharmWithTooltip GLUTTONY_CHARM = null;
	public static CharmWithTooltip OP_GLUTTONY_CHARM = null;
	
	public static CharmWithTooltip ALCHEMICAL_STASIS_SOOTHER = null;
	
	public static final TagKey<StatusEffect> ALCHEMICAL_STASIS_SOOTHER_BLACKLIST = TagKey.of(RegistryKeys.STATUS_EFFECT, Identifier.of(MOD_ID, "alchemical_stasis_soother_blacklist"));

	/*
	this shits so deprecated now. i swear ill make it one day TM
	public static final AmethystLauncher AMETHYST_LAUNCHER = new AmethystLauncher(new FabricItemSettings().maxCount(1).group(ItemGroup.COMBAT));
	public static final EntityType<AmethystShot> AMETHYST_SHOT_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			Identifier.of(MOD_ID, "amethyst_shot"),
			FabricEntityTypeBuilder.<AmethystShot>create(SpawnGroup.MISC, AmethystShot::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build()
	);*/


	@Override
	public void onInitialize() {

		//AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		//Config registered in MilkevsEssentialsMixinCondition.java
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
				
		if(config.enableExtendoGrips) {
			register("extendo_grip_low", 
					setExtendoGrips(config.extendoGripsLowBlockReach, config.extendoGripsLowAttackReach, Rarity.UNCOMMON),
					ItemGroups.TOOLS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Registries.ITEM.get(id("extendo_grip_low")))
					.pattern("P P")
					.pattern("BIB")
					.pattern(" S ")
					.input('P', Items.PISTON)
					.input('B', Items.IRON_BARS)
					.input('I', Items.IRON_INGOT)
					.input('S', Items.STICK));
			register("extendo_grip_normal",
					setExtendoGrips(config.extendoGripsNormalBlockReach, config.extendoGripsNormalAttackReach, Rarity.RARE),
					ItemGroups.TOOLS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Registries.ITEM.get(id("extendo_grip_normal")))
					.pattern("N N")
					.pattern("NEN")
					.pattern(" B ")
					.input('N', Items.NETHERITE_SCRAP)
					.input('E', Registries.ITEM.get(id("extendo_grip_low")))
					.input('B', Items.BLAZE_ROD));
			register("extendo_grip_high",
					setExtendoGrips(config.extendoGripsHighBlockReach, config.extendoGripsHighAttackReach, Rarity.EPIC),
					ItemGroups.TOOLS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Registries.ITEM.get(id("extendo_grip_high")))
					.pattern("PSP")
					.pattern("DGD")
					.pattern("RIR")
					.input('P', Items.ENDER_PEARL)
					.input('S', Items.NETHER_STAR)
					.input('D', Items.DIAMOND)
					.input('G', Registries.ITEM.get(id("extendo_grip_normal")))
					.input('R', Items.END_ROD)
					.input('I', Items.NETHERITE_INGOT));
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
			FLIGHT_CHARM = new FlightCharm(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
			register("flight_charm", FLIGHT_CHARM, ItemGroups.TOOLS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, FLIGHT_CHARM)
					.pattern("BTB")
					.pattern("SNS")
					.pattern("ETE")
					.input('B', Items.DRAGON_BREATH)
					.input('T', Items.GRAY_TERRACOTTA)
					.input('S', Items.SHULKER_SHELL)
					.input('N', Items.NETHER_STAR)
					.input('E', Items.ELYTRA));
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
			TOOL_BELT = new ToolBelt(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
			register("toolbelt", TOOL_BELT, ItemGroups.TOOLS);
			ToolBeltNetworking.init();
			Registry.register(Registries.SOUND_EVENT, TOOLBELT_PICKUP_ID, TOOLBELT_PICKUP);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, TOOL_BELT)
				.pattern("LLL")
				.pattern("CCC")
				.input('L', Items.LEATHER)
				.input('C', Items.CHAIN));
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
			CONDENSED_ROTTEN_FLESH = new Item(new Item.Settings().maxCount(64));
			register("condensed_rotten_flesh", CONDENSED_ROTTEN_FLESH, ItemGroups.INGREDIENTS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, CONDENSED_ROTTEN_FLESH)
					.pattern(" F ")
					.pattern("FFF")
					.pattern(" F ")
					.input('F', Items.ROTTEN_FLESH));
			recipesSmelt.put(Arrays.asList(CONDENSED_ROTTEN_FLESH), Items.LEATHER);
		} else if(!config.itemDisableSetting) {
			CONDENSED_ROTTEN_FLESH = new Item(new Item.Settings().maxCount(64));
			register("condensed_rotten_flesh", CONDENSED_ROTTEN_FLESH, ItemGroups.INGREDIENTS);
		}

		if(config.gluttonyCharm) {
			GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.RARE), "gluttony_charm.tooltip");
			register("gluttony_charm", GLUTTONY_CHARM, ItemGroups.TOOLS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, GLUTTONY_CHARM)
					.pattern("ABC")
					.pattern("DEF")
					.pattern("GHI")
					.input('A', Items.BREAD)
					.input('B', Items.GOLDEN_CARROT)
					.input('C', Items.COOKED_CHICKEN)
					.input('D', Items.COOKED_BEEF)
					.input('E', Items.GOLDEN_APPLE)
					.input('F', Items.CAKE)
					.input('G', Items.MELON_SLICE)
					.input('H', Items.PUMPKIN_PIE)
					.input('I', Items.COOKED_MUTTON));
		} else if(!config.itemDisableSetting) {
			GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.RARE), "gluttony_charm.tooltip");
			register("gluttony_charm", GLUTTONY_CHARM, ItemGroups.TOOLS);
		}

		if(config.opGluttonyCharm) {
			OP_GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "op_gluttony_charm.tooltip");
			register("op_gluttony_charm", OP_GLUTTONY_CHARM, ItemGroups.TOOLS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, OP_GLUTTONY_CHARM)
					.pattern("ABC")
					.pattern("DEF")
					.pattern("GHI")
					.input('A', Items.GLOW_BERRIES)
					.input('B', Items.ENCHANTED_GOLDEN_APPLE)
					.input('C', Items.TURTLE_EGG)
					.input('D', Items.HONEY_BOTTLE)
					.input('E', GLUTTONY_CHARM)
					.input('F', Items.DRAGON_EGG)
					.input('G', Items.SNIFFER_EGG)
					.input('H', Items.NETHER_STAR)
					.input('I', Items.RABBIT_STEW));
		} else if(!config.itemDisableSetting) {
			OP_GLUTTONY_CHARM = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "op_gluttony_charm.tooltip");
			register("op_gluttony_charm", OP_GLUTTONY_CHARM, ItemGroups.TOOLS);
		}
		
		if(config.alchemicalStasisSoother) {
			ALCHEMICAL_STASIS_SOOTHER = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "alchemical_stasis_soother_charm.tooltip");
			register( "alchemical_stasis_soother", ALCHEMICAL_STASIS_SOOTHER, ItemGroups.TOOLS);
			recipesShaped.add(ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ALCHEMICAL_STASIS_SOOTHER)
					.pattern("BB ")
					.pattern("BNn")
					.pattern(" nG")
					.input('B', Items.BLAZE_POWDER)
					.input('N', Items.NETHER_STAR)
					.input('n', Items.NETHERITE_INGOT)
					.input('G', Items.GOLD_INGOT));
		} else if(!config.itemDisableSetting) {
			ALCHEMICAL_STASIS_SOOTHER = new CharmWithTooltip(new Item.Settings().maxCount(1).rarity(Rarity.EPIC), "alchemical_stasis_soother_charm.tooltip");
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
