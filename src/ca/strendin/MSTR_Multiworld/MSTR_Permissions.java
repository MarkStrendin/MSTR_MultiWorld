package ca.strendin.MSTR_MultiWorld;

import org.bukkit.entity.Player;


public class MSTR_Permissions {
    
    public static boolean canCreateWorlds(Player player) {
    	return player.isOp();
    }
    
    
    public static boolean canTeleportToWorlds(Player player) {
        return player.isOp();
    }
    
}
