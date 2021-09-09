package net.milkev.milkevsessentials.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="milkevsessentials")
public class ModConfig implements ConfigData {
    @Comment("If Extendo Grips are enabled. Default: true")
    public boolean enableExtendoGrips = true;
    @Comment("If Flight Charm is enabled. Default: true")
    public boolean enableFlightCharm = true;
    @Comment("Extendo Grips reach buffs\nDefault: 3")
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
}