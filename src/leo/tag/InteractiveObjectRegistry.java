package leo.tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InteractiveObjectRegistry {
	private Map<Integer, InteractiveObject> objects;
	private int nextObjectId;
	
	public InteractiveObjectRegistry() {
		objects = new HashMap<Integer, InteractiveObject>();
		nextObjectId = 0;
	}
	
	public int addInteractiveObject(InteractiveObject object) {
		objects.put(Integer.valueOf(nextObjectId), object);
		return ++nextObjectId;
	}
	
	public InteractiveObject getInteractiveObject(int id) {
		return objects.get(Integer.valueOf(id));
	}
	
	public Collection<InteractiveObject> getAllObjects() {
		return objects.values();
	}
}
