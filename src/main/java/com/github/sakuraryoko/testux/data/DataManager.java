package com.github.sakuraryoko.testux.data;

import javax.annotation.Nullable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import com.github.sakuraryoko.testux.Reference;
import com.github.sakuraryoko.testux.TestUX;
import com.github.sakuraryoko.testux.network.TestPacket;
import com.github.sakuraryoko.testux.network.TestHandler;
import fi.dy.masa.servux.network.IPluginServerPlayHandler;
import fi.dy.masa.servux.network.ServerPlayHandler;

public class DataManager
{
    private static final DataManager INSTANCE = new DataManager();
    public static DataManager getInstance() { return INSTANCE; }

    private final static TestHandler<TestPacket.Payload> HANDLER = TestHandler.getInstance();

    private DataManager()
    {
    }

    public void reset(boolean isLogout)
    {
        if (isLogout)
        {
            TestUX.logger.info("DataManager#reset() - log-out");

            // Reset Handlers
            HANDLER.reset(HANDLER.getPayloadChannel());
        }
        else
        {
            TestUX.logger.info("DataManager#reset() - dimension change or log-in");
        }
    }

    public void onGameInit()
    {
        TestUX.logger.info("DataManager#onGameInit(): execute");

        // Register Handlers
        ServerPlayHandler.getInstance().registerServerPlayHandler(HANDLER);

        // Register Payload Channels
        HANDLER.registerPlayPayload(TestPacket.Payload.ID, TestPacket.Payload.CODEC, IPluginServerPlayHandler.BOTH_SERVER);
  
    }

    public void onServerStart()
    {
        TestUX.logger.info("DataManager#onServerStart(): execute");

        // Register Receivers
        HANDLER.registerPlayReceiver(TestPacket.Payload.ID, HANDLER::receivePlayPayload);
    }

    public void onServerStop()
    {
        TestUX.logger.info("DataManager#onServerStop(): execute");

        // Un-Register Receivers
        HANDLER.unregisterPlayReceiver();
    }

    public void onPlayerJoin(ServerPlayerEntity player)
    {
        TestUX.logger.info("DataManager#onPlayerJoin(): player {}", player.getName().getLiteralString());

        // Do Something, like register client.
        NbtCompound data = new NbtCompound();
        data.putString("test", "hello");

        HANDLER.encodePayload(player, new TestPacket(Reference.MOD_ID, Reference.MOD_VERSION, TestHandler.PROTOCOL_VERSION, data));
    }

    public void onPlayerLeave(ServerPlayerEntity player)
    {
        TestUX.logger.info("DataManager#onPlayerLeave(): player {}", player.getName().getLiteralString());

        // Do Something, like un-register a client.
    }

    public void sendPacket(ServerPlayerEntity player, @Nullable ServerPlayerEntity from, String message)
    {
        NbtCompound data = new NbtCompound();
        if (from != null)
        {
            data.putString("from", from.getName().getLiteralString());
        }
        else
        {
            data.putString("from", "<>");

        }
        if (message.isEmpty() == false)
        {
            data.putString("message", message);
        }
        else
        {
            data.putString("message", "<>");
        }

        HANDLER.encodePayload(player, new TestPacket(Reference.MOD_ID, Reference.MOD_VERSION, TestHandler.PROTOCOL_VERSION, data));
    }
}
