package com.flashfyre.volcanic_caverns.carver;

import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.flashfyre.volcanic_caverns.util.FastNoiseLite;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class LavaCavernsCarver extends WorldCarver<LavaCavernsConfiguration> {
	
	public final FastNoiseLite noise;
	public final FastNoiseLite warpNoise;

	public LavaCavernsCarver() {
		super(LavaCavernsConfiguration.CODEC.codec());
		this.noise = new FastNoiseLite();
		noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
		noise.SetFractalType(FastNoiseLite.FractalType.FBm);
		noise.SetFractalGain(0.45F);
		noise.SetFractalWeightedStrength(0.10F); // Setting this to 10 also makes really cool caves
		noise.SetFractalOctaves(3);
		noise.SetFractalLacunarity(2.5F);
		this.warpNoise = new FastNoiseLite();
		warpNoise.SetDomainWarpAmp(30F);
		warpNoise.SetFrequency(0.02F);
		warpNoise.SetFractalType(FastNoiseLite.FractalType.DomainWarpProgressive);
		warpNoise.SetFractalLacunarity(2f);
		warpNoise.SetFractalGain(0.5f);
		warpNoise.SetFractalOctaves(3);
	}

	@Override
	public boolean isStartChunk(LavaCavernsConfiguration config, RandomSource randSource) {
		return true;
	}
	
	@Override
	public boolean carve(CarvingContext ctx, LavaCavernsConfiguration config, ChunkAccess chunk,
			Function<BlockPos, Holder<Biome>> function, RandomSource randSource, Aquifer aquifer, ChunkPos chunkPos,
			CarvingMask carvingMask) {
		if(!chunk.getPos().equals(chunkPos)) return false;
		boolean flag = false;
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();
        MutableBoolean foundSurface = new MutableBoolean(false);
        for(int localX=0; localX < 16; ++localX) {
        	int x = chunkPos.getBlockX(localX);
			for(int localZ=0; localZ < 16; ++localZ) {
				int z = chunkPos.getBlockZ(localZ);
				int baseY = config.y.sample(randSource, ctx);
				int ySpread = (int) config.yScale.sample(randSource); // The vertical spread in blocks in either direction (1/2 the max height of the caverns)
				int topY = baseY + ySpread;
				int bottomY = Math.max(baseY-ySpread, ctx.getMinGenY() + 1) ;
				//VolcanicCaverns.LOGGER.info("lowest y:" + bottomY + " when local xz is x" + localX + " z" + localZ);
				for(int y = topY; y > bottomY; y--) {					
					float noiseVal = this.getNoiseVal(baseY, ySpread, x * config.xzScale, y, z * config.xzScale);
					if(noiseVal < config.noiseThreshold && !carvingMask.get(x,y,z)) {
						pos.set(localX, y, localZ);
						carvingMask.set(x, y, z);
						flag |= this.carveBlock(ctx, config, chunk, function, carvingMask, pos, checkPos, aquifer, foundSurface);
					}
				}
			}
        }
		return flag;
	}
	
	public void setSeed(long seed) {
		this.noise.SetSeed((int)seed);
		this.warpNoise.SetSeed((int)seed);
	}
	
	protected float getNoiseVal(int height, float yScale, float x, float y, float z) {
		float difference = Math.abs(y-height);
		float yVal = Math.min(1.0F, (difference*(1.0F/yScale)));
		FastNoiseLite.Vector3 pos = new FastNoiseLite.Vector3(x, y, z);
		//this.warpNoise.DomainWarp(warpedPos);
		//return Math.min(1.0F, yVal + noise.GetNoise(x, y * 0.5F, z));
		return Math.min(1.0F, yVal + noise.GetNoise(pos.x, pos.y * 0.1F, pos.z));
	}
	
	@Override
    protected boolean carveBlock(CarvingContext context, LavaCavernsConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccesor, CarvingMask carvingMask,
                               BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos,
                               Aquifer aquifer, MutableBoolean foundSurface) {
        BlockState blockState = chunk.getBlockState(pos);
        if (blockState.is(Blocks.GRASS_BLOCK) || blockState.is(Blocks.MYCELIUM)) {
            foundSurface.setTrue();
        }

        if (this.canReplaceBlock(config, blockState)) {
        	BlockState carveState = this.getCarveState(context, config, pos, aquifer);
            if (carveState == null) {
                return false;
            } else {
                chunk.setBlockState(pos, carveState, false);
                if (aquifer.shouldScheduleFluidUpdate() && !carveState.getFluidState().isEmpty()) {
                	chunk.markPosForPostprocessing(pos);
                }
                return true;
            }
        }
        return false;
    }
	
	@Nullable
    protected BlockState getCarveState(CarvingContext ctx, CarverConfiguration config, BlockPos pos, Aquifer aquifer) {
        if (pos.getY() <= config.lavaLevel.resolveY(ctx)) {
            return LAVA.createLegacyBlock();
        } else {
        	return Blocks.AIR.defaultBlockState();
        	// this aquifer stuff seems to break with a list out of range error iirc, I think it's only at very low y levels. Not sure why this doesn't happen for vanilla carvers though
        	// VolcanicCaverns.LOGGER.info("computing aquifer substance at x" + pos.getX() + " y"+ pos.getY() + " z"+ pos.getZ());
        	// return aquifer.computeSubstance(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()), 0.0F);
        }
    }
}
