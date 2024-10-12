package kr.hwaryuh.items.service

import kr.hwaryuh.items.Main
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File

object OIManager {
    private val items = mutableMapOf<String, ItemStack>()

    fun loadOwnItems(plugin: Main, ownItemsID: String): ItemStack? {
        return items[ownItemsID] ?: loadOIFromFile(plugin, ownItemsID)?.also { items[ownItemsID] = it }
    }

    private fun loadOIFromFile(plugin: Main, ownItemsID: String): ItemStack? {
        val file = File(plugin.dataFolder, "items/$ownItemsID.yml")

        if (!file.exists()) return null

        val config = YamlConfiguration.loadConfiguration(file)
        val material = Material.valueOf(config.getString("material", "IRON_SWORD")!!)
        val amount = config.getInt("amount", 1)
        val displayName = config.getString("display_name", ownItemsID)
        val lore = config.getStringList("lore")
        val customModelData = config.getInt("custom_model_data", 0)

        return ItemStack(material, amount).apply {
            itemMeta = itemMeta?.also { meta ->
                meta.setDisplayName(displayName)
                meta.lore = lore
                if (customModelData != 0) {
                    meta.setCustomModelData(customModelData)
                }
            }
        }
    }

    fun reloadOwnItems(plugin: Main) {
        items.clear()

        val itemsFolder = File(plugin.dataFolder, "items")
        itemsFolder.listFiles()?.forEach { file ->
            if (file.extension == "yml") {
                val ownItemsID = file.nameWithoutExtension
                loadOIFromFile(plugin, ownItemsID)?.let { items[ownItemsID] = it }
            }
        }
    }

    fun getOIList(plugin: Main): List<String> {
        val itemsFolder = File(plugin.dataFolder, "items")
        return itemsFolder.listFiles()
            ?.filter { it.extension == "yml" }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()
    }

    fun getOwnItemsID(plugin: Main, item: ItemStack): String? {
        val itemsFolder = File(plugin.dataFolder, "items")
        return itemsFolder.listFiles()?.find { file ->
            file.extension == "yml" && matchesFile(item, file)
        }?.nameWithoutExtension
    }

    private fun matchesFile(item: ItemStack, file: File): Boolean {
        val config = YamlConfiguration.loadConfiguration(file)
        val material = Material.valueOf(config.getString("material", "IRON_SWORD")!!)
        val displayName = config.getString("display_name")
        val lore = config.getStringList("lore")
        val customModelData = config.getInt("custom_model_data", 0)

        return item.type == material &&
                item.itemMeta?.displayName == displayName &&
                item.itemMeta?.lore == lore &&
                (customModelData == 0 || item.itemMeta?.customModelData == customModelData)
    }

    fun getRandomItemsID(plugin: Main): String? {
        if (items.isEmpty()) {
            reloadOwnItems(plugin)
        }
        return items.keys.randomOrNull()
    }
}