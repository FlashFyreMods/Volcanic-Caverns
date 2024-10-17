package com.flashfyre.volcanic_caverns;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flashfyre.volcanic_caverns.data.VCWorldGenData;
import com.flashfyre.volcanic_caverns.registry.VCBiomeModifierSerializers;
import com.flashfyre.volcanic_caverns.registry.VCCarvers;
import com.flashfyre.volcanic_caverns.registry.VCFeatures;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VolcanicCaverns.MOD_ID)
public class VolcanicCaverns
{
	public static final String MOD_ID = "volcanic_caverns";
    public static final Logger LOGGER = LogManager.getLogger();
    public static VolcanicCaverns instance;
    

    public VolcanicCaverns() {
    	
    	instance = this;
    	
    	IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        
        modBus.addListener(this::gatherData);
        forgeBus.addListener(this::worldLoad);
        forgeBus.addListener(this::createWorldSpawnPosition);
        
        VCCarvers.CARVERS.register(modBus);
        VCFeatures.FEATURES.register(modBus);
        VCBiomeModifierSerializers.BIOME_MODIFIER_SERIALIZERS.register(modBus);        
    }
    
	public void worldLoad(LevelEvent.Load event) {
		setWorldSeed(event);
	}
	
	public void createWorldSpawnPosition(LevelEvent.CreateSpawnPosition event) {
		setWorldSeed(event);
	}
	
	private void setWorldSeed(LevelEvent event) {
		if(event.getLevel() instanceof ServerLevel serverLevel) {
			VCCarvers.LAVA_CAVERNS.get().setSeed(serverLevel.getSeed());
		}
	}
	
	public void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		PackOutput packOutput = generator.getPackOutput();
		generator.addProvider(event.includeClient(), new VCWorldGenData(packOutput, lookupProvider));
	}
	
	public static class TagKeys {
		public static final TagKey<Block> LAVA_CAVERNS_REPLACEABLE_BLOCKS = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "lava_caverns_carver_replaceables"));
	    public static final TagKey<Block> LAVA_SPRING_VALID = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "is_lava_spring_valid"));
	}
}
