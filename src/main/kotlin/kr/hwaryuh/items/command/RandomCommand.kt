package kr.hwaryuh.items.command

import kr.hwaryuh.items.Main
import kr.hwaryuh.items.service.OIManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RandomCommand(private val plugin: Main) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) { return true }

        val randomItemsID = OIManager.getRandomItemsID(plugin)

        if (randomItemsID == null) {
            sender.sendMessage("사용 가능한 아이템이 없습니다.")
            return true
        }

        val randomItem = OIManager.loadOwnItems(plugin, randomItemsID)
        if (randomItem == null) {
            sender.sendMessage("아이템을 지급하는 중 오류가 발생했습니다.")
            return true
        }

        sender.inventory.addItem(randomItem)
        sender.sendMessage("${randomItemsID}을(를) 인벤토리에 지급했습니다.")

        return true
    }
}