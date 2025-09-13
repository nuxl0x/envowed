package nuxlox.envowed.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import nuxlox.envowed.item.util.*;

public class BindingItems extends ItemRegistrator {

    public static final Item CONTRACT = register("contract", new ContractItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item SIGNED_CONTRACT = register("signed_contract", new SignedContractItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));

    public static final Item CHARTER = register("charter", new CharterItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item SIGNED_CHARTER = register("signed_charter", new SignedCharterItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));

    public static void registerItems() {}
}
