package leo.tag;

import java.util.HashMap;
import java.util.Map;

public class FiniteStateMachine {
	private Map<Integer, FiniteState> states;
	private int currentState;
	
	public FiniteStateMachine() {
		states = new HashMap<Integer, FiniteState>();
	}
	
	public void addFiniteState(int stateCode, FiniteState state) {
		states.put(Integer.valueOf(stateCode), state);
	}
	
	public FiniteState getFiniteState(int stateCode) {
		return states.get(Integer.valueOf(stateCode));
	}
	
	public FiniteState getFiniteState() {
		return getFiniteState(currentState);
	}
	
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
	
	public int getCurrentState() {
		return currentState;
	}
}
