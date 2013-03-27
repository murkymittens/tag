package leo.tag;

public class ActionEventMetaTakeInteractiveObject extends ActionEventSequence {
	private InteractiveObject item;
	
	public ActionEventMetaTakeInteractiveObject(World world, InteractiveObject item) {
		super(world);
		this.item = item;
	}
	
	@Override
	public void update() {
		addActionEvent(new ActionEventDespawnInteractiveObject(world, item, item.getRoom()));
		addActionEvent(new ActionEventGiveInteractiveObjectToPlayer(world, item));
		super.update();
		clearActionEvents();
	}
}
