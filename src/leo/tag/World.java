package leo.tag;

import java.util.HashMap;
import java.util.Map;

import leo.tag.CommandInterpreter.Action;

public class World {
	public static InteractiveObjectRegistry objectRegistry = new InteractiveObjectRegistry();
	
	private Player player;
	private Room room;
	private Map<String, Room> rooms;
	
	public World(Player player) {
		rooms = new HashMap<String, Room>();
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Room getRoom(String id) {
		return rooms.get(id);
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
		player.setRoom(room);
		Main.printf("You have entered [%s]", room.getName(), room.getDescription());
	}
	
	public void buildTestWorld() {
		Room lobby = new Room(this, "lobby", "Lobby", "This lobby smells like Pine-sol.");
		Room bathroom = new Room(this, "bathroom", "Bathroom", "The fresh aroma of human feces is in the air.");
		Room bedroom = new Room(this, "bedroom", "Bedroom", "The room looks in disarray; perhaps the aftermath of a lovers' quarrel.");
		Room pitOfDeath = new Room(this, "pitofdeath", "Pit of Death", "The walls are covered with the scratches of the damned.");
		Room kitchen = new Room(this, "kitchen", "Kitchen", "You smell brownies. They smell delicious!");
		Room garden = new Room(this, "garden", "Garden", "You can hear the chirping of birds.");
		Room heaven = new Room(this, "heaven", "Heaven", "If you're here, something is terribly wrong.");
		
		rooms.put(lobby.getId(), lobby);
		rooms.put(bathroom.getId(), bathroom);
		rooms.put(bedroom.getId(), bedroom);
		rooms.put(pitOfDeath.getId(), pitOfDeath);
		rooms.put(kitchen.getId(), kitchen);
		rooms.put(garden.getId(), garden);
		rooms.put(heaven.getId(), heaven);
		
		lobby.addExit(Room.Direction.NORTH, bedroom);
		lobby.addExit(Room.Direction.EAST, bathroom);
		lobby.addExit(Room.Direction.WEST, kitchen);
		lobby.addExit(Room.Direction.SOUTH, garden);
		bathroom.addExit(Room.Direction.WEST, lobby);
		bedroom.addExit(Room.Direction.SOUTH, lobby);
		kitchen.addExit(Room.Direction.EAST, lobby);
		garden.addExit(Room.Direction.NORTH, lobby);
		
		InteractiveObject rock = new InteractiveObject(this, "rock", "Rock", "Your average rock. It looks heavy.");
		InteractiveObject brokenMirror = new InteractiveObject(this, "bathroom_brokenmirror", "Broken Mirror Shard", "Someone's gonna have really bad luck.");
		InteractiveObject key = new InteractiveObject(this, "bathroom_key", "Key", "This key looks important.");
		InteractiveObject floor = new InteractiveObject(this, "bedroom_floor", "Floor", "The floor is blanketed with papers.");
		InteractiveObject skeleton = new InteractiveObject(this, "pitofdeath_skeleton", "Skeleton", "The remains of an adventurer, well past his prime.");
		InteractiveObject ancientDoor = new InteractiveObject(this, "pitofdeath_ancientdoor", "Ancient Door", "It's ancient, but it has a keyhole!");
		InteractiveObject rose = new InteractiveObject(this, "rose", "Rose", "It's red and it smells delightful.");
		InteractiveObject healthPotion = new InteractiveObject(this, "hppot", "Health Potion", "It's red and it looks delicious.");
		InteractiveObject healthPotionRecipe = new InteractiveObject(this, "hppotrecipe", "Health Potion Recipe", "The recipe calls for a rose.");
		InteractiveObject weed = new InteractiveObject(this, "weed", "Bag of Weed", "It's...green...");
		
		lobby.addInteractiveObject(rock);
		bathroom.addInteractiveObject(brokenMirror);
		bathroom.addInteractiveObject(key);
		bedroom.addInteractiveObject(floor);
		pitOfDeath.addInteractiveObject(skeleton);
		pitOfDeath.addInteractiveObject(ancientDoor);
		garden.addInteractiveObject(rose);
		garden.addInteractiveObject(new InteractiveObject(rose));
		garden.addInteractiveObject(healthPotionRecipe);
		heaven.addInteractiveObject(weed);
		
		Npc slowBob = new Npc(this, "slowbob", "Slow Bob", "He's staring intently at the microwave. He looks stoned...");
		kitchen.addInteractiveObject(slowBob);
		
		ancientDoor.setPortable(false);
		
		//Generic take and place actions
		for(Room room : rooms.values()) {
			for(InteractiveObject item : room.getAllInteractiveObjects()) {
				if(!item.isPortable()) {
					continue;
				}
				item.addActionHook(CommandInterpreter.Action.TAKE,
						new ActionEventMetaTakeInteractiveObject(this, item));
				item.addActionHook(CommandInterpreter.Action.PLACE,
						new ActionEventMetaPlaceInteractiveObject(this, item));
			}
		}
		
		ActionEventSequence aes = new ActionEventSequence(this);
		aes.addActionEvent(new ActionEvent(this, "You ponder for a moment whether that was a good idea..."));
		aes.addActionEvent(new ActionEventMetaTakeInteractiveObject(this, floor));
		aes.addActionEvent(new ActionEventMovePlayer(this, pitOfDeath, null));
		floor.addActionHook(CommandInterpreter.Action.TAKE, aes);
		
		aes = new ActionEventSequence(this, true);
		aes.addActionEvent(new ActionEventInteractiveObjectConditional(this, null, key,
				new ActionEventSpawnExit(this, "You use the key on the door and notice a new path has opened!",
						pitOfDeath, lobby, Room.Direction.NORTH),
				new ActionEvent(this, "Using the key didn't work.")));
		aes.addActionEvent(new ActionEventInteractiveObjectConditional(this, null, rock,
				new ActionEventSpawnExit(this, "You throw the rock at the door and notice a new path has opened!",
						pitOfDeath, lobby, Room.Direction.NORTH),
				new ActionEvent(this, "Using the rock didn't work.")));
		aes.addActionEvent(new ActionEventInteractiveObjectConditional(this, null, brokenMirror,
				new ActionEventMovePlayer(this, lobby,
						"You look into the broken mirror shard and see the lobby in the reflection."),
				new ActionEvent(this, "Using the broken mirror shard didn't work.")));
		ancientDoor.addActionHook(CommandInterpreter.Action.USE, aes);
		ancientDoor.addActionHook(CommandInterpreter.Action.TAKE, new ActionEvent(this,
				"What, in the world, do you need a door for?!"));

		aes = new ActionEventSequence(this);
		aes.addActionEvent(new ActionEventNpcTalk(this, slowBob, "Hey, man! What the hell are you doing, man?! Put me down, man!"));
		aes.addActionEvent(new ActionEventMetaTakeInteractiveObject(this, slowBob));
		slowBob.addActionHook(CommandInterpreter.Action.TAKE, aes);
		
		slowBob.addActionHook(Action.USE, new ActionEventSequence(this, true,
				new ActionEventInteractiveObjectConditional(this, null, rock,
					new ActionEventSequence(this,
						new ActionEvent(this, "You use the rock to knock Slow Bob out for a few moments..."),
						new ActionEventNpcTalk(this, slowBob, "That wasn't cool, man..."))),
				new ActionEventInteractiveObjectConditional(this, null, rose,
					new ActionEventSequence(this,
						new ActionEventMetaTakeInteractiveObject(this, weed),
						new ActionEventNpcTalk(this, slowBob, "I love you, man!")))));
		
		FiniteStateMachine fsm = new FiniteStateMachine();
		FiniteState fs;
		
		fs = new FiniteState("He looks restless.");
		fs.addActionHook(CommandInterpreter.Action.TALK,
				new ActionEventNpcTalk(this, slowBob, "I could really go for a piss right about now, man!"));
		fs.addActionHook(CommandInterpreter.Action.PLACE, new ActionEventSequence(this,
				new ActionEventMetaPlaceInteractiveObject(this, slowBob),
				new ActionEventInteractiveObjectConditional(this, null, bathroom,
						new ActionEventSequence(this,
								new ActionEventNpcTalk(this, slowBob, "Thanks! Now get out, man!"),
								new ActionEventChangeFiniteState(this, slowBob, 1)),
						new ActionEventNpcTalk(this, slowBob, "Whoa! That was quite a trip, man!"))));
		fsm.addFiniteState(0, fs);
		
		fs = new FiniteState("He's staring at something very intently...");
		fs.addActionHook(Action.TALK, new ActionEventNpcTalk(this, slowBob, "Whoa...man..."));
		fs.addActionHook(Action.PLACE, new ActionEventSequence(this,
				new ActionEventMetaPlaceInteractiveObject(this, slowBob),
				new ActionEventNpcTalk(this, slowBob, "Whoa! That was quite a trip, man!")));
		fsm.addFiniteState(1, fs);
		
		fsm.setCurrentState(0);
		slowBob.setState(fsm);
		
		CraftingRecipe recipe = new CraftingRecipe(healthPotion);
		recipe.addMaterial(rose, 1);
		CraftingRecipe.recipes.put(healthPotion.getName().toLowerCase(), recipe);
		
		//Start Game
		setRoom(lobby);
	}
}
