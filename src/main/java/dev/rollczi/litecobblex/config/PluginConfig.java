package dev.rollczi.litecobblex.config;

import dev.rollczi.litecobblex.cobblex.CobbleXDrop;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class PluginConfig extends OkaeriConfig {

    @Exclude
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Comment("# Wiadomość o niepowodzeniu płatności")
    public String msgPaymentFailed = "&cNie posiadasz 9x stacków cobblestone!";

    @Comment("# Wiadomość o pomyślnej stworzeniu cobbleX")
    public String msgPaymentSucessfull = "&aStworzyles cobbleX!";

    @Comment("# Wiadomość, gdy przedmioty wypadną na ziemię z powodu braku miejsca")
    public String msgDropOnGround = "&cZabraklo ci miejsca w eq itemy wypadly na ziemie";

    @Comment("# Wiadomość o dropie przedmiotu")
    public String msgDrop = "&aWypadl przedmiot: &7{item}";

    @Comment("# Wiadomość o nie wylosowaniu przedmiotu")
    public String msgNoDrop = "&cNiestety nic nie wypadlo!";

    @Comment("# Przedmiot CobbleX")
    public ItemStack cobblexItem = createCobbleXItem();

    @Comment("# Wiadomość o braku itemu w rece")
    public String msgNoItemInHand = "&cMusisz trzymac item w rece!";

    private ItemStack createCobbleXItem() {
        ItemStack item = new ItemStack(Material.MOSSY_COBBLESTONE);
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10);
        item.editMeta(itemMeta -> {
            itemMeta.displayName(MINI_MESSAGE.deserialize("<gradient:#1a266a:#27a76a>CobbleX</gradient>"));
            itemMeta.lore(List.of(
                MINI_MESSAGE.deserialize("<gray>Postaw go na ziemi, aby stworzyc cobbleX</gray>")
            ));
        });

        return item;
    }

    @Comment("# Dropy z CobbleX")
    public List<CobbleXDrop> cobblexDrops = List.of(
        new CobbleXDrop(new ItemStack(Material.DIAMOND), 0.5, "&dDiament"),
        new CobbleXDrop(new ItemStack(Material.EMERALD), 2, "<#27a76a>Smaragd</#27a76a>"),
        new CobbleXDrop(new ItemStack(Material.GOLD_INGOT), 0.3, "<gradient:#ae7f3a:#aaae47>Zloto</gradient>"),
        new CobbleXDrop(new ItemStack(Material.STONE, 32), 15, "&8Stone"),
        new CobbleXDrop(new ItemStack(Material.GRASS_BLOCK, 16), 10.0, "&aTrawa"),
        new CobbleXDrop(() -> {
            ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
            item.addUnsafeEnchantment(Enchantment.PROTECTION, 10);
            item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10);

            item.editMeta(meta -> {
                meta.displayName(MINI_MESSAGE.deserialize("<gradient:#1a266a:#27a76a>Mega miecz</gradient>"));
                meta.lore(List.of(
                    Component.empty(),
                    MINI_MESSAGE.deserialize("<gray>Mega miecz, ktory zadaje ogromne obrazenia</gray>")
                ));
            });

            return item;
            }, 5, "&cMega miecz")
    );


}
