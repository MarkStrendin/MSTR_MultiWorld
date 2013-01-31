package ca.strendin.MSTR_MultiWorld;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MSTR_CreateCommand implements CommandExecutor {
	private static final MSTR_MultiWorld plugin = MSTR_MultiWorld.getPlugin();

	public MSTR_CreateCommand(MSTR_MultiWorld mstr_MultiWorld) {}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		
		if (args.length > 0) {
			String worldName = args[0];
			
			World targetWorld = plugin.getServer().getWorld(worldName);
			
			if (targetWorld != null) {
				MSTR_Comms.sendError(sender, "A world with that name exists already");				
			} else {
				
				
				WorldCreator c = new WorldCreator(worldName);
				c.environment(Environment.THE_END);
				c.generator(new WorldGenerator_Empty());				
				
				World newWorld = c.createWorld();
				newWorld.setSpawnLocation(0, 129, 0);
				
				if (sender instanceof Player) {
					Player player = (Player)sender;
					player.teleport(newWorld.getBlockAt(2, 129, 2).getLocation());
				}				
			}
			
		} else {
			MSTR_Comms.sendError(sender, "World name is required");
		}
		
				
		return true;
	}

}
