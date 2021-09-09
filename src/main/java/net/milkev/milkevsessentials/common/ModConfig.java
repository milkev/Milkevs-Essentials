package net.milkev.milkevsessentials.common;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="milkevsessentials")
public class ModConfig implements ConfigData {
    boolean enableExtendoGrips = true;
    boolean enableFlightCharm = true;
}
