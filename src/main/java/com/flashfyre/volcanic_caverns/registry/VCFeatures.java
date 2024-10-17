package com.flashfyre.volcanic_caverns.registry;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;
import com.flashfyre.volcanic_caverns.feature.ImprovedBasaltPillarFeature;
import com.flashfyre.volcanic_caverns.feature.ImprovedLargeDripstoneConfig;
import com.flashfyre.volcanic_caverns.feature.ImprovedLargeDripstoneFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VCFeatures {
	
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, VolcanicCaverns.MOD_ID);
	
	public static final RegistryObject<Feature<SimpleBlockConfiguration>> PILLAR = register("pillar", new ImprovedBasaltPillarFeature(SimpleBlockConfiguration.CODEC));
	public static final RegistryObject<Feature<ImprovedLargeDripstoneConfig>> LARGE_SPIKE = register("large_spike", new ImprovedLargeDripstoneFeature(ImprovedLargeDripstoneConfig.CODEC));
	
	private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<F> register(String id, F feature) {
		return FEATURES.register(id, () -> feature);				
	}
}
