package ca.strendin.MSTR_MultiWorld;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MSTR_LoadCommand implements CommandExecutor {
	private static final MSTR_MultiWorld plugin = MSTR_MultiWorld.getPlugin();

	public MSTR_LoadCommand(MSTR_MultiWorld mstr_MultiWorld) {}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		
		if (args.length > 0) {
			String worldName = args[0];
			
			World targetWorld = plugin.getServer().getWorld(worldName);
			
			if (targetWorld != null) {
				MSTR_Comms.sendInfo(sender, "A world with that name exists already");				
			} else {
				WorldCreator c = new WorldCreator(worldName);
				c.environment(Environment.NORMAL);
				c.seed(0);
				
				World newWorld = c.createWorld();
				
				if (sender instanceof Player) {
					Player player = (Player)sender;
					player.teleport(newWorld.getSpawnLocation());
				}				
			}
			
		} else {
			MSTR_Comms.sendInfo(sender, "World name is required");
		}
		
		return true;
	}

}
