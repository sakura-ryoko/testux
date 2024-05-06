package io.github.sakuraryoko.testux.event;

import net.minecraft.server.MinecraftServer;
import io.github.sakuraryoko.testux.data.DataManager;
import fi.dy.masa.servux.interfaces.IServerListener;

public class ServerListener implements IServerListener
{
    @Override
    public void onServerStarting(MinecraftServer server)
    {
        DataManager.getInstance().onServerStart();
    }

    @Override
    public void onServerStarted(MinecraftServer server)
    {
    }

    @Override
    public void onServerStopping(MinecraftServer server)
    {
        DataManager.getInstance().reset(true);
    }

    @Override
    public void onServerStopped(MinecraftServer server)
    {
        DataManager.getInstance().onServerStop();
    }
}
