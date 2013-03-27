package leo.tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Main {
	private StringBuilder sb;
	private Player player;
	private CommandInterpreter commandInterpreter;
	private World world;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		commandInterpreter = new CommandInterpreter();
		String playerName = input("What is your name? ");
		String playerDesc = input("Tell me about yourself: ");
		player = new Player(playerName, playerDesc);
		printf("And so begins the story of [%s], who describes himself as follows: [%s]",
				player.getName(), player.getDescription());
		world = new World(player);
		world.buildTestWorld();
		update();
	}
	
	public void update() {
		Command command;
		while(true) {
			printf("");
			command = commandInterpreter.parse(input("What do you do?").trim());
			if(command == null) {
				printf("Your command is invalid.");
			} else {
				switch(command.getAction()) {
				case GO:
					Room room = world.getRoom().hasExit(command.getArgument(0));
					if(room != null) {
						world.setRoom(room);
					} else {
						printf("You cannot go [%s]", command.getArgument(0));
					}
					break;
				case EXAMINE:
					InteractiveObject item = world.getRoom().hasInteractiveObject(command.getArgument(0));
					if(item != null) {
						printf("You examine [%s]. [%s]", item.getName(), item.getDescription());
					}
					break;
				case LOOK:
					printf("You look around [%s]. [%s]", world.getRoom().getName(), world.getRoom().getDescription());
					printf("You can go [%s]", concatenate(world.getRoom().getPossibleExits(), "Nowhere"));
					printf("You can see [%s]", concatenate(world.getRoom().getAllInteractiveObjects(), "Nothing"));
					break;
				case TAKE:
					item = world.getRoom().hasInteractiveObject(command.getArgument(0));
					if(item != null) {
						ActionEvent actionHook = item.onAction(CommandInterpreter.Action.TAKE);
						if(actionHook != null) {
							actionHook.update();
						} else {
							printf("You can't take [%s]", item.getName());
						}
					}
					break;
				case USE:
					InteractiveObject what = player.hasInteractiveObject(command.getArgument(0));
					InteractiveObject to = world.getRoom().hasInteractiveObject(command.getArgument(1));
					if(what != null && to != null) {
						ActionEvent actionHook = to.onAction(CommandInterpreter.Action.USE);
						if(actionHook != null) {
							printf("You try to use [%s] with [%s]", what.getName(), to.getName());
							actionHook.setArguments(what);
							actionHook.update();
						}
					}
					break;
				case TALK:
					item = world.getRoom().hasInteractiveObject(command.getArgument(0));
					if(item != null) {
						ActionEvent actionHook = item.onAction(CommandInterpreter.Action.TALK);
						if(actionHook != null) {
							actionHook.update();
						}
					}
					break;
				case PLACE:
					item = player.hasInteractiveObject(command.getArgument(0));
					if(item != null) {
						ActionEvent actionHook = item.onAction(CommandInterpreter.Action.PLACE);
						if(actionHook != null) {
							actionHook.setArguments(player.getRoom());
							actionHook.update();
						}
					}
					break;
				case CRAFT:
					String arg = command.getArgument(0);
					if(arg == null) {
						break;
					}
					CraftingRecipe recipe = CraftingRecipe.recipes.get(arg.toLowerCase());
					if(recipe != null) {
						boolean canCraft = true;
						List<InteractiveObject> craftingMaterials = new LinkedList<InteractiveObject>();
						for(Entry<String, Integer> material : recipe.getMaterials()) {
							List<InteractiveObject> playerMaterials = player.hasInteractiveObject(
								material.getKey(), material.getValue());
							if(playerMaterials != null) {
								craftingMaterials.addAll(playerMaterials);
							} else {
								canCraft = false;
								break;
							}
						}
						if(canCraft) {
							for(InteractiveObject material : craftingMaterials) {
								player.removeInteractiveObject(material.getUniqueId());
							}
							item = recipe.craft();
							player.addInteractiveObject(item);
							Main.printf("You've crafted a [%s]", item.getName());
						} else {
							Main.printf("You don't have the required items to craft [%s]", recipe.getItem().getName());
						}
					}
					break;
				default:
					printf("Your command was not understood.");
					break;
				}
			}
		}
	}
	
	public <T extends Collection<? extends Object>> String concatenate(T objectCollection,
			String noObjects) {
		if(objectCollection == null || objectCollection.size() == 0) {
			return noObjects;
		}
		sb = new StringBuilder();
		for(Object object : objectCollection) {
			sb.append(object.toString());
			sb.append(", ");
		}
		return sb.substring(0, sb.length() - 2);
	}
	
	public static String input(String prompt) {
		System.out.print(prompt + " ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void printf(String str, Object... vars) {
		System.out.printf(str + "\n", vars);
	}
}
