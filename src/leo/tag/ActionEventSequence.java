package leo.tag;

import java.util.ArrayList;
import java.util.List;

public class ActionEventSequence extends ActionEvent {
	protected List<ActionEvent> events;
	protected boolean conditional = false;	
	
	public ActionEventSequence(World world) {
		super(world, null);
		events = new ArrayList<ActionEvent>();
	}
	
	public ActionEventSequence(World world, ActionEvent... events) {
		this(world);
		for(ActionEvent event : events) {
			this.events.add(event);
		}
	}
	
	public ActionEventSequence(World world, boolean conditional) {
		this(world);
		this.conditional = conditional;
	}
	
	public ActionEventSequence(World world, boolean conditional, ActionEvent... events) {
		this(world, conditional);
		for(ActionEvent event : events) {
			this.events.add(event);
		}
	}

	public void addActionEvent(ActionEvent event) {
		events.add(event);
	}
	
	public void clearActionEvents() {
		events.clear();
	}
	
	@Override
	public void update() {
		super.update();
		for(ActionEvent event : events) {
			event.setArguments(args);
			event.update();
			if(conditional && event.isConsumedAction()) {
				break;
			}
		}
		//events.clear();
	}
}
