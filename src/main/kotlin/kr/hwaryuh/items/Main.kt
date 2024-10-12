package kr.hwaryuh.items

import kr.hwaryuh.items.command.GiveCommand
import kr.hwaryuh.items.command.HoldingCommand
import kr.hwaryuh.items.command.RandomCommand
import kr.hwaryuh.items.command.ReloadCommand
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin() {
    override fun onEnable() {
        if (!File(dataFolder, "config.yml").exists()) {
            saveResource("config.yml", false)
        }
        dataFolder.resolve("items").mkdirs()

        val exampleFile = File(dataFolder.resolve("items"), "example.yml")

        getResource("example.yml")?.let { inputStream ->
            exampleFile.outputStream().use { outputStream -> inputStream.copyTo(outputStream) }
        }

        getCommand("oi.give")?.setExecutor(GiveCommand(this))
        getCommand("oi.give")?.tabCompleter = GiveCommand(this)
        getCommand("oi.reload")?.setExecutor(ReloadCommand(this))
        getCommand("oi.holding")?.setExecutor(HoldingCommand(this))
        getCommand("oi.random")?.setExecutor(RandomCommand(this))
    }

    override fun onDisable() {
    }
}