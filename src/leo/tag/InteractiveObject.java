package leo.tag;

import java.util.HashMap;
import java.util.Map;

public class InteractiveObject {
	protected int uniqueId;
	protected String id;
	protected String name;
	protected String description;
	protected World world;
	protected Room room;
	protected Map<CommandInterpreter.Action, ActionEvent> actionHooks;
	protected boolean portable = true;
	protected FiniteStateMachine state;
	
	public InteractiveObject(World world, String id, String name, String description) {
		this.world = world;
		this.id = id;
		this.name = name;
		this.description = description;
		this.uniqueId = World.objectRegistry.addInteractiveObject(this);
		actionHooks = new HashMap<CommandInterpreter.Action, ActionEvent>();
	}
	
	public InteractiveObject(InteractiveObject item) {
		this(item.getWorld(), item.getId(), item.getName(), item.getDescription());
	}
	
	public void setState(FiniteStateMachine state) {
		this.state = state;
	}
	
	public FiniteStateMachine getState() {
		return state;
	}
	
	public ActionEvent onAction(CommandInterpreter.Action action) {
		ActionEvent retVal = null;
		if(state != null) {
			retVal = state.getFiniteState().getActionHooks().get(action);
		}
		if(retVal == null) {
			retVal = actionHooks.get(action);
		}
		return retVal;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public void addActionHook(CommandInterpreter.Action action, ActionEvent hook) {
		actionHooks.put(action, hook);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		if(state == null) {
			return description;
		} else {
			return state.getFiniteState().getDescription();
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isPortable() {
		return portable;
	}
	
	public void setPortable(boolean portable) {
		this.portable = portable;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Map<CommandInterpreter.Action, ActionEvent> getActionHooks() {
		return actionHooks;
	}

	public void setActionHooks(
			Map<CommandInterpreter.Action, ActionEvent> actionHooks) {
		this.actionHooks = actionHooks;
	}
}
