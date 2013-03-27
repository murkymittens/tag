package leo.tag;

public class ActionEventMetaPlaceInteractiveObject extends ActionEventSequence {
	private InteractiveObject item;
	
	public ActionEventMetaPlaceInteractiveObject(World world, InteractiveObject item) {
		super(world);
		this.item = item;
	}
	
	@Override
	public void update() {
		addActionEvent(new ActionEventTakeInteractiveObjectFromPlayer(world, item));
		addActionEvent(new ActionEventSpawnInteractiveObject(world, item, world.getPlayer().getRoom()));
		super.update();
		clearActionEvents();
	}
}
