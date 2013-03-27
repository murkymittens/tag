package leo.tag;

public class ActionEventSpawnInteractiveObject extends ActionEvent {
	private InteractiveObject item;
	private Room room;
	
	public ActionEventSpawnInteractiveObject(World world, InteractiveObject item, Room room) {
		super(world, null);
		this.item = item;
		this.room = room;
	}

	@Override
	public void update() {
		super.update();
		room.addInteractiveObject(item);
	}
}
