package ca.strendin.MSTR_MultiWorld;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

public class MSTR_WorldHandler {
	private static Hashtable<CommandSender, MSTR_Workspace> worldWorkspace = new Hashtable<CommandSender,MSTR_Workspace>();
	
	public static void sendWorkspaceInfo(CommandSender sender) {
		if (hasWorkspace(sender)) {
			MSTR_Workspace thisWorldCreator = worldWorkspace.get(sender);
			MSTR_Comms.sendMessage(sender, "Your workspace:");
			MSTR_Comms.sendValue(sender, " Name", thisWorldCreator.getName());
			if (thisWorldCreator.getEnvironment() != null) {
				MSTR_Comms.sendValue(sender, " Environment", thisWorldCreator.getEnvironment().name());
			} else {
				MSTR_Comms.sendValue(sender, " Environment", "Unspecified");
			}
			if (thisWorldCreator.getGenerator() != null) {
				MSTR_Comms.sendValue(sender, " Generator", thisWorldCreator.getGenerator().toString());
			} else {
				MSTR_Comms.sendValue(sender, " Generator", "Unspecified");
			}
			if (thisWorldCreator.getWorldType() != null) {
				MSTR_Comms.sendValue(sender, " World Type", "" + thisWorldCreator.getWorldType().getName());
			} else {
				MSTR_Comms.sendValue(sender, " World Type", "Unspecified");
			}
			if (thisWorldCreator.getSeed() != 0) {
				MSTR_Comms.sendValue(sender, " Seed", "" + thisWorldCreator.getSeed());
			} else {
				MSTR_Comms.sendValue(sender, " Seed", "Unspecified");
			}
			MSTR_Comms.sendMessage(sender, "");
				
			
		} else {
			MSTR_Comms.sendInfo(sender, "You do not have an active workspace");
		}
	}
	
	public static String getWorldNameFromWorkspace(CommandSender sender) {
		if (worldWorkspace.containsKey(sender)) {
			MSTR_Workspace workSpaceWC = worldWorkspace.get(sender);
			return workSpaceWC.getName();
		} else {
			return null;
		}				
	}
	
	public static long getWorldSeedFromWorkspace(CommandSender sender) {
		if (worldWorkspace.containsKey(sender)) {
			MSTR_Workspace workSpaceWC = worldWorkspace.get(sender);
			return workSpaceWC.getSeed();
		} else {
			return 0;
		}				
	}
	
	public static void loadExistingWorld(CommandSender sender, String worldName) {
		
	}
	
	public static void startNewWorld(CommandSender sender, String worldName) {				
		clearWorkspace(sender);		
		MSTR_Workspace newWorld = new MSTR_Workspace(worldName);		
		worldWorkspace.put(sender, newWorld);
		MSTR_Comms.sendInfo(sender, "");
		MSTR_Comms.sendMessage(sender, "New world \""+worldName+"\" started in your workspace");
		sendInstructions(sender);		
	}
	
	public static void setEnvironment(CommandSender sender, Environment environment) {
		MSTR_Workspace playerWorkspace = worldWorkspace.get(sender);
		playerWorkspace.setEnvironment(environment);	
		sendInstructions(sender);			
	}
	
	public static void setGenerator(CommandSender sender, ChunkGenerator gen) {
		MSTR_Workspace playerWorkspace = worldWorkspace.get(sender);
		playerWorkspace.setGenerator(gen);	
		sendInstructions(sender);			
	}
	
	public static void setSeed(CommandSender sender, long seed) {
		MSTR_Workspace playerWorkspace = worldWorkspace.get(sender);
		playerWorkspace.setSeed(seed);	
		sendInstructions(sender);			
	}
	
	public static void setWorldType(CommandSender sender, WorldType newType) {
		MSTR_Workspace playerWorkspace = worldWorkspace.get(sender);
		playerWorkspace.setWorldType(newType);	
		sendInstructions(sender);			
	}
	
	public static boolean hasWorkspace(CommandSender sender) {
		return worldWorkspace.containsKey(sender);		
	}
	
	public static void clearWorkspace(CommandSender sender) {
		if (worldWorkspace.containsKey(sender)) {
			worldWorkspace.remove(sender);
		}		
	}	
	
