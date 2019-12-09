import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GUI extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -4819813465334971585L;
	
	private Board board;
	private List<Object> list = new ArrayList<>();
	private List<Object> list2 = new ArrayList<>();
	private HashMap<String, BufferedImage> cards;
	private HashMap<String, BufferedImage> wonders;
	private HashMap<String, BufferedImage> other;
	private int curPlayer;
	private boolean cardSelected;
	private int cardSelection;
	private int wonderUse;//0 - not in use, 1 - halikarnassus, 2 - olympia
	
	public GUI() throws IOException
	{
		//read in the images
		board = new Board();
		cards = new HashMap<>();
		wonders = new HashMap<>();
		other = new HashMap<>();
		curPlayer = board.getPlaying();
		cardSelected = false;
		cardSelection = 0;
		wonderUse = 0;
		
		Scanner in = new Scanner(new File("cardNames.txt"));
		while (in.hasNext()) {
			String key = in.nextLine().trim();
			cards.put(key, ImageIO.read(new File(key+".png")));
		}
		in = new Scanner(new File("wonderNames.txt"));
		while (in.hasNext()) {
			String key = in.nextLine().trim();
			wonders.put(key+"a", ImageIO.read(new File(key+"a.png")));
			wonders.put(key, ImageIO.read(new File(key+".jpg")));
		}
		Collections.addAll(list, (cards.keySet().toArray()));
		Collections.addAll(list2, (wonders.keySet().toArray()));
		
		in = new Scanner(new File("otherNames.txt"));
		while (in.hasNext()) {
			String key = in.nextLine().trim();
			other.put(key, ImageIO.read(new File(key+".png")));
		}
		
//		System.out.println("Done");
		
		
		
		
		//adding the panel to the frame and setting the size
		setPreferredSize(new Dimension(1500, 1000));
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics gr)
	{
		super.paintComponent(gr);
		BufferedImage scr = new BufferedImage(1500, 1000, BufferedImage.TYPE_INT_ARGB);
		Graphics g = scr.createGraphics();
		
		
//		try {
//			for (int r = 0; r < 3; r++)
//				for (int c = 0; c < 8; c++)
//					g.drawImage(cards.get(list.remove(0)), 180*c, 275*r, 180, 275, null);
//		} catch (Exception e) {System.out.println("Empty");}
		
		
//		try {
//			g.drawImage(wonders.get(list2.get(0)), 0, 200, 1500, 844, null);
//			g.drawImage(wonders.get(list2.get(1)), 0, 0, 750, 230, null);
//			g.drawImage(wonders.get(list2.get(0)), 750, 0, 750, 230, null);
//		} catch (Exception e) {System.out.println("Empty");}
//		try {
//			for (int x = 0; x < 7; x++)
//				for (int i = 0; i < 6; i++)
//					g.drawImage(cards.get(list.get((int) (list.size()*Math.random()))), x*300+50+i*30, 260+i*50, 144, 220, null);
////			for (int i = 0; i < 7; i++)
////				g.drawImage(cards.get(list.remove((int) (list.size()*Math.random()))), 50+i*30, 250+i*50, 144, 220, null);
////			g.drawImage(cards.get(list.remove(0)), 80, 300, 144, 220, null);
//		} catch (Exception e) {System.out.println("ERROR");}
		
//		5+ 300, other 30 from end
		
//		Player cur = board.getPlayer();
//		ArrayList<Card> hand = cur.getHand();
//		Collections.sort(hand);
		
		
		curPlayer %= 3;
		
//		g.drawImage(wonders.get(board.getPlayer(curPlayer).getWonder().getName()+"a"), 0, 230, 1000, 300, null);
		g.drawImage(wonders.get(board.getPlayer(curPlayer+1).getWonder().getName()), 0, 0, 750, 220, null);
		g.drawImage(wonders.get(board.getPlayer(curPlayer+2).getWonder().getName()), 750, 0, 750, 220, null);
		g.drawImage(wonders.get(board.getPlayer(curPlayer).getWonder().getName()), 0, 200, 1500, 844, null);
		
		
		//display current player info
		Player cp = board.getPlayer(curPlayer);
		String wn = cp.getWonder().getName();
		Card[] ca = cp.getWonder().getBuilt();
		for (int i = 0; i < 3; i++)
			if (ca[i] != null)
				g.drawImage(other.get("age"+ca[i].getAge()), 452+i*222, 635, 144, 220, null);
//				g.drawImage(other.get("age"+ca[i].getAge()), 452+i*222, 665, 144, 220, null);//different height
			else break;
//		g.drawImage(other.get("age1"), 452+0*222, 635, 144, 220, null);//testing card height behind wonder
		g.drawImage(other.get(wn+1), 422, 688, 212, 62, null);
		g.drawImage(other.get(wn+2), 644, 688, 212, 62, null);
		g.drawImage(other.get(wn+3), 866, 688, 212, 62, null);
		
		
		g.setColor(Color.gray);
//		g.setColor(new Color(Color.gray.getRed(), Color.gray.getGreen(), Color.gray.getBlue(), 222));
		g.fillRect(0, 750, 1500, 250);
		if (board.getAge() < 4)
			g.drawImage(other.get("age"+board.getAge()), 10, 770, 144, 220, null);
//		g.drawImage(cards.get("arena"), 10, 770, 144, 220, null);//Used to find card*************
		g.setColor(Color.black);
		g.fillRoundRect(1405, 810, 80, 80, 80, 80);
		g.drawImage(other.get(cp.getWonder().getResource().toString()), 1410, 815, 70, 70, null);
		g.setColor(Color.darkGray);
		g.fillRoundRect(1405, 900, 80, 80, 80, 80);//using the wonder and displaying the pyramid
		//using wonder and stuff *********
		if (curPlayer == board.getPlaying() && wn.equals("halikarnassus") || wn.equals("olympia")) {
			if (cp.getWonder().nextBuild() > 1) {
				if (cp.getWonder().used()) {
					g.drawImage(other.get("wonder0"), 1411, 910, 70, 58, null);
				}
				else {
					g.drawImage(other.get("wonder1"), 1411, 910, 70, 58, null);
					if (wonderUse != 0)
						g.drawImage(other.get("check"), 1451, 900, 40, 31, null);
				}
			}
//			g.drawImage(other.get("check"), 1451, 900, 40, 31, null);//look where check is
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Papyrus", Font.BOLD, 16));
		g.drawString(String.format("Player %d", curPlayer+1).toUpperCase(), 1290, 770);
		g.drawString(wn.toUpperCase(), 1290, 790);
		
		g.setFont(new Font("Papyrus", Font.BOLD, 25));
		g.drawImage(other.get("coin"), 1290, 805, 50, 50, null);
		g.drawString(": "+cp.getCoins(), 1350, 835);
		g.drawImage(other.get("victory"), 1295, 865, 40, 50, null);
		g.drawString(": "+cp.getWins(), 1350, 895);
		g.drawImage(other.get("victoryminus"), 1292, 925, 50, 50, null);
		g.drawString(": "+cp.getLosses(), 1350, 955);
		
		//display other info for top of screen
		//left
		cp = board.getPlayer(curPlayer+1);
		wn = cp.getWonder().getName();
		g.setFont(new Font("Papyrus", Font.BOLD, 16));
		g.drawString(String.format("Player %d", (curPlayer+1)%3+1).toUpperCase(), 5, 170);
		g.drawString(wn.toUpperCase(), 5, 190);
		
		g.setFont(new Font("Papyrus", Font.BOLD, 25));
		g.drawImage(other.get("coin"), 215, 15, 50, 50, null);
		g.drawString(": "+cp.getCoins(), 275, 45);
		g.drawImage(other.get("victory"), 220, 75, 40, 50, null);
		g.drawString(": "+cp.getWins(), 275, 105);
		g.drawImage(other.get("victoryminus"), 217, 135, 50, 50, null);
		g.drawString(": "+cp.getLosses(), 275, 165);
		
		g.setColor(Color.black);
		g.fillRoundRect(20, 60, 80, 80, 80, 80);
		g.drawImage(other.get(cp.getWonder().getResource().toString()), 25, 65, 70, 70, null);
		
		//right
		cp = board.getPlayer(curPlayer+2);
		wn = cp.getWonder().getName();
		g.setColor(Color.white);
		g.setFont(new Font("Papyrus", Font.BOLD, 16));
		g.drawString(String.format("Player %d", (curPlayer+2)%3+1).toUpperCase(), 755, 170);
		g.drawString(wn.toUpperCase(), 755, 190);

		g.setFont(new Font("Papyrus", Font.BOLD, 25));
		g.drawImage(other.get("coin"), 965, 15, 50, 50, null);
		g.drawString(": "+cp.getCoins(), 1025, 45);
		g.drawImage(other.get("victory"), 970, 75, 40, 50, null);
		g.drawString(": "+cp.getWins(), 1025, 105);
		g.drawImage(other.get("victoryminus"), 967, 135, 50, 50, null);
		g.drawString(": "+cp.getLosses(), 1025, 165);
		
		g.setColor(Color.black);
		g.fillRoundRect(770, 60, 80, 80, 80, 80);
		g.drawImage(other.get(cp.getWonder().getResource().toString()), 775, 65, 70, 70, null);
		

//		try {
//			for (int x = 0; x < 7; x++)
//				for (int i = 0; i < 6; i++)
//					g.drawImage(cards.get(list.get((int) (list.size()*Math.random()))), x*300+50+i*30, 260+i*50, 144, 220, null);
////			for (int i = 0; i < 7; i++)
////				g.drawImage(cards.get(list.remove((int) (list.size()*Math.random()))), 50+i*30, 250+i*50, 144, 220, null);
////			g.drawImage(cards.get(list.remove(0)), 80, 300, 144, 220, null);
//		} catch (Exception e) {System.out.println("ERROR");}
		
		
		
		Player player = board.getPlayer(curPlayer);
		ArrayList<Card> hand = player.getHand();
		
//		hand.clear();
//		for (int i = 0; i < 20; i++) {
//			String s = (String) list.get((int) (list.size()*Math.random()));
////			System.out.println(s);
//			Card c = board.getCard(s);
////			System.out.println(c);
////			Card c = new Card(s);
////			if (!hand.contains(c)) {
//				hand.add(c);
////				i++;
////			}
////			i--;
//		}
////		System.out.println(hand.size());
		
		//listing out discard pile
//		ArrayList<Card> discard = board.getDiscard();
//		discard.clear();
//		for (int i = 0; i < 20; i++)
//			discard.add(new Card((String) list.get((int) (Math.random()*list.size())) ));
//		
//		int row = 0;
//		for (int i = 0; i < discard.size(); i++) {
//			if (i != 0 && i % 10 == 0)
//				row++;
//			g.drawImage(cards.get(discard.get(i).getName()), 5+(i%10)*149, 210+row*230, 144, 220, null);
//		}
		if (wonderUse == 1) {
			ArrayList<Card> discard = board.getDiscard();
			int row = 0;
			for (int i = 0; i < discard.size(); i++) {
				if (i != 0 && i % 10 == 0)
					row++;
				g.drawImage(cards.get(discard.get(i).getName()), 5+(i%10)*149, 210+row*230, 144, 220, null);
			}
		}
		else if (hand.size() > 0) {
			Collections.sort(hand);
			BufferedImage hand_image = new BufferedImage(1500, 1000, BufferedImage.TYPE_INT_ARGB);
			Graphics the_hand = hand_image.createGraphics();
			int groups = 1, tempSize = 0, tempColor = hand.get(0).getColor();
			ArrayList<Integer> indexSplit = new ArrayList<>();
			indexSplit.add(0);
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getColor() == 0 || hand.get(i).getColor() == 1);
				else
					if (tempColor != hand.get(i).getColor()) {
						tempColor = hand.get(i).getColor();
						if (!indexSplit.contains(i)) {
							indexSplit.add(i);
							groups++;
						}
						tempSize = 0;
					}
				tempSize++;
				if (tempSize == 6) {
					groups++;
					tempSize = 0;
					indexSplit.add(i+1);
				}
			}
			if (!indexSplit.contains(hand.size()))
				indexSplit.add(hand.size());
			indexSplit.add(hand.size());
			int h = 0;
			int px = 0;
			for (int i = 0; i < groups; i++) {
				int f = indexSplit.get(i), b = indexSplit.get(i+1);
				for (int j = 0; j < b-f; j++) {
					the_hand.drawImage(cards.get(hand.get(h++).getName()), px+j*30, j*50, 144, 220, null);
				}
				px += (b-f-1)*30+15+144;
			}
			g.drawImage(hand_image, 750-(px-15)/2, 200, 1500, 1000, null);
		}
		
		//displaying the current player's playable hands
		if (curPlayer == board.getPlaying() && wonderUse != 1) {
			ArrayList<Card> cur = board.getChoices();
			int sep = 10;
			int size = cur.size();
			int beginning = 750 - (144*size+sep*(size-1))/2;
			for (int i = 0; i < size; i++)
				g.drawImage(cards.get(cur.get(i).getName()), beginning+i*(144+sep), 770, 144, 220, null);
			if (cardSelected) {
				g.drawImage(other.get("play"), beginning+cardSelection*(144+sep)+50, 830, 66, 48, null);
				g.drawImage(other.get("wonder1"), beginning+cardSelection*(144+sep)+55, 880, 57, 48, null);
				g.drawImage(other.get("discard"), beginning+cardSelection*(144+sep)+65, 935, 36, 45, null);
			}
		}
		
		
