package net.milkev.milkevsessentials.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="milkevsessentials")
public class ModConfig implements ConfigData {
    @Comment("If disabling an item should remove it from the game. Default: true")
    public boolean itemDisableSetting = true;
    @Comment("If Extendo Grips are enabled. Default: true")
    public boolean enableExtendoGrips = true;
    @Comment("If Flight Charm is enabled. Default: true")
    public boolean enableFlightCharm = true;
    //@Comment("If Amethyst Launcher is enabled. This item is not yet implemented. Default: false")
    //public boolean enableAmethystLauncher = false;
    @Comment("If Tool belt is enabled. Default: true")
    public boolean enableToolBelt = true;
    @Comment("If Tool belt should pickup items when equipped. Priorities are: Fill stacks in hotbar, Fill stacks in toolbelt, Fill empty slots in hotbar, normal. Default: true) ")
    public boolean enableToolBeltPickup = true;
    @Comment("If Instant Shield Blocking is enabled. Default: true")
    public boolean enableInstantShieldBlocking = true;
    @Comment("If holding shield up should negate fall damage. Default: false")
    public boolean enableShieldBlocksFallDamage = false;
    @Comment("Extendo Grips reach buffs\n" +
            "Default: 3")
    public int extendoGripsLowBlockReach = 3;
    @Comment("Default: 0")
    public int extendoGripsLowAttackReach = 0;
    @Comment("Default: 5")
    public int extendoGripsNormalBlockReach = 5;
    @Comment("Default: 5")
    public int extendoGripsNormalAttackReach = 5;
    @Comment("Default: 15")
    public int extendoGripsHighBlockReach = 15;
    @Comment("Default: 15")
    public int extendoGripsHighAttackReach = 15;
    @Comment("Wether Milkevs Custom Rules are enabled. Consists of ancient debris and wheat being effected by fortune, as well as crafting recipes for cobweb from string and vice versa")
    public boolean milkevsCustomRules = true;
    @Comment("If you should be able to make leather from rotten flesh")
    public boolean rottenFleshToLeather = true;
    @Comment("If Gluttony Charm should be enabled. Default: true")
    public boolean gluttonyCharm = true;
    @Comment("If OP Gluttony Charm should be enabled. Default: true")
    public boolean opGluttonyCharm = true;
    @Comment("If Alchemical Stasis Soother should be enabled. Default: true")
    public boolean alchemicalStasisSoother = true;
    @Comment("How often the Alchemical Stasis Soother should refresh effects in ticks. 1 second = 20 ticks. Default: 15")
    public int alchemicalStasisSootherRefreshTimer = 15;
    @Comment("If the Alchemical Stasis Soother should refresh effects shorter than the refresh timer. Default: true")
    public boolean alchemicalStasisSootherRefreshesShortEffects = true;
    @Comment("If the Alchemical Stasis Soother should prevent effects from being added to the player (commands, potions, mob attacks, beacons, food, etc). Default: true")
    public boolean alchemicalStasisSootherPreventEffectAdd = true;
    @Comment("If the Alchemical Stasis Soother should prevent effects from being forcibly removed from the player (commands, milk, etc). This does NOT stop effects from being removed by their timers ending. Timers will only be allowed to end if alchemicalStasisSootherRefreshesShortEffects is off and the effect has a duration below the refresh timer. Default: true")
    public boolean alchemicalStasisSootherPreventEffectRemove = true;
    @Comment("If the Dirt Singularity should be enabled. Default: true")
    public boolean dirtSingularity = true;
    @Comment("If Piglins, Zombified Piglins and Wither Skeletons should be blocked from dropping the weapon they spawn with")
    public boolean blockSpamWeaponDrops = true;
    @Comment("If Painting Stability should be enabled. This makes it so only full blocks placed on a painting will break it. Makes it so you can place torches/trapdoors/etc in the same spot as a painting. Default: true")
    public boolean paintingStability = true;
}
