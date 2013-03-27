package leo.tag;

public class ActionEventTakeInteractiveObjectFromPlayer extends ActionEvent {
	private InteractiveObject item;
	
	public ActionEventTakeInteractiveObjectFromPlayer(World world, InteractiveObject item) {
		super(world, null);
		this.item = item;
	}

	@Override
	public void update() {
		super.update();
		world.getPlayer().removeInteractiveObject(item.getUniqueId());
		Main.printf("[%s] has been removed from your inventory", item.getName());
	}
}