//		g.setColor(Color.white);
//		g.fillRect(0, 0, 1500, 1000);
//		g.setFont(new Font("Papyrus", 0, 50));
//		g.setColor(Color.RED);
//		g.drawString("Olympiad", 100, 100);
		
//		try {
//			String s = (String) list.remove(0);
////			g.drawImage(cards.get(s), 10, 10, 180, 275, null);
//			g.drawImage(wonders.get(s), 10, 10, 1250, 500, null);
//		} catch (Exception e) {System.out.println("Empty");}
		
		
		//display guild cards
//		String[] tempArr = {"builders", "craftsmens", "magistrates", "philosophers", "scientists", "shipowners", "spies", "strategists", "traders", "workers"};
//		for (int i = 0; i < 10; i++)
//			g.drawImage(cards.get(tempArr[i]), i*144, 200, 144, 200, null);
		
		if (board.getWon()) {
			g.drawImage(other.get("background"), 0, 0, 1510, 1000, null);
			g.setFont(new Font("Papyrus", Font.BOLD, 100));
			g.setColor(Color.black);
//			int[] scores = board.getScores();
			int[] scores = {100, 10, 1};
			TreeMap<Integer, Integer> map = new TreeMap<>();
			for (int i = 0; i < 3; i++)
				map.put(scores[i], i);
//			int px = 0, pn = 0;
//			for (int i = 0; i < 3; i++) {
//				if (scores[i] > scores[px])
//					px = i;
//				if (scores[i] < scores[pn])
//					pn = i;
//			}
//			int[] players = {px, 3-px-pn, pn};
//			System.out.println(Arrays.toString(players));
			Iterator<Integer> iter = map.keySet().iterator();
			int t = iter.next();
			int s = iter.next();
			int f = iter.next();
			g.drawString(String.format("1st Player %d  Score: %d", map.get(f)+1, f), 50, 200);
			g.drawString(String.format("2nd Player %d  Score: %d", map.get(s)+1, s), 50, 300);
			g.drawString(String.format("3rd Player %d  Score: %d", map.get(t)+1, t), 50, 400);
//			g.drawImage(wonders.get(board.getPlayer(board.getWinner()).getWonder().getName()), 0, 78, 1500, 844, null);
		}
		
		
		
		g.dispose();
		int width = getWidth();
		int height = getHeight();
		if (width >= height * 3 / 2)
		{
			gr.setColor(Color.BLACK);
			gr.fillRect(0, 0, width, height);
			gr.drawImage(scr, (int)(width / 2 - height * 3 / 4.0), 0, (int)(height * 3 / 2.0), height, null);
		}
		else
		{
			gr.setColor(Color.BLACK);
			gr.fillRect(0, 0, width, height);
			gr.drawImage(scr, 0, (int)(height / 2.0 - width * 2 / 6.0), width, (int)(width * 2 / 3.0), null);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (board.getWon()) {
			board.setWon(false);
			repaint();
			return;
		}
		int x = e.getX();
		int y = e.getY();
		int width = getWidth();
		int height = getHeight();
		if (width >= height * 3 / 2) {
			double xFactor = height*3/2.0/1500.0;
			double yFactor = height/1000.0;
			int start = (int)(width / 2 - height * 3 / 4.0);
			x -= start;
			x /= xFactor;
			y /= yFactor;
		}
		else {
			double xFactor = width/1500.0;
			double yFactor = width*2/3/1000.0;
			int start = (int)(height / 2.0 - width * 2 / 6.0);
			x /= xFactor;
			y -= start;
			y /= yFactor;
		}
		//clicks on top of screen to switch board looks, main board it in curPlayer
		if (0 <= y && y < 200 && 0 <= x && x < 1500) {
			curPlayer++;
			if (750 <= x)
				curPlayer++;
			curPlayer %= 3;
			cardSelected = false;
			wonderUse = 0;
			repaint();
			return;
		}

		if (board.getAge() == 4)
			return;
		
		//picking cards at bottom
		if (wonderUse == 1) {
			ArrayList<Card> discard = board.getDiscard();
			int size = discard.size();
			Card c = null;
			int i = 0;
			int row = 0;
			for (i = 0; i < size; i++) {
				if (i != 0 && i % 10 == 0)
					row++;
				if (5+(i%10)*149 <= x && x < 5+(i%10)*149 + 144 && 210+row*230 <= y && y < 210+row*230 + 220) {
					c = discard.get(i);
					break;
				}
			}
			if (c != null) {
				board.wonderThing(curPlayer, c);
				wonderUse = 0;
				discard.remove(i);
				curPlayer = board.nextPlayer();
				repaint();
				return;
			}
		}
		if (curPlayer == board.getPlaying()) {
			if (770 <= y && y < 990) {
				Card c = null;
				ArrayList<Card> cur = board.getChoices();
				int sep = 10;
				int size = cur.size();
				int beginning = 750 - (144*size+sep*(size-1))/2;
				int i = 0;
				for (i = 0; i < size; i++) {
					if (beginning+i*(144+sep) <= x && x < beginning+(i+1)*(144+sep)-sep) {
						c = cur.get(i);
						break;
					}
				}
				if (c != null) {
					if (wonderUse == 2) {
						board.wonderThing(curPlayer, c);
						wonderUse = 0;
						cur.remove(i);
						curPlayer = board.nextPlayer();
					}
					if (wonderUse == 1) {
						
					}
//					g.drawImage(other.get("play"), beginning+cardSelection*(144+sep)+50, 830, 66, 48, null);
//					g.drawImage(other.get("wonder1"), beginning+cardSelection*(144+sep)+55, 880, 57, 48, null);
//					g.drawImage(other.get("discard"), beginning+cardSelection*(144+sep)+65, 935, 36, 45, null);
					if (cardSelected && cardSelection == i) {
						int f = beginning+cardSelection*(144+sep);
						if (f+50 <=x && x < f+50+66 && 830 <= y && y < 878) {
							//playCard
							if (!board.playCard(curPlayer, c))
								return;
							cur.remove(i);
							curPlayer = board.nextPlayer();
						}
						else if (f+55 <=x && x < f+55+57 && 880 <= y && y < 928) {
							//use wonder
							if (!board.build(curPlayer, c))
								return;
							cur.remove(i);
							curPlayer = board.nextPlayer();
						}
						else if (f+65 <= x && x < f+65+36 && 935 <= y && y < 980) {
							cur.remove(i);
							board.discard(curPlayer, c);
							curPlayer = board.nextPlayer();
						}
					}
				}
				cardSelected = cardSelected ? cardSelection == i ? !cardSelected : cardSelected : true;
				cardSelection = i;
				if (i == size)
					cardSelected = false;
//				System.out.println(c);
//				ArrayList<ArrayList<Card>> qwer = board.getCur();
//				for (i = 0; i < 3; i++)
//					System.out.println(qwer.get(0).size());
			}
			else
				cardSelected = false;

//			if (wn.equals("halikarnassus") || wn.equals("olympia")) {
//				if (cp.getWonder().nextBuild() > 1) {
//					if (cp.getWonder().used())
//						g.drawImage(other.get("wonder0"), 1411, 910, 70, 58, null);
//					else
//						g.drawImage(other.get("wonder1"), 1411, 910, 70, 58, null);
//				}
//			}
//			g.drawImage(other.get("wonder0"), 1411, 910, 70, 58, null);
			Wonder w = board.getPlayer(curPlayer).getWonder();
			if (1411 <= x && x < 1481 && 910 <= y && y < 968) {
				if (w.getName().equals("olympia")) {
					if (!w.used())
						wonderUse = wonderUse == 0 ? 2 : 0;
				}
				else if (w.getName().equals("halikarnassus")) {
					if (!w.used() && board.getDiscard().size() != 0)
						wonderUse = wonderUse == 0 ? 1 : 0;
				}
			}
		}
		
		
		//clicks on card to play card
		//next player
//		board.nextPlayer();
		
		
		
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
