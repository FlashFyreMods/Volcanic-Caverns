package com.flashfyre.volcanic_caverns.biomemodifier;

import com.flashfyre.volcanic_caverns.registry.VCBiomeModifierSerializers;
import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public record AddCarverBiomeModifier(HolderSet<Biome> biomes, Holder<ConfiguredWorldCarver<?>> carver) implements BiomeModifier {

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD && biomes.contains(biome)) {
			builder.getGenerationSettings().addCarver(Carving.AIR, carver);			
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return VCBiomeModifierSerializers.ADD_CARVER.get();
	}
}
