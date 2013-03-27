package leo.tag;

public class ActionEventDespawnInteractiveObject extends ActionEvent {
	private InteractiveObject item;
	private Room room;
	
	public ActionEventDespawnInteractiveObject(World world, InteractiveObject item, Room room) {
		super(world, null);
		this.item = item;
		this.room = room;
	}

	@Override
	public void update() {
		super.update();
		room.removeInteractiveObject(item);
	}
}
