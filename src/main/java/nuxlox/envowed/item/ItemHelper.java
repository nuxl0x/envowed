package nuxlox.envowed.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nuxlox.envowed.Envowed;

public class ItemHelper {

    public static Item register(String id, Item item) {

        Identifier itemID = new Identifier(Envowed.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);

    }

    public static void initializeItems() {
        BindingItems.initialize();
    }

    public static void initializeItemGroups() {
        EnvowedItemGroup.initialize();
    }

}
