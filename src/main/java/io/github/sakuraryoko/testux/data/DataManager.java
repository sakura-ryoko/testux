package io.github.sakuraryoko.testux.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import io.github.sakuraryoko.testux.Reference;
import io.github.sakuraryoko.testux.TestUX;
import io.github.sakuraryoko.testux.network.TestData;
import io.github.sakuraryoko.testux.network.TestHandler;
import io.github.sakuraryoko.testux.network.TestPayload;
import fi.dy.masa.servux.network.server.IPluginServerPlayHandler;
import fi.dy.masa.servux.network.server.ServerPlayHandler;

public class DataManager
{
    private static final DataManager INSTANCE = new DataManager();
    public static DataManager getInstance() { return INSTANCE; }

    private final static TestHandler<TestPayload> HANDLER = TestHandler.getInstance();

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
        HANDLER.registerPlayPayload(TestPayload.TYPE, TestPayload.CODEC, IPluginServerPlayHandler.BOTH_SERVER);
  
    }

    public void onServerStart()
    {
        TestUX.logger.info("DataManager#onServerStart(): execute");

        // Register Receivers
        HANDLER.registerPlayReceiver(TestPayload.TYPE, HANDLER::receivePlayPayload);
    }

    public void onServerStopping()
    {
        TestUX.logger.info("DataManager#onServerStopping(): execute");

        // Un-Register Receivers
        HANDLER.unregisterPlayReceiver();
    }

    public void onPlayerJoin(ServerPlayerEntity player)
    {
        TestUX.logger.info("DataManager#onPlayerJoin(): player {}", player.getName().getLiteralString());

        // Do Something, like register client.
        NbtCompound data = new NbtCompound();
        data.putString("test", "hello");

        HANDLER.encodePayload(player, new TestData(Reference.MOD_ID, Reference.MOD_VERSION, TestHandler.PROTOCOL_VERSION, data));
    }

    public void onPlayerLeave(ServerPlayerEntity player)
    {
        TestUX.logger.info("DataManager#onPlayerLeave(): player {}", player.getName().getLiteralString());

        // Do Something, like un-register a client.
    }
}
