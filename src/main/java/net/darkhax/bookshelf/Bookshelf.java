package net.darkhax.bookshelf;

import net.darkhax.bookshelf.block.BlockShelves;
import net.darkhax.bookshelf.common.ProxyCommon;
import net.darkhax.bookshelf.handler.ForgeEventHandler;
import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.darkhax.bookshelf.lib.Constants;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION)
public class Bookshelf {
    
    @SidedProxy(serverSide = Constants.PROXY_COMMON, clientSide = Constants.PROXY_CLIENT)
    public static ProxyCommon proxy;
    
    @Mod.Instance(Constants.MOD_ID)
    public static Bookshelf instance;
    
    public static SimpleNetworkWrapper network;
    public static Block blockShelf;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        network = NetworkRegistry.INSTANCE.newSimpleChannel("Bookshelf");
        
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        
        blockShelf = new BlockShelves();
        GameRegistry.registerBlock(blockShelf, ItemBlockBasic.class, "bookshelf", new Object[] { BlockShelves.types });
        
        for (int meta = 1; meta <= 5; meta++)
            GameRegistry.addShapedRecipe(new ItemStack(blockShelf, 1, meta - 1), new Object[] { "xxx", "yyy", "xxx", Character.valueOf('x'), new ItemStack(Blocks.planks, 1, meta), Character.valueOf('y'), Items.book });
            
        proxy.preInit();
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
        
        proxy.init();
    }
    
    @EventHandler
    public void onPostInit (FMLPostInitializationEvent event) {
        
        proxy.postInit();
    }
}