package party.lemons.orebiome;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

public class BiomeOre extends Biome
{
	public BiomeOre()
	{
		super(new BiomeProperties("ore"));

		this.fillerBlock = Blocks.STONE.getDefaultState();
		this.topBlock = Blocks.STONE.getDefaultState();
	}

	@Override
	public void decorate(World world, Random rand, BlockPos pos)
	{
		Chunk chk = world.getChunkFromBlockCoords(pos);
		BlockPos bpos = new BlockPos(8 + chk.x * 16, 0, 8 + chk.z * 16);
		for(int xx = 0; xx < 16; xx++)
		{
			for(int zz = 0; zz < 16; zz++)
			{
				BlockPos pp = bpos.add(xx, 0, zz);
				if(world.getBiome(pp) == this)
				{
					for(int yy = 0; yy < world.getHeight(); yy++)
					{
						BlockPos _p = pp.add(0, yy, 0);
						if(world.getBlockState(_p) == fillerBlock)
						{
							OBMod.replaceAt(_p, world);
						}
					}
				}
			}
		}


		super.decorate(world, rand, pos);
	}
}
