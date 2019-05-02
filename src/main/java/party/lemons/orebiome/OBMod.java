package party.lemons.orebiome;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mod(modid = OBMod.MODID, name = OBMod.NAME, version = OBMod.VERSION)
@Mod.EventBusSubscriber(modid = OBMod.MODID)
public class OBMod
{
	public static final String MODID = "ore_biome";
	public static final String VERSION = "1.0.0";
	public static final String NAME = "Ore Biome";

	@GameRegistry.ObjectHolder("ore_biome:ore_biome")
	public static final Biome ORE_BIOME = Biomes.PLAINS;

	@SubscribeEvent
	public static void onRegisterBiome(RegistryEvent.Register<Biome> event)
	{
		Biome oreBiome = new BiomeOre().setRegistryName(MODID, "ore_biome");

		event.getRegistry().register(oreBiome);

		BiomeProvider.allowedBiomes.add(oreBiome);
		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(oreBiome, Cfg.biome_weight));
	}


	public static void replaceAt(BlockPos p, World world)
	{
		Random random = world.rand;
		if(random.nextFloat() < Cfg.replace_chance)
		{
			String val = Cfg.ores[random.nextInt(Cfg.ores.length)];

			world.setBlockState(p, getCachedState(val));
		}
	}

	public static IBlockState getCachedState(String name)
	{
		if(oreStates.containsKey(name))
			return oreStates.get(name);

		Block bl = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
		oreStates.put(name, bl.getDefaultState());

		return bl.getDefaultState();
	}

	private static Map<String, IBlockState> oreStates = new HashMap<>();

	@Config(modid = MODID)
	public static class Cfg
	{
		public static int biome_weight = 10;
		public static float replace_chance = 0.75F;

		public static String[] ores = {
				"minecraft:iron_ore",
				"minecraft:coal_ore",
				"minecraft:diamond_ore",
				"minecraft:gold_ore",
				"minecraft:emerald_ore",
				"minecraft:lapis_ore",
				"minecraft:redstone_ore"
		};
	}
}