	public static void sendInstructions(CommandSender sender) {
		MSTR_Workspace playerWorkspace = worldWorkspace.get(sender);
		
		if (!playerWorkspace.hasSetEnvironment()) {
			MSTR_Comms.sendMessage(sender, "");
			MSTR_Comms.sendMessage(sender, "Now \""+playerWorkspace.getName()+"\" needs an environment");
			MSTR_Comms.sendInfo(sender, "Enter the command: /mw environment <environment>");
			MSTR_Comms.sendInfo(sender, " or just \"/mw environment\" to use defaults");
			MSTR_Comms.sendInfo(sender, "Valid environments: normal, nether, end");
			MSTR_Comms.sendInfo(sender, "Example: /mw environment normal");	
			MSTR_Comms.sendMessage(sender, "");		
		} else if (!playerWorkspace.hasSetGenerator()) {
			MSTR_Comms.sendMessage(sender, "");
			MSTR_Comms.sendMessage(sender, "Now you should choose a Generator for \""+playerWorkspace.getName()+"\"");
			MSTR_Comms.sendInfo(sender, "Enter the command: /mw generator <generator>");
			MSTR_Comms.sendInfo(sender, " or just \"/mw generator\" to use defaults");
			MSTR_Comms.sendInfo(sender, "Valid generators: normal, flatland, redstoneready, space, waterworld");
			MSTR_Comms.sendInfo(sender, "Example: /mw generator flatland");
			MSTR_Comms.sendMessage(sender, "");
		} else if (!playerWorkspace.hasSetWorldType()) {
			MSTR_Comms.sendMessage(sender, "");
			MSTR_Comms.sendMessage(sender, "Now \""+playerWorkspace.getName()+"\" needs a World Type");
			MSTR_Comms.sendInfo(sender, "Enter the command: /mw type <type>");
			MSTR_Comms.sendInfo(sender, " or just \"/mw type\" to use defaults");
			MSTR_Comms.sendInfo(sender, "Valid types: normal, largebiome");
			MSTR_Comms.sendInfo(sender, "Example: /mw type normal");
			MSTR_Comms.sendMessage(sender, "");			
		} else if (!playerWorkspace.hasSetSeed()) {
			MSTR_Comms.sendMessage(sender, "");
			MSTR_Comms.sendMessage(sender, "Now you may enter a seed for \""+playerWorkspace.getName()+"\"");
			MSTR_Comms.sendInfo(sender, "Enter the command: /mw seed <seed>");
			MSTR_Comms.sendInfo(sender, " or just \"/mw seed\" to use a random seed");
			MSTR_Comms.sendInfo(sender, "Seeds must be in number form");
			MSTR_Comms.sendInfo(sender, "Example: /mw seed -8042471896278289475");	
			MSTR_Comms.sendMessage(sender, "");	
			
		}
		
		
		if (
				(playerWorkspace.hasSetEnvironment()) &&
				(playerWorkspace.hasSetGenerator()) &&
				(playerWorkspace.hasSetWorldType()) &&
				(playerWorkspace.hasSetSeed())
				) {
			MSTR_Comms.sendMessage(sender, "");
			MSTR_Comms.sendMessage(sender, "\""+playerWorkspace.getName()+"\" is now ready to be created!");
			MSTR_Comms.sendMessage(sender, "");
			MSTR_Comms.sendValue(sender, "To review \""+playerWorkspace.getName()+"\"'s settings, type","/mw workspace");
			MSTR_Comms.sendValue(sender, "To generate the world, type","/mw commit");			
			MSTR_Comms.sendValue(sender, "To cancel and clear your workspace, type","/mw clear");	
			MSTR_Comms.sendMessage(sender, "");	
			
		}
		
	}
	
	public static boolean validateWorkspace(CommandSender sender) {
		MSTR_Workspace playerWorkspace = worldWorkspace.get(sender);
		
		if (!playerWorkspace.hasSetEnvironment()) {
			MSTR_Comms.sendError(sender, "You have not specified a world environment");		
		} else if (!playerWorkspace.hasSetGenerator()) {
			MSTR_Comms.sendError(sender, "You have not specified a world generator");
		} else if (!playerWorkspace.hasSetWorldType()) {
			MSTR_Comms.sendError(sender, "You have not specified a world type");
		} else if (!playerWorkspace.hasSetSeed()) {
			MSTR_Comms.sendError(sender, "You have not specified a seed");			
		}		
		
		if (
				(playerWorkspace.hasSetEnvironment()) &&
				(playerWorkspace.hasSetGenerator()) &&
				(playerWorkspace.hasSetWorldType()) &&
				(playerWorkspace.hasSetSeed())
				) {
			return true;
			
		}
		return false;		
	}
	
	
	public static boolean createWorld(CommandSender sender) {
		MSTR_Workspace playerWorkspace = worldWorkspace.get(sender);

		MSTR_Comms.sendMessage(sender, "Creating world \""+playerWorkspace.getName()+"\"...");
		MSTR_Comms.sendInfo(sender, "Note: This will lag the server while it generates the first few chunks");
		
		World newWorld = playerWorkspace.createWorld();

		MSTR_Comms.sendInfo(sender, "Done!");

		if (sender instanceof Player) {
			Player player = (Player)sender;
			MSTR_Comms.sendMessage(sender,"Teleporting you to the spawn location of \""+playerWorkspace.getName()+"\"");
			player.teleport(newWorld.getSpawnLocation());
		}		
		
		clearWorkspace(sender);
		return true;
	}
	
	
	public static String sanitizeInput(String input) {        
        String working = null;
        
        if (input.length() > 30) {
            working = input.substring(0, 30);            
        } else {
            working = input;
        }
        
        // only output alphabet characters and numbers - remove anything else
        
        String REGEX = "[^a-zA-Z0-9]";
        
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(working); // get a matcher object
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
          m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        
        working = sb.toString();
        return working;
    }

}
