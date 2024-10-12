package kr.hwaryuh.items.command

import kr.hwaryuh.items.Main
import kr.hwaryuh.items.service.OIManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HoldingCommand(private val plugin: Main) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) { return true }

        val holdItem = sender.inventory.itemInMainHand
        val ownItemsID = OIManager.getOwnItemsID(plugin, holdItem)

        sender.sendMessage(ownItemsID ?: "false")

        return true
    }
}