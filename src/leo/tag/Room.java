package leo.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Room extends InteractiveObject {
	public enum Direction { NORTH, EAST, SOUTH, WEST }
	
	private Map<Direction, Room> exits;
	private List<InteractiveObject> items;
	
	public Room(World world, String id, String name, String description) {
		super(world, id, name, description);
		exits = new HashMap<Direction, Room>();
		items = new ArrayList<InteractiveObject>();
	}
	
	public void addInteractiveObject(InteractiveObject item) {
		items.add(item);
		item.setRoom(this);
	}
	
	public void removeInteractiveObject(InteractiveObject item) {
		items.remove(item);
		item.setRoom(null);
	}
	
	public List<InteractiveObject> getAllInteractiveObjects() {
		return items;
	}
	
	public InteractiveObject hasInteractiveObject(String item) {
		if(item == null) {
			return null;
		}
		for(InteractiveObject InteractiveObject : items) {
			if(InteractiveObject.getName().toUpperCase().equals(item.toUpperCase())) {
				return InteractiveObject;
			}
		}
		Main.printf("There is no [%s] here.", item);
		return null;
	}
	
	public void addExit(Direction dir, Room room) {
		exits.put(dir, room);
	}
	
	public void removeExit(Direction dir) {
		exits.remove(dir);
	}
	
	public Room hasExit(String directionString) {
		Direction direction = null;
		try {
			direction = Direction.valueOf(directionString.toUpperCase());
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return getExit(direction);
	}
	
	public Room getExit(Direction direction) {
		return exits.get(direction);
	}
	
	public Set<Direction> getPossibleExits() {
		return exits.keySet();
	}
}
