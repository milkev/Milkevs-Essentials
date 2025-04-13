package net.milkev.milkevsessentials.common.network;

import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;


import java.util.UUID;

public record ToolBeltPacket(UUID uuid) implements CustomPayload {

    public static final CustomPayload.Id<ToolBeltPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of(MilkevsEssentials.MOD_ID, "toolbeltpacket"));
    public static final PacketCodec<RegistryByteBuf, ToolBeltPacket> PACKET_CODEC = Uuids.PACKET_CODEC.xmap(ToolBeltPacket::new, ToolBeltPacket::uuid).cast();

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
