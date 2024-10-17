package com.flashfyre.volcanic_caverns.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class LavaCavernsConfiguration extends CarverConfiguration {
	
	public static final MapCodec<LavaCavernsConfiguration> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
	      return instance.group(Codec.FLOAT.fieldOf("noise_threshold").forGetter(config -> {
	    	  return config.noiseThreshold;
	      }), Codec.FLOAT.fieldOf("xz_scale").forGetter(config -> {
	    	  return config.xzScale;
	      }), HeightProvider.CODEC.fieldOf("y").forGetter((config) -> {
	         return config.y;
	      }), FloatProvider.CODEC.fieldOf("yScale").forGetter((config) -> {
	         return config.yScale;
	      }), VerticalAnchor.CODEC.fieldOf("lava_level").forGetter((config) -> {
	         return config.lavaLevel;
	      }), CarverDebugSettings.CODEC.optionalFieldOf("debug_settings", CarverDebugSettings.DEFAULT).forGetter((config) -> {
	         return config.debugSettings;
	      }), RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("replaceable").forGetter((config) -> {
	         return config.replaceable;
	      })).apply(instance, LavaCavernsConfiguration::new);
	   });
	
	public final float noiseThreshold;
	public final float xzScale;

	public LavaCavernsConfiguration(float noiseThreshold, float xzScale, HeightProvider y, FloatProvider yScale,
			VerticalAnchor lavaLevel, CarverDebugSettings carverDebugSettings, HolderSet<Block> replaceableBlocks) {
		super(1.0F, y, yScale, lavaLevel, carverDebugSettings, replaceableBlocks);
		this.noiseThreshold = noiseThreshold;
		this.xzScale = xzScale;
	}

}
