package leo.tag;

public class ActionEventMovePlayer extends ActionEvent {
	private Room room;
	
	public ActionEventMovePlayer(World world, Room room, String description) {
		super(world, description);
		this.room = room;
	}

	@Override
	public void update() {
		super.update();
		world.setRoom(room);
	}
}
