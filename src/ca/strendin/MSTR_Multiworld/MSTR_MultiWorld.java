package ca.strendin.MSTR_MultiWorld;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class MSTR_MultiWorld extends JavaPlugin {
	
	public static MSTR_MultiWorld getPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MSTR_MultiWorld");
		return (MSTR_MultiWorld) plugin;		
	}
	
	@Override
    public void onDisable() {
    	MSTR_Comms.logThis("Plugin DISABLED");        
	}
	
	@Override
	public void onEnable() {
		
	    //Commands
		getCommand("mw").setExecutor(new MSTR_MWCommand(this));
		MSTR_Comms.logThis("Plugin ENABLED");        
	}
	
}
