import java.util.ArrayList;

public class Wonder {

	private String name;
	private Resource resource;
	private ArrayList<Resource>[] cost;
	private String[] effect;
	private Card[] built;
	private boolean used;

	public Wonder(String name, ArrayList<Resource>[] cost, String[] effect, Resource resource) {
		this.name = name;
		this.effect = effect;
		this.cost = cost;
		built = new Card[3];
		this.resource = resource;
	}
	
	public int nextBuild()
	{
		int i = 0;
		while (i < 3 && built[i] != null)
			i++;
		return i;
	}
	public ArrayList<Resource> hasResource(ArrayList<Resource> resource, int x)
	{
		ArrayList<Resource> temp = new ArrayList<>();
		for (Resource item : cost[x])
			temp.add(item);
		ArrayList<Resource> re = new ArrayList<>();
		for (Resource item : resource)
			re.add(item);
		for (int i = temp.size()-1; i >= 0; i--) {
			for (int j = 0; j < re.size(); j++) {
				if (re.get(j).contains(temp.get(i))) {
					temp.remove(i);
					re.remove(j);
					break;
				}
			}
		}
		return temp;
	}
//	public boolean canBuild(ArrayList<Resource> resource)
//	{
//		int x = 0;
//		while (x < 3 && built[x] != null)
//			x++;
////		ArrayList<Resource> temp = new ArrayList<>();
////		temp.addAll(resource);
//		char r = cost[x].get(0).getResource().get(0);
//		int j = 0;
//		for (int i = 0; i < resource.size(); i++)
//			if (resource.get(i).contains(r))
//				j++;
//		return j >= cost[x].size(); //implement trading*************
//	}
	public void build(Card c, int i)
	{
		built[i] = c;
	}

	public String getName() {
		return name;
	}

	public String getEffect(int i) {
		return effect[i];
	}
	public int getPoints()
	{
		return 0;
	}
	public Resource getResource()
	{
		return resource;
	}
	public Card[] getBuilt()
	{
		return built;
	}
	public ArrayList<Resource> getCost(int x)
	{
		return cost[x];
	}
	public void use()
	{
		used = true;
	}
	public void resetUse()
	{
		used = false;
	}
	public boolean used()
	{
		return used;
	}
}
