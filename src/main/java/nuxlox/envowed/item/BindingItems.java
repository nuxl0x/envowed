package nuxlox.envowed.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class BindingItems {

    public static final Item CONTRACT = ItemHelper.register("contract", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item SIGNED_CONTRACT = ItemHelper.register("signed_contract", new Item(new FabricItemSettings().maxCount(1)));

    public static final Item CHARTER = ItemHelper.register("charter", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item SIGNED_CHARTER = ItemHelper.register("signed_charter", new Item(new FabricItemSettings().maxCount(1)));

    public static void initialize() {}
}
