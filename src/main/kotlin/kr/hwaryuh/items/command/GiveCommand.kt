package kr.hwaryuh.items.command

import kr.hwaryuh.items.Main
import kr.hwaryuh.items.service.OIManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GiveCommand(private val plugin: Main) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) { return true }

        if (args.isEmpty()) {
            sender.sendMessage("/oi.give <item_name>")
            return true
        }

        val ownItemsID = args[0]
        val ownItems = OIManager.loadOwnItems(plugin, ownItemsID)

        if (ownItems == null) {
            sender.sendMessage("아이템을 찾을 수 없습니다: $ownItemsID")
            return true
        }

        sender.inventory.addItem(ownItems)
        sender.sendMessage("${ownItemsID}을(를) 인벤토리에 지급했습니다.")
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (args.size == 1) {
            val availableItems = OIManager.getOIList(plugin)
            return availableItems.filter { it.startsWith(args[0], ignoreCase = true) }
        }
        return emptyList()
    }
}