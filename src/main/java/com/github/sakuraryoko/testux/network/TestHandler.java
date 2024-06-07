package com.github.sakuraryoko.testux.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import com.github.sakuraryoko.testux.Reference;
import com.github.sakuraryoko.testux.TestUX;
import fi.dy.masa.servux.network.server.IPluginServerPlayHandler;

public abstract class TestHandler<T extends CustomPayload> implements IPluginServerPlayHandler<T>
{
    private static final TestHandler<TestPayload> INSTANCE = new TestHandler<>()
    {
        @Override
        public void receive(TestPayload payload, ServerPlayNetworking.Context context)
        {
            TestHandler.INSTANCE.receivePlayPayload(payload, context);
        }
    };
    public static TestHandler<TestPayload> getInstance() { return INSTANCE; }
    public static final Identifier CHANNEL_ID = Identifier.of(Reference.MOD_ID, "test");
    public static final int PROTOCOL_VERSION = 1;

    private boolean registered = false;

    @Override
    public Identifier getPayloadChannel()
    {
        return CHANNEL_ID;
    }

    @Override
    public boolean isPlayRegistered(Identifier identifier)
    {
        if (identifier.equals(CHANNEL_ID))
        {
            return this.registered;
        }

        return false;
    }

    @Override
    public void setPlayRegistered(Identifier identifier)
    {
        if (identifier.equals(CHANNEL_ID))
        {
            this.registered = true;
        }
    }

    @Override
    public void reset(Identifier identifier)
    {
        if (identifier.equals(CHANNEL_ID))
        {
            TestUX.logger.info("TestHandler: reset()");

            TestHandler.INSTANCE.unregisterPlayReceiver();
        }
    }

    public void decodePayload(ServerPlayerEntity player, TestData content)
    {
        TestUX.logger.info("TestHandler#decodePayload: from {}", player.getName().getLiteralString());

        content.dump();
    }

    public void encodePayload(ServerPlayerEntity player, TestData content)
    {
        TestHandler.INSTANCE.sendPlayPayload(player, new TestPayload(content));
    }

    @Override
    public void receivePlayPayload(T payload, ServerPlayNetworking.Context context)
    {
        TestHandler.INSTANCE.decodePayload(context.player(), ((TestPayload) payload).content());
    }
}
