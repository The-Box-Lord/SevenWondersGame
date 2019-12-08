import java.util.ArrayList;
import java.util.Collections;

public class Player {
//	instance variables
	private ArrayList<Card> hand;
	private ArrayList<Resource> resources;
	private ArrayList<Resource> tradeable;
	private ArrayList<Resource> trade;
	private Wonder wonder;
	private int coins;
	private int wins;
	private int losses;
	private int shield;
	

// default constructor
	public Player() {
		hand = new ArrayList<Card>();
		resources = new ArrayList<Resource>();
		tradeable = new ArrayList<>();
		trade = new ArrayList<Resource>();
		coins = 3;
		wins = 0;
		losses = 0;
		shield = 0;
		wonder = null;
	}
	
//	constructor, given only wonder
	public Player(Wonder w) {
		hand = new ArrayList<Card>();
		resources = new ArrayList<Resource>();
		tradeable = new ArrayList<>();
		trade = new ArrayList<Resource>();
		coins = 3;
		wins = 0;
		losses = 0;
		shield = 0;
		wonder = w;
		resources.add(wonder.getResource());
	}
//	constructor, given wonder and hand
	public Player(Wonder w, ArrayList<Card> h) {
		wins = 0;
		losses = 0;
		coins = 0;
		wonder = w;
		hand = new ArrayList<Card>();
		resources = new ArrayList<Resource>();
		trade = new ArrayList<Resource>();
		hand = h;
	}
//	constructor, given wonder, hand, resources, and trade
	public Player(Wonder w, ArrayList<Card> h, ArrayList<Resource> r, ArrayList<Resource> t) {
		wins = 0;
		losses = 0;
		coins = 0;
		wonder = w;
		hand = new ArrayList<Card>();
		resources = new ArrayList<Resource>();
		trade = new ArrayList<Resource>();
		hand = h;
		resources = r;
		trade = t;
		
	}
	
	public void setWonder(Wonder wonder)
	{
		this.wonder = wonder;
	}
	
//	might not need this method, but it was on UML
//	public void build(String name, int id) {
//		wonder = new Wonder(name, id);
//	}
//	INCOMPLETE, I DON'T UNDERSTAND ENUM
	public void useWonder(int i) {
		wonder.getEffect(i); // ERROR
	}
//	receives an integer (how many WINS to add) and adds it to total WINS
	public void addWins(int amountToAddToAdd) {
		wins += amountToAddToAdd;
	}
//	increments losses by 1
	public void addLoss() {
		losses++;
	}
//	receives how many COINS to ADD (integer), and adds it to total coins
	public void addCoins(int amountToAdd) {
		coins += amountToAdd;
	}
//	receives how many COINS to REMOVE (integer), and removes it from total coins
	public void removeCoins(int amountToRemove) {
		coins -= amountToRemove;
	}
	
	public void addShield(int s) {
		shield += s;
	}
	
//  *** accessors for int variables ***
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public int getShield() {
		return shield;
	}
	
	public int getCoins() {
		return coins;
	}
	
//  *** accessors for ArrayList variables ***
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public ArrayList<Resource> getResource() {
		return resources;
	}
	
