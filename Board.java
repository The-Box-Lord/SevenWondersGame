import java.io.*;
import java.util.*;

public class Board {
	private int age, round; 
	private Deck deck;
	private Player[] players;
	private int[] scores;
	private int winner;
	private int playing;//current player
	private boolean won;

	public Board() throws IOException {
		//set up each variable
		
		age = round = 1;
		playing = 0;
		players = new Player[3];
		scores = new int[3];
		winner = 0;
		won = false;
		deck = new Deck();
		for(int x = 0; x < 3; x++)
			players[x] = new Player(deck.revRanWonder());
		deck.deal(age);
		
		//create the deck
	/*	
		deck = new Deck();
		//Age one
		deck.deal(1);//puts age one cards into the current
		startAge(1);
		//Age 2
		startAge(2);
		//Purple card stuff needs to be checked out
		System.out.println();
		//Age 3
		startAge(3);
		displayResults();
	*/	
		
		
	}
//	public void startAge(int i) {//advanced the age
//		
//		
//		age = i;
//		deck.deal(age);
//	}
	public ArrayList<ArrayList<Card>> getCur()
	{
		return deck.getCur();
	}
	public ArrayList<Card> getChoices()
	{
		return deck.getChoices(playing);
	}
	public int nextPlayer()
	{
		playing++;
		if (playing == 3) {
			nextRound();
			deck.rotate(age);
		}
		playing %= 3;
		return playing;
	}
	public void rotate()
	{
		deck.rotate(age);
	}
	public Player getPlayer(int i)
	{
		return players[i%3];
	}
	public int getPlaying()
	{
		return playing;
	}
	public void nextRound()
	{
		round++;
		if (round == 7) {
			for (int i = 0; i < 3; i++)
				deck.discard(deck.getChoices(i).remove(0));
			war();
			nextAge();
			round = 1;
		}
	}
	public void setWon(boolean w)
	{
		won = w;
	}
	public boolean getWon()
	{
		return won;
	}
	public void nextAge()
	{
		age++;
		if (age == 4) {
			won = true;
			calcWinner();
			return;
		}
		deck.deal(age);
		for (int i = 0; i < 3; i++)
			if (players[i].getWonder().getName().equals("olympia"))
				players[i].getWonder().resetUse();
	}
	public int getAge() {
		return age;
	}
	public void war() {
		int one = players[0].getShield();
		int two = players[1].getShield();
		int three = players[2].getShield();
		if (one - two < 0) {
			players[0].addLoss();
			players[1].addWins(age*2-1);
		}
		else if (one - two > 0) {
			players[0].addWins(age*2-1);
			players[1].addLoss();
		}
		if (one - three < 0) {
			players[0].addLoss();
			players[2].addWins(age*2-1);
		}
		else if (one - three > 0) {
			players[0].addWins(age*2-1);
			players[2].addLoss();
		}
		if (two - three < 0) {
			players[1].addLoss();
			players[2].addWins(age*2-1);
		}
		else if (two - three > 0) {
			players[1].addWins(age*2-1);
			players[2].addLoss();
		}
	}
	public void calcWinner()
	{
		for (int i = 0; i < 3; i++)
		{
			int score = 0;
			//military
			score += players[i].getWins() - players[i].getLosses();
			//coins
			score += players[i].getCoins() / 3;
			//wonder points
			Wonder w = players[i].getWonder();
			for (int j = 0; j < 3; j++)
				if (w.getEffect(j).toString().contains("{"))
					score += j*2 + 3;
			//blue cards
			ArrayList<Card> hand = players[i].getHand();
			for (int j = 0; j < hand.size(); j++)
				if (hand.get(j).getColor() == 2)
					score += hand.get(j).getEffect().toString().charAt(1) - 48;
			//science
			int max = 0;
			if (w.getName().equals("babylon"))
				if (hand.contains(new Card("scientists")))
					for (int j = 0; j < 3; j++)
						for (int k = 0; k < 3; k++)
							max = Math.max(max, getScience(j, k, i));
				else
					for (int j = 0; j < 3; j++)
						max = Math.max(max, getScience(j, 3, i));
			score += Math.max(max, getScience(3, 3, i));
			//yellow cards
			for (int j = 0; j < hand.size(); j++) {
				String name = hand.get(j).getName();
				if (name.equals("arena"))
					score += players[i].getWonder().nextBuild();
				else if (name.equals("haven")) {
					for (int k = 0; k < hand.size(); k++)
						if (hand.get(k).getColor() == 0)
							score++;
				}
				else if (name.equals("lighthouse")) {
					for (int k = 0; k < hand.size(); k++)
						if (hand.get(k).getColor() == 5)
							score++;
				}
			}
			//guild
			for (int j = 0; j < hand.size(); j++)
				if (hand.get(i).getColor() == 6)
					score += getGuild(hand.get(i), i);
			scores[i] = score;
		}
		for (int i = 0; i < 3; i++)
			if (scores[i] > scores[winner])
				winner = i;
	}
	public int getGuild(Card c, int x)
	{
		int total = 0;
		String effect = c.getEffect().toString();
		if (effect.equals("builders"))
			for (int i = 0; i < 3; i++)
				total += players[(x+i)%3].getWonder().nextBuild();
		else if (effect.equals("stategists"))
			for (int i = 1; i < 3; i++)
				total += players[(x+i)%3].getLosses();
		else if (effect.equals("shipowners"))
			for (int j = 1; j < 3; j++) {
				ArrayList<Card> hand = players[(x+1)%3].getHand();
				for (int i = 0; i < hand.size(); i++) {
					int color = hand.get(i).getColor();
					if (color == 0 || color == 1 || color == 6)
						total++;
				}
			}
		else if (!effect.equals("scientists"))
			for (int j = 1; j < 3; j++)
			{
				ArrayList<Card> hand = players[(x+1)%3].getHand();
				for (int i = 0; i < hand.size(); i++)
					if (hand.get(i).getColor() == effect.charAt(1)-48)
						total += effect.charAt(4)-48;
			}
		
		return total;
	}
	public int getScience(int s, int s2, int x)
	{
		//&-0, #-1, @-2
		int total = 0;
		int[] science = new int[3];
		if (s < 3)
			science[s]++;
		if (s2 < 3)
			science[s2]++;
		ArrayList<Card> hand = players[x].getHand();
		for (int i = 0; i < hand.size(); i++)
			if (hand.get(i).getColor() == 3) {
				char ch = hand.get(i).getEffect().toString().charAt(0);
				science[ch == '&' ? 0 : ch == '#' ? 1 : 2]++;
			}
		int set = 0;
		for (int i = 0; i < 3; i++) {
			total += science[i] * science[i];
			set = Math.min(set, science[i]);
		}
		total += set*7;
		return total;
	}
//	public int computeColorPoints(int player, int color) {
//		int pt = 0;
//		for(int x=0; x<players[x].getHand().size(); x++) {
//			if(players[player].getHand().get(x).getColor()==color) {
////				pt+=players[player].getHand().get(x).getEffect();//Add the points from a blue card
//			}else break;
//		}
//		return 0;
//	}
	public void discard(int p, Card c)
	{
		players[p].addCoins(3);
		deck.discard(c);
	}
	public void wonderThing(int p, Card c) //add the trading thingy
	{
		Player player = players[p];
		ArrayList<Card> hand = player.getHand();
		String cn = c.getName();
		if (cn.equals("vineyard")) {
			int total = 0;
			for (int i = 0; i < 3; i++) {
				hand = players[(p+1)%3].getHand();
				for (int j = 0; j < hand.size(); j++)
					if (hand.get(j).getColor() == 0)
						total++;
			}
			player.addCoins(total);
		}
		else if (cn.equals("arena")) {
			player.addCoins(3*player.getWonder().nextBuild());
		}
		else if (cn.equals("haven")) {
			for (int i = 0; i < hand.size(); i++)
				if (hand.get(i).getColor() == 0)
					player.addCoins(1);
		}
		else if (cn.equals("lighthouse")) {
			for (int i = 0; i < hand.size(); i++)
				if (hand.get(i).getColor() == 5)
					player.addCoins(1);
		}
		player.playCard(c);
	}
	public boolean trade(int p, ArrayList<Resource> cost)
	{
		boolean mart = players[p].getHand().contains(new Card("marketplace"));
		boolean east = players[p].getHand().contains(new Card("easttradingpost"));
		boolean west = players[p].getHand().contains(new Card("westtradingpost"));
		int left = 0, right = 0;
		if (east && !west) {
			//right first
			ArrayList<Resource> resource = players[(p+2)%3].getResource();
			ArrayList<Resource> res = new ArrayList<>();
			for (Resource item : resource)
				res.add(item);
			for (int i = cost.size()-1; i >= 0; i--) {
				for (int j = 0; j < res.size(); j++) {
					if (res.get(j).getTradable()) {
						if (res.get(j).contains(cost.get(i))) {
							Resource r = cost.remove(i);
							right += r.toString().matches("[CSOW]") ? 1 : mart ? 1 : 2;
							res.remove(j);
							break;
						}
					}
				}
			}
			resource = players[(p+1)%3].getResource();
			res.clear();
			for (Resource item : resource)
				res.add(item);
			for (int i = cost.size()-1; i >= 0; i--) {
				for (int j = 0; j < res.size(); j++) {
					if (res.get(j).getTradable()) {
						if (res.get(j).contains(cost.get(i))) {
							Resource r = cost.remove(i);
							left += r.toString().matches("[CSOW]") ? 2 : mart ? 1 : 2;
							res.remove(j);
							break;
						}
					}
				}
			}
		}
		else {
			ArrayList<Resource> resource = players[(p+1)%3].getResource();
			ArrayList<Resource> res = new ArrayList<>();
			for (Resource item : resource)
				res.add(item);
			for (int i = cost.size()-1; i >= 0; i--) {
				for (int j = 0; j < res.size(); j++) {
					if (res.get(j).getTradable()) {
						if (res.get(j).contains(cost.get(i))) {
							Resource r = cost.remove(i);
							left += r.toString().matches("[CSOW]") ? west ? 1 : 2 : mart ? 1 : 2;
							res.remove(j);
							break;
						}
					}
				}
			}
			resource = players[(p+2)%3].getResource();
			res.clear();
			for (Resource item : resource)
				res.add(item);
			for (int i = cost.size()-1; i >= 0; i--) {
				for (int j = 0; j < res.size(); j++) {
					if (res.get(j).getTradable()) {
						if (res.get(j).contains(cost.get(i))) {
							Resource r = cost.remove(i);
							right += r.toString().matches("[CSOW]") ? east ? 1 : 2 : mart ? 1 : 2;
							res.remove(j);
							break;
						}
					}
				}
			}
		}
		
		if (cost.isEmpty()) {
			players[p].addCoins(-1*(left + right));
			players[(p+1)%3].addCoins(left);
			players[(p+2)%3].addCoins(right);
			return true;
		}
		return false;
	}
	public boolean build(int p, Card c)
	{
		Wonder w = players[p].getWonder();
		int next = w.nextBuild();
		if (next == 3)
			return false;
		ArrayList<Resource> cost = w.hasResource(players[p].getResource(), next);
		if (!cost.isEmpty())
			if (!trade(p, cost))
				return false;
		w.build(c, next);
		return true;
	}
	public boolean playCard(int p, Card c)
	{
		Player player = players[p];
		ArrayList<Card> hand = player.getHand();
		boolean bo = false;
		String[] a = c.getFree().split("/");
		for (int i = 0; i < a.length; i++)
			if (hand.contains(new Card(a[i])))
				bo = true;
		if (!bo) {
			if (c.getCoinCost() > player.getCoins())
				return false;
			player.addCoins(-1*c.getCoinCost());
			ArrayList<Resource> cost = player.hasResource(c);
			if (!cost.isEmpty())
				if (!trade(p, cost))
					return false;
		}
		
		//everything below is for when the card can be played
		String cn = c.getName();
		if (cn.equals("vineyard")) {
			int total = 0;
			for (int i = 0; i < 3; i++) {
				hand = players[(p+1)%3].getHand();
				for (int j = 0; j < hand.size(); j++)
					if (hand.get(j).getColor() == 0)
						total++;
			}
			player.addCoins(total);
		}
		else if (cn.equals("arena")) {
			player.addCoins(3*player.getWonder().nextBuild());
		}
		else if (cn.equals("haven")) {
			for (int i = 0; i < hand.size(); i++)
				if (hand.get(i).getColor() == 0)
					player.addCoins(1);
		}
		else if (cn.equals("lighthouse")) {
			for (int i = 0; i < hand.size(); i++)
				if (hand.get(i).getColor() == 5)
					player.addCoins(1);
		}
		player.playCard(c);
		
		return true;
	}
	public Card getCard(String n)
	{
		return deck.getCard(n);
	}
	public int getWinner()
	{
		return winner;
	}
	public int[] getScores()
	{
		return scores;
	}
	public ArrayList<Card> getDiscard()
	{
		return deck.getDiscard();
	}
}
