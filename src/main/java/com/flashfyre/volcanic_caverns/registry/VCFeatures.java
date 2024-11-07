package com.flashfyre.volcanic_caverns.registry;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;
import com.flashfyre.volcanic_caverns.feature.ImprovedBasaltPillarFeature;
import com.flashfyre.volcanic_caverns.feature.ImprovedLargeDripstoneConfig;
import com.flashfyre.volcanic_caverns.feature.ImprovedLargeDripstoneFeature;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VCFeatures {

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, VolcanicCaverns.MOD_ID);
	
	public static final Supplier<Feature<SimpleBlockConfiguration>> PILLAR = register("pillar", new ImprovedBasaltPillarFeature(SimpleBlockConfiguration.CODEC));
	public static final Supplier<Feature<ImprovedLargeDripstoneConfig>> LARGE_SPIKE = register("large_spike", new ImprovedLargeDripstoneFeature(ImprovedLargeDripstoneConfig.CODEC));
	
	private static <FC extends FeatureConfiguration, F extends Feature<FC>> Supplier<F> register(String id, F feature) {
		return FEATURES.register(id, () -> feature);
	}
}
