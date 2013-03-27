package leo.tag;

public class ActionEventNpcTalk extends ActionEvent {
	private InteractiveObject npc;
	private String conversation;
	
	public ActionEventNpcTalk(World world, InteractiveObject npc, String conversation) {
		super(world, null);
		this.npc = npc;
		this.conversation = conversation;
	}

	@Override
	public void update() {
		super.update();
		Main.printf("[%s] says: %s", npc.getName(), conversation);
	}
}
