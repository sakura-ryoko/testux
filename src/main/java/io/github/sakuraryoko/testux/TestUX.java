package io.github.sakuraryoko.testux;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.api.ModInitializer;
import io.github.sakuraryoko.testux.data.DataManager;
import io.github.sakuraryoko.testux.event.PlayerListener;
import io.github.sakuraryoko.testux.event.ServerListener;
import fi.dy.masa.servux.event.PlayerHandler;
import fi.dy.masa.servux.event.ServerHandler;

public class TestUX implements ModInitializer
{
    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

    @Override
    public void onInitialize()
    {
        DataManager.getInstance().onGameInit();

        ServerListener serverListener = new ServerListener();
        ServerHandler.getInstance().registerServerHandler(serverListener);

        PlayerListener playerListener = new PlayerListener();
        PlayerHandler.getInstance().registerPlayerHandler(playerListener);

        //TestUXCommands.register();
    }
}
