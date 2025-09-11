package nuxlox.envowed.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class BindingItems extends ItemRegistrator {

    public static final Item CONTRACT = register("contract", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item SIGNED_CONTRACT = register("signed_contract", new Item(new FabricItemSettings().maxCount(1)));

    public static final Item CHARTER = register("charter", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item SIGNED_CHARTER = register("signed_charter", new Item(new FabricItemSettings().maxCount(1)));

    public static void initialize() {}
}
