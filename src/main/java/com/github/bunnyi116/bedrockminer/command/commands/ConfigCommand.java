package com.github.bunnyi116.bedrockminer.command.commands;

import com.github.bunnyi116.bedrockminer.I18n;
import com.github.bunnyi116.bedrockminer.command.CommandBase;
import com.github.bunnyi116.bedrockminer.config.Config;
import com.github.bunnyi116.bedrockminer.util.MessageUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class ConfigCommand extends CommandBase {

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public void build(LiteralArgumentBuilder<FabricClientCommandSource> builder) {
        builder.then(literal("limitMax")
                .executes(context -> {
                    showLimitMax();
                    return Command.SINGLE_SUCCESS;
                })
                .then(argument("count", IntegerArgumentType.integer())
                        .executes(context -> {
                            int count = IntegerArgumentType.getInteger(context, "count");
                            if (count < 1 || count > 50) {
                                MessageUtils.addMessage(I18n.COMMAND_CONFIG_LIMIT_MAX_INVALID);
                                return Command.SINGLE_SUCCESS;
                            }
                            Config.getInstance().limitMax = count;
                            Config.getInstance().save();
                            MessageUtils.addMessage(
                                    Component.literal(I18n.COMMAND_CONFIG_LIMIT_MAX_SET.getString()
                                            .replace("%count%", String.valueOf(count)))
                            );
                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }

    private void showLimitMax() {
        MessageUtils.addMessage(
                Component.literal(I18n.COMMAND_CONFIG_LIMIT_MAX_SHOW.getString()
                        .replace("%count%", String.valueOf(Config.getInstance().limitMax)))
        );
    }
}