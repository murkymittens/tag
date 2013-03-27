package leo.tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CraftingRecipe {
	public static Map<String, CraftingRecipe> recipes = new HashMap<String, CraftingRecipe>();
	
	private Map<String, Integer> materials;
	private InteractiveObject item;
	
	public CraftingRecipe(InteractiveObject item) {
		this.item = item;
		materials = new HashMap<String, Integer>();
	}
	
	public void addMaterial(InteractiveObject item, int count) {
		materials.put(item.getName(), count);
	}
	
	public Set<Entry<String, Integer>> getMaterials() {
		return materials.entrySet();
	}
	
	public InteractiveObject craft() {
		return new InteractiveObject(item);
	}
	
	public InteractiveObject getItem() {
		return item;
	}
}
