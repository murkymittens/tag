package leo.tag;

public class ActionEventGiveInteractiveObjectToPlayer extends ActionEvent {
	private InteractiveObject item;
	
	public ActionEventGiveInteractiveObjectToPlayer(World world, InteractiveObject item) {
		super(world, null);
		this.item = item;
	}

	@Override
	public void update() {
		super.update();
		world.getPlayer().addInteractiveObject(item);
		Main.printf("[%s] has been added to your inventory", item.getName());
	}
}
