package net.milkev.milkevsessentials.common.network;

import io.wispforest.accessories.api.AccessoriesCapability;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.milkev.milkevsessentials.common.items.trinkets.ToolBelt;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ToolBeltNetworking {

    public static Identifier USE_TOOLBELT = Identifier.of(MilkevsEssentials.MOD_ID, "use_toolbelt");

    public static void init() {
        //System.out.println("Milkevs Essentials: Tool Belt Networking Initialized");
        PayloadTypeRegistry.playC2S().register(ToolBeltPacket.PACKET_ID, ToolBeltPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ToolBeltPacket.PACKET_ID, ((payload, context) -> {
            recieveUseToolBeltPacket(context.player());
        }));
    }

    private static void recieveUseToolBeltPacket(ServerPlayerEntity serverPlayerEntity) {

        //System.out.println("recieve use tool belt packet called!");
        
        try {
            AccessoriesCapability accessoriesCapability = AccessoriesCapability.get(serverPlayerEntity);
            if (MilkevsEssentials.TOOL_BELT != null) {
                if (accessoriesCapability.isEquipped(MilkevsEssentials.TOOL_BELT)) {
                    //System.out.println("toolbelt is equipped!");

                    ItemStack toolBelt = accessoriesCapability.getFirstEquipped(MilkevsEssentials.TOOL_BELT).stack();

                    ToolBelt.swap(toolBelt, serverPlayerEntity);

                } else {
                    //System.out.println("toolbelt is not equipped!");
                }
            }
        } catch(Exception e) {
            //this is here incase accessoriesCapability produces null, which is intended by accessories, and just means that the entity we are working on cant have accessories equipped
        }

    }

}
