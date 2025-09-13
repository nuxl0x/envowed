package nuxlox.envowed.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import nuxlox.envowed.Envowed;

import java.util.ArrayList;
import java.util.List;

public class ContractData extends PersistentState {
    private static final String DATA_NAME = "envowed_contract_data";

    // Data Fields
    private final List<String> contracts = new ArrayList<>();

    public ContractData() {
        super();
    }

    public static ContractData get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(
                ContractData::fromNbt,
                ContractData::new,
                DATA_NAME
        );
    }

    public static ContractData fromNbt(NbtCompound nbt) {
        ContractData data = new ContractData();

        if (nbt.contains("contracts", NbtElement.LIST_TYPE)) {
            NbtList contractsNbt = nbt.getList("contracts", NbtElement.STRING_TYPE);
            for (int i = 0; i < contractsNbt.size(); i++) {
                data.contracts.add(contractsNbt.getString(i));
            }
        }

        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {

        NbtList contractsNbt = new NbtList();
        for (String str : contracts) {
            contractsNbt.add(NbtString.of(str));
        }
        nbt.put("contracts", contractsNbt);

        return nbt;
    }

    public Boolean checkForExistingEntry(String entryToCheck) {
        return contracts.contains(entryToCheck);
    }

    public void createNewEntry(String entryData) {

        if (!contracts.contains(entryData)) {
            contracts.add(entryData);
            this.markDirty();
        } else {
            Envowed.LOGGER.error("Failed to create new contract entry, as entry already existed.");
        }

    }

    public void removeEntry(String entryData) {

        if (contracts.contains(entryData)) {
            contracts.remove(entryData);
            this.markDirty();
        } else {
            Envowed.LOGGER.error("Failed to remove existing contract entry, no entry found.");
        }

    }

    public List<String> getContracts() {
        return contracts;
    }

}