	public ArrayList<Resource> getTrade() {
		return trade;
	}
	
//	returns the player's wonder
	public Wonder getWonder() {
		return wonder;
	}
//	public boolean canBuild(int x) {
//		ArrayList<Resource> cost = wonder.getCost(x);
//		ArrayList<Resource> temp = new ArrayList<>(resources);
//		for (int i = 0; i < cost.size(); i++) {
//			Resource c = cost.get(i);
//			int j = 0;
//			for (j = temp.size()-1; j >= 0; j--) {
//				Resource r = temp.remove(j);
//				if (r.contains(c))
//					break;
//			}
//			if (j == -1)
//				return false;
//		}
//		return true;
//	}
	public void build(Card c, int next) {
		wonder.build(c, next);
	}
//	public boolean canPlay(Card c) {
//		if (coins < c.getCoinCost())
//			return false;
//		Collections.sort(resources);
//		ArrayList<Resource> cost = c.getResourceCost();
//		int i = 0;
//		while (i < resources.size() && resources.get(i).len() < 2)
//		{
//			if (cost.contains(resources.get(i)))
//				cost.remove(resources.get(i));
//			i++;
//		}
//		if (i != resources.size())
//			while (i < resources.size()) {
//				
//			}
////		for (int i = 0; i < resources.size(); i++) {
////			if (resources.get(i).len() > 1){
////				
////			}
////			
////		}
//		if (cost.size() == 0)
//			return true;
//		
//		Collections.sort(tradeable);
//		
//		
//		
//		return true;
//	}
	public ArrayList<Resource> hasResource(Card c) {
		ArrayList<Resource> qwer = c.getResourceCost();
		ArrayList<Resource> cost = new ArrayList<>();
		for (int i = 0; i < qwer.size(); i++)
			cost.add(qwer.get(i));
		if (cost.get(0).isEmpty()) {
			cost.clear();
			return cost;
		}
		ArrayList<Resource> temp = new ArrayList<>();
		for (int i = 0; i < resources.size(); i++)
			temp.add(resources.get(i));
		for (int i = cost.size()-1; i >= 0; i--) {
			Resource r = cost.get(i);
			for (int j = 0; j < temp.size(); j++) {
				if (temp.get(j).len() > 1)
					continue;
				else if (temp.get(j).contains(r)) {
					temp.remove(j);
					cost.remove(i);
					break;
				}
			}
		}
		for (int i = cost.size()-1; i >= 0; i--) {
			Resource r = cost.get(i);
			for (int j = 0; j < temp.size(); j++) {
				if (temp.get(j).contains(r)) {
					temp.remove(j);
					cost.remove(i);
					break;
				}
			}
		}
//		int j = 0;
//		while (j < resources.size() && resources.get(j).len() < 2)
//			j++;
//		for (int i = cost.size()-1; i >= 0; i--) {
//			Resource r = cost.get(i);
//			for (int m = 0; m < j; m++) {
//				if (temp.get(m).equals(r)) {
//					temp.remove(m);
//					cost.remove(i);
//					break;
//				}
//			}
//		}
//		if (cost.isEmpty())
//			return true;
//		for (int i = cost.size()-1; i >= 0; i--) {
//			Resource r = cost.get(i);
//			for (int m = temp.size()-(resources.size()-j); m < temp.size(); m++) {
//				if (temp.get(m).equals(r)) {
//					temp.remove(m);
//					cost.remove(i);
//					break;
//				}
//			}
//		}
		return cost;
	}
//	public void wonderThing(Card c)
//	{
//		wonder.use();
//		hand.add(c);
//		c.inHand();
//		c.setOrder(hand.size());
//		String effect = c.getEffect();
//		if (effect.matches("[CSOWLGP]+.*")) {
//			if (effect.contains("/")) {
//				char[] arr = effect.toCharArray();
//				Resource r = new Resource();
//				for (int i = 0; i < arr.length; i += 2)
//					r.add(arr[i]);
//				resources.add(r);
//			}
//			else
//				for (int i = 0; i < effect.length(); i++)
//					resources.add(new Resource(effect.charAt(i)));
//		}
//		else if (effect.matches("X+")) {
//			shield += effect.length();
//		}
//	}
	public boolean playCard(Card c)
	{
//		boolean bo = false;
//		String[] a = c.getFree().split("/");
//		for (int i = 0; i < a.length; i++)
//			if (hand.contains(new Card(a[i])))
//				bo = true;
//		if (!bo) {
//			if (c.getCoinCost() > coins)
//				return false;
//			coins -= c.getCoinCost();
//			boolean hr = hasResource(c).isEmpty();
//			if (!hr)
//				return false;//add tradddinggggg*****************************
//		}
		
		hand.add(c);
		c.inHand();
		c.setOrder(hand.size());
		String effect = c.getEffect();
		if (effect.matches("[CSOWLGP]+.*")) {
			if (effect.contains("/")) {
				char[] arr = effect.toCharArray();
				Resource r = new Resource();
				for (int i = 0; i < arr.length; i += 2)
					r.add(arr[i]);
				if (c.getColor() != 5)
					r.tradable();
				resources.add(r);
			}
			else
				for (int i = 0; i < effect.length(); i++)
					resources.add(new Resource(effect.charAt(i), c.getColor() != 5));
		}
		else if (effect.matches("X+")) {
			shield += effect.length();
		}
		return true;
	}
}
