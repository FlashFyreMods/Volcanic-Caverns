package com.flashfyre.volcanic_caverns.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ImprovedLargeDripstoneConfig extends LargeDripstoneConfiguration {
	
	public static final Codec<ImprovedLargeDripstoneConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(config -> {
			return config.stateProvider;
		}),	Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter((config) -> {
			return config.floorToCeilingSearchRange;
		}), IntProvider.codec(1, 60).fieldOf("column_radius").forGetter((config) -> {
			return config.columnRadius;
		}), FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter((config) -> {
			return config.heightScale;
		}), Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter((config) -> {
			return config.maxColumnRadiusToCaveHeightRatio;
		}), FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter((config) -> {
			return config.stalactiteBluntness;
		}), FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter((config) -> {
			return config.stalagmiteBluntness;
		}), FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter((config) -> {
			return config.windSpeed;
		}), Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter((config) -> {
			return config.minRadiusForWind;
		}), Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter((config) -> {
			return config.minBluntnessForWind;
		})).apply(instance, ImprovedLargeDripstoneConfig::new);
	});
	
	public final BlockStateProvider stateProvider;

	public ImprovedLargeDripstoneConfig(BlockStateProvider stateProvider, int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio, 
			FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) {
		super(floorToCeilingSearchRange, columnRadius, heightScale, maxColumnRadiusToCaveHeightRatio, stalactiteBluntness, stalagmiteBluntness, windSpeed, minRadiusForWind, minBluntnessForWind);
		this.stateProvider = stateProvider;
	}
}
