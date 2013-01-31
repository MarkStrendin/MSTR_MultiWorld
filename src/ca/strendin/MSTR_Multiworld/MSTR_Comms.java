package ca.strendin.MSTR_MultiWorld;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MSTR_Comms {
	public static String pluginName = "MSTR_MultiWorld";
    public static final Logger log = Logger.getLogger("Minecraft");
    private static ChatColor infoColor = ChatColor.GRAY;
    private static ChatColor valueColor = ChatColor.WHITE;
    private static ChatColor normalColor = ChatColor.AQUA;
    private static ChatColor errorColor = ChatColor.RED;
    private static ChatColor emoteColor = ChatColor.GRAY;
    private static ChatColor serverMsgColor = ChatColor.YELLOW;    

    public static void sendError(CommandSender sender, String message) {
    	if (sender instanceof Player) {
    		sender.sendMessage(errorColor + "Error: " + message);    		
    	} else {
    		logThis("Error: " + message);
    	}        
    }
    
    public static void sendBroadcast(String message) {
        for(Player thisPlayer : org.bukkit.Bukkit.getServer().getOnlinePlayers()) {
            thisPlayer.sendMessage(serverMsgColor + message);
        }
    }
    
    public static void sendEmote(Player player, String message) {
        player.sendMessage(emoteColor + message);        
    }
    
    public static void sendWorldBroadcast(World thisWorld,String message) {
        for(Player thisPlayer : thisWorld.getPlayers()) {
            thisPlayer.sendMessage(serverMsgColor + message);
        }
    }
    
    public static void sendMessage(CommandSender sender, String message) {
    	if (sender instanceof Player) {
    		sender.sendMessage(normalColor + message);    		
    	} else {
    		logThis(message);
    	}        
    }
    
    
    public static void sendValue(CommandSender sender, String valueTitle, String value) {
    	if (sender instanceof Player) {
    		sender.sendMessage(normalColor + valueTitle + ": " + valueColor + value);    		
    	} else {
    		logThis(valueTitle + ": " + value);
    	}        
    }
    
    public static void sendInfo(CommandSender sender, String message) {
    	if (sender instanceof Player) {
    		sender.sendMessage(infoColor + message);    		
    	} else {
    		logThis(message);
    	}        
    }
    
        
    public static void logThis(String message) {
        log.info("[" + pluginName + "] " + message);
    }
    
    public static void permDenyMsg(Player tothisplayer) {
        tothisplayer.sendMessage(errorColor + "You do not have permission to use that command");        
    }

    public static void sendToOps(String message) {
        for(Player thisPlayer : org.bukkit.Bukkit.getServer().getOnlinePlayers()) {
            if (thisPlayer.isOp()) {
                thisPlayer.sendMessage(serverMsgColor + "To ops: " + message);
            }        
        }        
    }    
}