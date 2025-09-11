package nuxlox.envowed.item;

public class ItemHelper {

    public static void initializeItems() {
        BindingItems.registerItems();
    }

    public static void initializeItemGroups() {
        EnvowedItemGroup.registerGroup();
    }

}
