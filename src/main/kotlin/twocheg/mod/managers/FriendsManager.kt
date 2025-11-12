package twocheg.mod.managers

import net.minecraft.entity.player.PlayerEntity
import java.nio.file.Path

class FriendsManager {
    init {
        loadFriends()
    }
    companion object {
        val friendsFile: Path = Path.of(System.getProperty("user.home"), ".dl", "friends.json")

        var friends = mutableListOf<String>()
        var disables = mutableListOf<String>()
    }

    fun isFriend(name: String): Boolean {
        return friends.contains(name) && !disables.contains(name)
    }
    fun isFriend(player: PlayerEntity): Boolean {
        return isFriend(player.name.toString())
    }

    fun isDisable(name: String?): Boolean {
        return disables.contains(name)
    }

    fun addFriend(friend: String?) {
        if (!friends.contains(friend)) {
            friends.add(friend!!)
            saveFriends()
        }
    }

    fun disableFriend(name: String) {
        disables.add(name)
        saveFriends()
    }
    fun enableFriend(name: String) {
        disables.remove(name)
        saveFriends()
    }

    fun clear() {
        disables.clear()
        friends.clear()
        saveFriends()
    }

    fun saveFriends() {
        ConfigManager.writeJson(friendsFile, mutableMapOf(
            "friends" to friends,
            "disables" to disables
        ))
    }

    @Suppress("UNCHECKED_CAST")
    fun loadFriends() {
        val json = ConfigManager.readJson(friendsFile)
        friends = (json["friends"] ?: mutableListOf<String>()) as MutableList<String>
        disables = (json["disables"] ?: mutableListOf<String>()) as MutableList<String>
    }
}