package com.github.sakuraryoko.testux.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import com.github.sakuraryoko.testux.data.DataManager;

import static net.minecraft.server.command.CommandManager.*;

public class TestUXCommands
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                literal("testux")
                        .then(
                                argument("player", EntityArgumentType.player())
                                .executes(ctx -> sendPacket(ctx.getSource(), ctx, EntityArgumentType.getPlayer(ctx, "player"), ""))
                                .then(argument("message", StringArgumentType.greedyString())
                                        .executes(ctx -> sendPacket(ctx.getSource(), ctx, EntityArgumentType.getPlayer(ctx, "player"), StringArgumentType.getString(ctx, "message")))
                                )
                        )
        ));
    }

    private static int sendPacket(ServerCommandSource src, CommandContext<ServerCommandSource> context, ServerPlayerEntity player, String message)
    {
        ServerPlayerEntity from = context.getSource().getPlayer();
        src.sendFeedback(() -> Text.literal("Sending Packet to Player: "+player.getName().getLiteralString()), false);
        DataManager.getInstance().sendPacket(player, from, message);

        return 1;
    }
}
