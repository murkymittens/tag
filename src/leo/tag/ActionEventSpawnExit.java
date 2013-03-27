package leo.tag;

public class ActionEventSpawnExit extends ActionEvent {
	private Room fromRoom;
	private Room toRoom;
	private Room.Direction direction;
	
	public ActionEventSpawnExit(World world, String description, Room fromRoom, Room toRoom,
			Room.Direction direction) {
		super(world, description);
		this.fromRoom = fromRoom;
		this.toRoom = toRoom;
		this.direction = direction;
	}
	
	@Override
	public void update() {
		super.update();
		fromRoom.addExit(direction, toRoom);
	}
}
