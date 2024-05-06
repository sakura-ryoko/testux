package io.github.sakuraryoko.testux.event;

import java.net.SocketAddress;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import io.github.sakuraryoko.testux.data.DataManager;
import fi.dy.masa.servux.interfaces.IPlayerListener;

public class PlayerListener implements IPlayerListener
{
    @Override
    public void onClientConnect(SocketAddress addr, GameProfile profile, Text result)
    {
        // NO-OP
    }

    @Override
    public void onPlayerJoin(SocketAddress addr, GameProfile profile, ServerPlayerEntity player)
    {
        DataManager.getInstance().onPlayerJoin(player);
    }

    @Override
    public void onPlayerRespawn(ServerPlayerEntity newPlayer, ServerPlayerEntity oldPlayer)
    {
        // NO-OP
    }

    @Override
    public void onPlayerOp(GameProfile profile, UUID uuid, @Nullable ServerPlayerEntity player)
    {
        // NO-OP
    }

    @Override
    public void onPlayerDeOp(GameProfile profile, UUID uuid, @Nullable ServerPlayerEntity player)
    {
        // NO-OP
    }

    @Override
    public void onPlayerLeave(ServerPlayerEntity player)
    {
        DataManager.getInstance().onPlayerLeave(player);
    }
}
