package ca.strendin.MSTR_MultiWorld;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MSTR_MWCommand implements CommandExecutor {
	private static final MSTR_MultiWorld plugin = MSTR_MultiWorld.getPlugin();

	public MSTR_MWCommand(MSTR_MultiWorld mstr_MultiWorld) {}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		
		if (sender.isOp()) {		
			if (args.length > 0) {
				String subCommand = args[0];
				
				if ((subCommand.toLowerCase().contentEquals("new")) || (subCommand.toLowerCase().contentEquals("create")))  {
					handleNewCommand(sender,args);
				} else if (subCommand.toLowerCase().contentEquals("load")) {
					MSTR_Comms.sendError(sender, "Not implemented yet");										
				} else if ((subCommand.toLowerCase().contentEquals("environment")) || (subCommand.toLowerCase().contentEquals("env"))) {				
					if (MSTR_WorldHandler.hasWorkspace(sender)) {
						handleEnvironmentCommand(sender,args);						
					} else {
						MSTR_Comms.sendError(sender, "This command requires a workspace.");
						MSTR_Comms.sendInfo(sender, "To start creating a new world, type /mw create <name>");
					}
				} else if (subCommand.toLowerCase().contentEquals("name")) {				
					if (MSTR_WorldHandler.hasWorkspace(sender)) {
						MSTR_Comms.sendError(sender, "Not implemented yet");						
					} else {
						MSTR_Comms.sendError(sender, "This command requires a workspace.");
						MSTR_Comms.sendInfo(sender, "To start creating a new world, type /mw create <name>");
					}
				} else if (subCommand.toLowerCase().contentEquals("tp")) {
					handleTPCommand(sender,args);
				} else if ((subCommand.toLowerCase().contentEquals("generator")) || (subCommand.toLowerCase().contentEquals("gen"))) {				
					if (MSTR_WorldHandler.hasWorkspace(sender)) {
						handleGeneratorCommand(sender,args);					
					} else {
						MSTR_Comms.sendError(sender, "This command requires a workspace.");
						MSTR_Comms.sendInfo(sender, "To start creating a new world, type /mw create <name>");
					}
				} else if ((subCommand.toLowerCase().contentEquals("worldtype")) || (subCommand.toLowerCase().contentEquals("type"))) {				
					if (MSTR_WorldHandler.hasWorkspace(sender)) {
						handleWorldTypeCommand(sender,args);						
					} else {
						MSTR_Comms.sendError(sender, "This command requires a workspace.");
						MSTR_Comms.sendInfo(sender, "To start creating a new world, type /mw create <name>");
					}
				} else if (subCommand.toLowerCase().contentEquals("list")) {
					handleListCommand(sender);
				} else if (subCommand.toLowerCase().contentEquals("seed")) {
					if (MSTR_WorldHandler.hasWorkspace(sender)) {
						handleSeedCommand(sender,args);						
					} else {
						MSTR_Comms.sendError(sender, "This command requires a workspace.");
						MSTR_Comms.sendInfo(sender, "To start creating a new world, type /mw create <name>");
					}
				} else if (subCommand.toLowerCase().contentEquals("commit")) {				
					if (MSTR_WorldHandler.hasWorkspace(sender)) {
						handleCommitCommand(sender,args);						
					} else {
						MSTR_Comms.sendError(sender, "This command requires a workspace.");
						MSTR_Comms.sendInfo(sender, "To start creating a new world, type /mw create <name>");
					}
				} else if (subCommand.toLowerCase().contentEquals("workspace")) {				
					if (MSTR_WorldHandler.hasWorkspace(sender)) {
						MSTR_WorldHandler.sendWorkspaceInfo(sender);						
					} else {
						MSTR_Comms.sendInfo(sender, "You do not have an active workspace");
					}
				} else if (subCommand.toLowerCase().contentEquals("clear")) {
					MSTR_WorldHandler.clearWorkspace(sender);
					MSTR_Comms.sendInfo(sender, "Workspace cleared");
				} else {
					sendUsage(sender);
				}
			} else {
				sendUsage(sender);
			}
		} else {
			MSTR_Comms.sendError(sender, "You must be a server op to do that");
		}
		
		return true;
	}
	
	private void handleCommitCommand(CommandSender sender, String[] args) {
		MSTR_WorldHandler.createWorld(sender);
	}
	
	private void handleEnvironmentCommand(CommandSender sender, String[] args) {
		if (args.length > 1) {
			String input = args[1];
			
			if ((input.toLowerCase().contentEquals("normal")) || (input.toLowerCase().contentEquals("default"))) {
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s environment to","NORMAL");
				MSTR_WorldHandler.setEnvironment(sender, Environment.NORMAL);				
			} else if ((input.toLowerCase().contentEquals("nether")) || (input.toLowerCase().contentEquals("hell"))) {				
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s environment to","NETHER");
				MSTR_WorldHandler.setEnvironment(sender, Environment.NETHER);
			} else if ((input.toLowerCase().contentEquals("end")) || (input.toLowerCase().contentEquals("theend"))) {
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s environment to","THE END");
				MSTR_WorldHandler.setEnvironment(sender, Environment.THE_END);
			} else {
				MSTR_Comms.sendError(sender, "Environment \""+input+"\" not recognized");
			}
		} else {
			MSTR_Comms.sendInfo(sender, "Using default (NORMAL) environment");
			MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s environment to","NORMAL");
			// Use normal environment			
		}
		
	}
	
	
	/*
	 * default, flatland, redstoneready, space, waterworld
	 */
	private void handleGeneratorCommand(CommandSender sender, String[] args) {
		if (args.length > 1) {
			String input = args[1];
			
			if ((input.toLowerCase().contentEquals("default")) || (input.toLowerCase().contentEquals("normal"))) {
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s generator to","Normal");
				MSTR_WorldHandler.setGenerator(sender, null);
			} else if ((input.toLowerCase().contentEquals("flatland")) ||(input.toLowerCase().contentEquals("flat"))) {	
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s generator to","Flatland");
				MSTR_WorldHandler.setGenerator(sender, new WorldGenerator_Flat());
			} else if ((input.toLowerCase().contentEquals("redstone")) || (input.toLowerCase().contentEquals("redstoneready")) || (input.toLowerCase().contentEquals("redstonedream"))) {	
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s generator to","Redstone ready");
				MSTR_WorldHandler.setGenerator(sender, new WorldGenerator_RedstoneDream());	
			} else if ((input.toLowerCase().contentEquals("space")) || (input.toLowerCase().contentEquals("empty"))) {	
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s generator to","Space");
				MSTR_WorldHandler.setGenerator(sender, new WorldGenerator_Empty());		
			} else if ((input.toLowerCase().contentEquals("water")) || (input.toLowerCase().contentEquals("waterworld"))) {	
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s generator to","Water world");
				MSTR_WorldHandler.setGenerator(sender, new WorldGenerator_WaterWorld());
			} else {
				MSTR_Comms.sendError(sender, "Generator \""+input+"\" not recognized");
			}
			
		} else {
			MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s generator to","NORMAL");
			MSTR_WorldHandler.setGenerator(sender, null);
			// Use normal environment			
		}
	}
	
	private void handleWorldTypeCommand(CommandSender sender, String[] args) {
		if (args.length > 1) {
			String input = args[1];
			
			if ((input.toLowerCase().contentEquals("default")) || (input.toLowerCase().contentEquals("normal"))) {
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s world type to","Normal");
				MSTR_WorldHandler.setWorldType(sender, WorldType.NORMAL);
			} else if ((input.toLowerCase().contentEquals("large")) ||(input.toLowerCase().contentEquals("largebiomes"))) {
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s world type to","Large Biomes");
				MSTR_WorldHandler.setWorldType(sender, WorldType.LARGE_BIOMES);
			} else if ((input.toLowerCase().contentEquals("flat")) ||(input.toLowerCase().contentEquals("flatland"))) {
				MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s world type to","Flat");
				MSTR_WorldHandler.setWorldType(sender, WorldType.FLAT);
			} else {
				MSTR_Comms.sendError(sender, "World Type \""+input+"\" not recognized");
			}
			
		} else {
			MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s generator to","NORMAL");
			MSTR_WorldHandler.setWorldType(sender, WorldType.NORMAL);
			// Use normal environment			
		}
	}
	
	private void handleSeedCommand(CommandSender sender, String[] args) {
		if (args.length > 1) {
			String input = args[1];
			long inputLong = Long.parseLong(input);
			MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s seed to",Long.toString(inputLong));
			MSTR_WorldHandler.setSeed(sender, inputLong);
		} else {
			MSTR_Comms.sendValue(sender, "Setting " + MSTR_WorldHandler.getWorldNameFromWorkspace(sender) + "'s seed to a random number","");
			MSTR_WorldHandler.setSeed(sender, 0);
		}
	}
	
	private void handleNewCommand(CommandSender sender, String[] args) {
		if (!MSTR_WorldHandler.hasWorkspace(sender)) {
			if (args.length > 1) {
				String worldName = args[1];			
				
				World targetWorld = plugin.getServer().getWorld(worldName);			
				if (targetWorld == null) {
					MSTR_WorldHandler.startNewWorld(sender, worldName);
				} else {
					MSTR_Comms.sendError(sender, "A world with that name already exists");
				}			
			} else {
				MSTR_Comms.sendError(sender, "World name is required");
				sendUsage(sender);
			}		
		} else {
			MSTR_Comms.sendError(sender, "You have a world in the workspace already");
			MSTR_Comms.sendInfo(sender, "To clear your workspace, type: /mw clear");
		}
	}
	
	private void handleListCommand(CommandSender sender) {
		if (!plugin.getServer().getWorlds().isEmpty()) {
			MSTR_Comms.sendMessage(sender, "Worlds loaded on this server: ");
			for (World thisWorld : plugin.getServer().getWorlds()) {
				MSTR_Comms.sendInfo(sender, " " + thisWorld.getName());
			}
		} else {
			MSTR_Comms.sendInfo(sender, "No worlds!");
		}
	}
	
	private void handleTPCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			
			// Get name of the world the player is trying to teleport to			
			if (args.length > 1) {
				String worldName = args[1];
				
				World targetWorld = plugin.getServer().getWorld(worldName);
				
				if (targetWorld != null) {
					player.teleport(targetWorld.getSpawnLocation());
				} else {
					MSTR_Comms.sendError(player, "A world with that name does not exist");
				}
				
			} else {
				MSTR_Comms.sendError(player, "World name is required");
			}
		} else {
			MSTR_Comms.sendError(sender, "Player command only!");
		}
	}
	
	private void sendUsage(CommandSender sender) {
		MSTR_Comms.sendInfo(sender, "Usage: /mw <command>");
		MSTR_Comms.sendInfo(sender, "Commands: create, list, tp");
		MSTR_Comms.sendInfo(sender, "Example: /mw create mynewworldname");
	}

}
