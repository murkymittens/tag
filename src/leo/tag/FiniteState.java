package leo.tag;

import java.util.HashMap;
import java.util.Map;

public class FiniteState {
	private String description;
	private Map<CommandInterpreter.Action, ActionEvent> actionHooks;
	
	public FiniteState(String description) {
		this.description = description;
		actionHooks = new HashMap<CommandInterpreter.Action, ActionEvent>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addActionHook(CommandInterpreter.Action action, ActionEvent event) {
		actionHooks.put(action, event);
	}
	
	public Map<CommandInterpreter.Action, ActionEvent> getActionHooks() {
		return actionHooks;
	}

	public void setActionHooks(
			Map<CommandInterpreter.Action, ActionEvent> actionHooks) {
		this.actionHooks = actionHooks;
	}
}
