package com.tacz.guns.api.event.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Optional;

public class KillingSpreeMarkEvent extends Event {

    public final LivingEntity killedEntity;
    public final int newScore;

    public KillingSpreeMarkEvent(LivingEntity hurtEntity, int newScore) {
        this.killedEntity = hurtEntity;
        this.newScore = newScore;
    }
}
