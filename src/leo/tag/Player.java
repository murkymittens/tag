package leo.tag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Player {
	private String name;
	private String description;
	private Room room;
	private Map<Integer, InteractiveObject> inventory;
	
	public Player(String name, String description) {
		this.name = name;
		this.description = description;
		inventory = new HashMap<Integer, InteractiveObject>();
	}

	public void addInteractiveObject(InteractiveObject item) {
		inventory.put(Integer.valueOf(item.getUniqueId()), item);
	}
	
	public void removeInteractiveObject(int id) {
		inventory.remove(Integer.valueOf(id));
	}
	
	public InteractiveObject getInteractiveObject(int id) {
		return inventory.get(Integer.valueOf(id));
	}
	
	public InteractiveObject hasInteractiveObject(String name) {
		for(InteractiveObject item : inventory.values()) {
			if(item.getName().equalsIgnoreCase(name)) {
				return item;
			}
		}
		return null;
	}
	
	public List<InteractiveObject> hasInteractiveObject(String name, int count) {
		List<InteractiveObject> objects = new LinkedList<InteractiveObject>();
		for(InteractiveObject item : inventory.values()) {
			if(item.getName().equalsIgnoreCase(name)) {
				objects.add(item);
			}
			if(objects.size() == count) {
				return objects;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
