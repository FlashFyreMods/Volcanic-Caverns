package com.flashfyre.volcanic_caverns.feature;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LargeDripstoneFeature;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ImprovedLargeDripstoneFeature extends Feature<ImprovedLargeDripstoneConfig> {
	
	public ImprovedLargeDripstoneFeature(Codec<ImprovedLargeDripstoneConfig> p_159960_) {
	      super(p_159960_);
	   }

	   public boolean place(FeaturePlaceContext<ImprovedLargeDripstoneConfig> p_159967_) {
	      WorldGenLevel worldgenlevel = p_159967_.level();
	      BlockPos blockpos = p_159967_.origin();
	      ImprovedLargeDripstoneConfig config = p_159967_.config();
	      RandomSource r = p_159967_.random();
	      if (!DripstoneUtils.isEmptyOrWater(worldgenlevel, blockpos)) {
	         return false;
	      } else {
	         Optional<Column> optional = Column.scan(worldgenlevel, blockpos, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);
	         if (optional.isPresent() && optional.get() instanceof Column.Range) {
	            Column.Range column$range = (Column.Range)optional.get();
	            if (column$range.height() < 4) {
	               return false;
	            } else {
	               int i = (int)((float)column$range.height() * config.maxColumnRadiusToCaveHeightRatio);
	               int j = Mth.clamp(i, config.columnRadius.getMinValue(), config.columnRadius.getMaxValue());
	               int k = Mth.randomBetweenInclusive(r, config.columnRadius.getMinValue(), j);
	               ImprovedLargeDripstoneFeature.LargeSpike largedripstonefeature$largedripstone = makeDripstone(blockpos.atY(column$range.ceiling() - 1), config.stateProvider, false, r, k, config.stalactiteBluntness, config.heightScale);
	               ImprovedLargeDripstoneFeature.LargeSpike largedripstonefeature$largedripstone1 = makeDripstone(blockpos.atY(column$range.floor() + 1), config.stateProvider, true, r, k, config.stalagmiteBluntness, config.heightScale);
	               LargeDripstoneFeature.WindOffsetter largedripstonefeature$windoffsetter;
	               if (largedripstonefeature$largedripstone.isSuitableForWind(config) && largedripstonefeature$largedripstone1.isSuitableForWind(config)) {
	                  largedripstonefeature$windoffsetter = new LargeDripstoneFeature.WindOffsetter(blockpos.getY(), r, config.windSpeed);
	               } else {
	                  largedripstonefeature$windoffsetter = LargeDripstoneFeature.WindOffsetter.noWind();
	               }

	               boolean flag = largedripstonefeature$largedripstone.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldgenlevel, largedripstonefeature$windoffsetter);
	               boolean flag1 = largedripstonefeature$largedripstone1.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(worldgenlevel, largedripstonefeature$windoffsetter);
	               if (flag) {
	                  largedripstonefeature$largedripstone.placeBlocks(worldgenlevel, r, largedripstonefeature$windoffsetter);
	               }

	               if (flag1) {
	                  largedripstonefeature$largedripstone1.placeBlocks(worldgenlevel, r, largedripstonefeature$windoffsetter);
	               }

	               return true;
	            }
	         } else {
	            return false;
	         }
	      }
	   }

	   private static ImprovedLargeDripstoneFeature.LargeSpike makeDripstone(BlockPos p_197109_, BlockStateProvider stateProvider, boolean p_197110_, RandomSource p_197111_, int p_197112_, FloatProvider p_197113_, FloatProvider p_197114_) {
	      return new ImprovedLargeDripstoneFeature.LargeSpike(p_197109_, stateProvider, p_197110_, p_197112_, (double)p_197113_.sample(p_197111_), (double)p_197114_.sample(p_197111_));
	   }
	   
	   public static final class LargeSpike {
		      private BlockPos root;
		      private final boolean pointingUp;
		      private int radius;
		      private final double bluntness;
		      private final double scale;
		      private BlockStateProvider stateProvider;

		      public LargeSpike(BlockPos pos, BlockStateProvider stateProvider, boolean pointingUp, int radius, double bluntness, double scale) {
		         this.stateProvider = stateProvider;
		    	  this.root = pos;
		         this.pointingUp = pointingUp;
		         this.radius = radius;
		         this.bluntness = bluntness;
		         this.scale = scale;
		      }

		      private int getHeight() {
		         return this.getHeightAtRadius(0.0F);
		      }

		      public boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel p_159990_, LargeDripstoneFeature.WindOffsetter p_159991_) {
		         while(this.radius > 1) {
		            BlockPos.MutableBlockPos blockpos$mutableblockpos = this.root.mutable();
		            int i = Math.min(10, this.getHeight());

		            for(int j = 0; j < i; ++j) {
		               if (p_159990_.getBlockState(blockpos$mutableblockpos).is(Blocks.LAVA)) {
		                  return false;
		               }

		               if (DripstoneUtils.isCircleMostlyEmbeddedInStone(p_159990_, p_159991_.offset(blockpos$mutableblockpos), this.radius)) {
		                  this.root = blockpos$mutableblockpos;
		                  return true;
		               }

		               blockpos$mutableblockpos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
		            }

		            this.radius /= 2;
		         }

		         return false;
		      }

		      private int getHeightAtRadius(float p_159988_) {
		         return (int)DripstoneUtils.getDripstoneHeight((double)p_159988_, (double)this.radius, this.scale, this.bluntness);
		      }

		      public void placeBlocks(WorldGenLevel p_159993_, RandomSource rand, LargeDripstoneFeature.WindOffsetter p_159995_) {
		         for(int i = -this.radius; i <= this.radius; ++i) {
		            for(int j = -this.radius; j <= this.radius; ++j) {
		               float f = Mth.sqrt((float)(i * i + j * j));
		               if (!(f > (float)this.radius)) {
		                  int k = this.getHeightAtRadius(f);
		                  if (k > 0) {
		                     if ((double)rand.nextFloat() < 0.2D) {
		                        k = (int)((float)k * Mth.randomBetween(rand, 0.8F, 1.0F));
		                     }

		                     BlockPos.MutableBlockPos blockpos$mutableblockpos = this.root.offset(i, 0, j).mutable();
		                     boolean flag = false;
		                     int l = this.pointingUp ? p_159993_.getHeight(Heightmap.Types.WORLD_SURFACE_WG, blockpos$mutableblockpos.getX(), blockpos$mutableblockpos.getZ()) : Integer.MAX_VALUE;

		                     for(int i1 = 0; i1 < k && blockpos$mutableblockpos.getY() < l; ++i1) {
		                        BlockPos blockpos = p_159995_.offset(blockpos$mutableblockpos);
		                        if (DripstoneUtils.isEmptyOrWaterOrLava(p_159993_, blockpos)) {
		                           flag = true;
		                           p_159993_.setBlock(blockpos, this.stateProvider.getState(rand, blockpos), 2);
		                        } else if (flag && p_159993_.getBlockState(blockpos).is(BlockTags.BASE_STONE_OVERWORLD)) {
		                           break;
		                        }

		                        blockpos$mutableblockpos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
		                     }
		                  }
		               }
		            }
		         }

		      }

		      public boolean isSuitableForWind(LargeDripstoneConfiguration p_159997_) {
		         return this.radius >= p_159997_.minRadiusForWind && this.bluntness >= (double)p_159997_.minBluntnessForWind;
		      }
		   }
}
