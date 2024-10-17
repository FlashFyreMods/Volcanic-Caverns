package com.flashfyre.volcanic_caverns.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class ImprovedBasaltPillarFeature extends Feature<SimpleBlockConfiguration> {
	
	public ImprovedBasaltPillarFeature(Codec<SimpleBlockConfiguration> p_65190_) {
	      super(p_65190_);
	   }

	   public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> ctx) {
	      BlockPos blockpos = ctx.origin();
	      WorldGenLevel worldgenlevel = ctx.level();
	      RandomSource r = ctx.random();
	      SimpleBlockConfiguration config = ctx.config();
	      if (worldgenlevel.isEmptyBlock(blockpos) && !worldgenlevel.isEmptyBlock(blockpos.above())) {
	         BlockPos.MutableBlockPos mpos1 = blockpos.mutable();
	         BlockPos.MutableBlockPos mpos2 = blockpos.mutable();
	         boolean flag = true;
	         boolean flag1 = true;
	         boolean flag2 = true;
	         boolean flag3 = true;

	         while(worldgenlevel.isEmptyBlock(mpos1)) {
	            if (worldgenlevel.isOutsideBuildHeight(mpos1)) {
	               return true;
	            }

	            worldgenlevel.setBlock(mpos1, config.toPlace().getState(r, mpos1), 2);
	            flag = flag && this.placeHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.NORTH), config);
	            flag1 = flag1 && this.placeHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.SOUTH), config);
	            flag2 = flag2 && this.placeHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.WEST), config);
	            flag3 = flag3 && this.placeHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.EAST), config);
	            mpos1.move(Direction.DOWN);
	         }

	         mpos1.move(Direction.UP);
	         this.placeBaseHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.NORTH), config);
	         this.placeBaseHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.SOUTH), config);
	         this.placeBaseHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.WEST), config);
	         this.placeBaseHangOff(worldgenlevel, r, mpos2.setWithOffset(mpos1, Direction.EAST), config);
	         mpos1.move(Direction.DOWN);
	         BlockPos.MutableBlockPos mpos3 = new BlockPos.MutableBlockPos();

	         for(int i = -3; i < 4; ++i) {
	            for(int j = -3; j < 4; ++j) {
	               int k = Mth.abs(i) * Mth.abs(j);
	               if (r.nextInt(10) < 10 - k) {
	                  mpos3.set(mpos1.offset(i, 0, j));
	                  int l = 3;

	                  while(worldgenlevel.isEmptyBlock(mpos2.setWithOffset(mpos3, Direction.DOWN))) {
	                     mpos3.move(Direction.DOWN);
	                     --l;
	                     if (l <= 0) {
	                        break;
	                     }
	                  }

	                  if (!worldgenlevel.isEmptyBlock(mpos2.setWithOffset(mpos3, Direction.DOWN))) {
	                     worldgenlevel.setBlock(mpos3, config.toPlace().getState(r, mpos3), 2);
	                  }
	               }
	            }
	         }

	         return true;
	      } else {
	         return false;
	      }
	   }

	   private void placeBaseHangOff(LevelAccessor level, RandomSource r, BlockPos pos, SimpleBlockConfiguration config) {
	      if (r.nextBoolean()) {
	    	  level.setBlock(pos, config.toPlace().getState(r, pos), 2);
	      }

	   }

	   private boolean placeHangOff(LevelAccessor level, RandomSource r, BlockPos pos, SimpleBlockConfiguration config) {
	      if (r.nextInt(10) != 0) {
	    	  level.setBlock(pos, config.toPlace().getState(r, pos), 2);
	         return true;
	      } else {
	         return false;
	      }
	   }

}
