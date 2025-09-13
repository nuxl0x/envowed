package nuxlox.envowed.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import nuxlox.envowed.Envowed;
import nuxlox.envowed.item.util.DebuggerItem;

public class ItemRegistrator {

    public static Item register(String id, Item item) {

        Identifier itemID = new Identifier(Envowed.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);

    }

    public static final Item DEBUGGER = register("debugger", new DebuggerItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

}
