package kr.hwaryuh.items.command

import kr.hwaryuh.items.Main
import kr.hwaryuh.items.service.OIManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ReloadCommand(private val plugin: Main) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        OIManager.reloadOwnItems(plugin)
        sender.sendMessage("플러그인을 다시 불러왔습니다.")
        return true
    }
}