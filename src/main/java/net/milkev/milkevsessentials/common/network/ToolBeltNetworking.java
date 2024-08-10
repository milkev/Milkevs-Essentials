package net.milkev.milkevsessentials.common.network;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
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
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(serverPlayerEntity);
        if(MilkevsEssentials.TOOL_BELT != null) {
            if (trinketComponent.get().isEquipped(MilkevsEssentials.TOOL_BELT)) {
                //System.out.println("toolbelt is equipped!");

                ItemStack toolBelt = getToolBeltItemStack(trinketComponent);

                ToolBelt.swap(toolBelt, serverPlayerEntity);

            } else {
                //System.out.println("toolbelt is not equipped!");
            }
        }

    }

    private static ItemStack getToolBeltItemStack(Optional<TrinketComponent> trinketComponent) {
        for(int i = 0; i <trinketComponent.get().getAllEquipped().size(); i++) {
            if(trinketComponent.get().getAllEquipped().get(i).getRight().isOf(MilkevsEssentials.TOOL_BELT)) {
                return trinketComponent.get().getAllEquipped().get(i).getRight();
            }
        }
        //technically unreachable in use case, but :shrug:
        return ItemStack.EMPTY;
    }

}
