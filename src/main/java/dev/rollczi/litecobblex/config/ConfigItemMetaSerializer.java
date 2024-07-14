package dev.rollczi.litecobblex.config;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class ConfigItemMetaSerializer implements ObjectSerializer<ItemMeta> {

    private final MiniMessage miniMessage;

    public ConfigItemMetaSerializer(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    @Override
    public boolean supports(Class<? super ItemMeta> type) {
        return ItemMeta.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ItemMeta itemMeta, SerializationData data, GenericsDeclaration generics) {

        if (itemMeta.hasDisplayName()) {
            data.add("display", this.decolor(itemMeta.displayName()));
        }

        if (itemMeta.hasLore()) {
            data.addCollection("lore", this.decolor(itemMeta.lore()), String.class);
        }

        if (!itemMeta.getEnchants().isEmpty()) {
            data.addAsMap("enchantments", itemMeta.getEnchants(), Enchantment.class, Integer.class);
        }

        if (!itemMeta.getItemFlags().isEmpty()) {
            data.addCollection("flags", itemMeta.getItemFlags(), ItemFlag.class);
        }
    }

    @Override
    public ItemMeta deserialize(DeserializationData data, GenericsDeclaration generics) {

        String displayName = data.get("display", String.class);
        if (displayName == null) { // legacy
            displayName = data.get("display-name", String.class);
        }

        List<String> lore = data.containsKey("lore")
            ? data.getAsList("lore", String.class)
            : Collections.emptyList();

        Map<Enchantment, Integer> enchantments = data.containsKey("enchantments")
            ? data.getAsMap("enchantments", Enchantment.class, Integer.class)
            : Collections.emptyMap();

        List<ItemFlag> itemFlags = new ArrayList<>(data.containsKey("flags")
            ? data.getAsList("flags", ItemFlag.class)
            : Collections.emptyList());

        if (data.containsKey("item-flags")) { // legacy
            itemFlags.addAll(data.getAsList("item-flags", ItemFlag.class));
        }

        ItemMeta itemMeta = new ItemStack(Material.COBBLESTONE).getItemMeta();
        if (itemMeta == null) {
            throw new IllegalStateException("Cannot extract empty ItemMeta from COBBLESTONE");
        }

        if (displayName != null) {
            itemMeta.displayName(this.color(displayName));
        }

        itemMeta.lore(this.color(lore));

        enchantments.forEach((enchantment, level) -> itemMeta.addEnchant(enchantment, level, true));
        itemMeta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));

        return itemMeta;
    }

    private List<Component> color(List<String> lines) {
        return lines.stream()
            .map(line -> color(line))
            .toList();
    }

    private Component color(String text) {
        return this.miniMessage.deserialize(text);
    }

    private List<String> decolor(List<Component> lines) {
        return lines.stream()
            .map(line -> decolor(line))
            .toList();
    }

    private String decolor(Component text) {
        return miniMessage.serialize(text);
    }

}
