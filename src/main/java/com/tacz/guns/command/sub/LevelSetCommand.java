package com.tacz.guns.command.sub;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tacz.guns.api.item.IGun;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LevelSetCommand {
    private static final String LEVEL_SET_NAME = "level_set";
    private static final String ENTITY = "target";
    private static final String GUN_UPGRADABLE_LEVEL = "UpgradableLevel";

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        LiteralArgumentBuilder<CommandSourceStack> levelSet = Commands.literal(LEVEL_SET_NAME);
        RequiredArgumentBuilder<CommandSourceStack, EntitySelector> entities = Commands.argument(ENTITY, EntityArgument.entities());
        RequiredArgumentBuilder<CommandSourceStack, Integer> level = Commands.argument(GUN_UPGRADABLE_LEVEL, IntegerArgumentType.integer());
        levelSet.then(entities.then(level.executes(LevelSetCommand::levelSet)));
        return levelSet;
    }

    private static int levelSet(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var entities = EntityArgument.getEntities(context, ENTITY);
        int cnt = 0;
        int level = IntegerArgumentType.getInteger(context, GUN_UPGRADABLE_LEVEL);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living) {
                ItemStack stack = living.getMainHandItem();
                if (stack.getItem() instanceof IGun iGun) {
                    iGun.setUpgradableLevel(stack, level);
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
