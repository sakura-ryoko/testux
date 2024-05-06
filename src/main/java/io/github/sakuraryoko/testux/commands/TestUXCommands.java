package io.github.sakuraryoko.testux.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class TestUXCommands
{
    public static boolean inspectOn = false;

    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                literal("malitest")
                        .then(literal("inspect")
                                .executes(ctx -> inspectRoot(ctx.getSource(), ctx))
                                .then(literal("on")
                                        .executes(ctx -> turnOn(ctx.getSource(), ctx))
                                )
                                .then(literal("off")
                                        .executes(ctx -> turnOff(ctx.getSource(), ctx))
                                )
                        )
                        .then(literal("search")
                                .then(argument("param", StringArgumentType.greedyString())
                                        .executes(ctx -> sendSearchQuery(ctx.getSource(), ctx, StringArgumentType.getString(ctx, "param")))
                                )
                        )
        ));
    }

    private static int sendSearchQuery(ServerCommandSource src, CommandContext<ServerCommandSource> context, String param)
    {
        src.sendFeedback(() -> Text.literal("Sending Search Query."), false);
        //DataManager.getInstance().sendSearchQuery(param);

        return 1;
    }

    private static int inspectRoot(ServerCommandSource src, CommandContext<ServerCommandSource> context)
    {
        inspectOn = !inspectOn;

        if (inspectOn)
        {
            src.sendFeedback(() -> Text.literal("Enabled client-side inspect."), false);
        }
        else
        {
            src.sendFeedback(() -> Text.literal("Disabled client-side inspect."), false);
        }

        return 1;
    }

    private static int turnOn(ServerCommandSource src, CommandContext<ServerCommandSource> context)
    {
        inspectOn = true;
        src.sendFeedback(() -> Text.literal("Enabled client-side inspect."), false);

        return 1;
    }

    private static int turnOff(ServerCommandSource src, CommandContext<ServerCommandSource> context)
    {
        inspectOn = false;
        src.sendFeedback(() -> Text.literal("Disabled client-side inspect."), false);

        return 1;
    }
}
