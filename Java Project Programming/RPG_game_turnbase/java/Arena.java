import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Arena {
	public enum Row {Front, Back};	//enum for specifying the front or back row
	public enum Team {A, B};		//enum for specifying team A or B
	private Player[][] teamA = null;	//two dimensional array representing the players of Team A
	private Player[][] teamB = null;	//two dimensional array representing the players of Team B
	private int numRowPlayers = 0;		//number of players in each row
	public static final int MAXROUNDS = 100;	//Max number of turn
	public static final int MAXEACHTYPE = 3;	//Max number of players of each type, in each team.
	private Player[][] myteam = null;
	private Player[][] theirteam = null;
	private final Path logFile = Paths.get("battle_log.txt");	
	private int numRounds = 0;	//keep track of the number of rounds so far	
	
	/**
	 * Constructor. 
	 * @param _numRowPlayers is the number of players in each row.
	 */
	public Arena(int _numRowPlayers)
	{	
		//INSERT YOUR CODE HERE
		numRowPlayers = _numRowPlayers;
		teamA = new Player[2][numRowPlayers];
		teamB = new Player[2][numRowPlayers];
		myteam = teamA;
		theirteam = teamB;
		
		////Keep this block of code. You need it for initialize the log file. 
		////(You will learn how to deal with files later)
		try {
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////
		
	}
	/**
	 * Returns true if "player" is a member of "team", false otherwise.
	 * Assumption: team can be either Team.A or Team.B
	 * @param player
	 * @param team
	 * @return
	 */
	public boolean isMemberOf(Player player, Team team)
	{
		//INSERT YOUR CODE HERE
		Player[][] x = null;
		if(team==Team.A)
			x = teamA.clone();
		else
			x = teamB.clone();
		for(int i=0;i<2;i++) {
			for(int j=0;j<x[i].length;j++) {
				if(player.equals(x[i][j]))
					return true;
			}
		}
		return false;
	}
	/**
	 * This methods receives a player configuration (i.e., team, type, row, and position), 
	 * creates a new player instance, and places him at the specified position.
	 * @param team is either Team.A or Team.B
	 * @param pType is one of the Player.Type  {Healer, Tank, Samurai, BlackMage, Phoenix}
	 * @param row	either Row.Front or Row.Back
	 * @param position is the position of the player in the row. Note that position starts from 1, 2, 3....
	 */
	public void addPlayer(Team team, Player.PlayerType pType, Row row, int position)
	{	
		//INSERT YOUR CODE HERE
		if(team==Team.A) {
			if(row==Row.Front) {
				teamA[0][position-1] = new Player(pType);
			}
			else {
				teamA[1][position-1] = new Player(pType);
			}
		}
		else {
			if(row==Row.Front) {
				teamB[0][position-1] = new Player(pType);
			}
			else {
				teamB[1][position-1] = new Player(pType);
			}
		}
	}
	/**
	 * Validate the players in both Team A and B. Returns true if all of the following conditions hold:
	 * 
	 * 1. All the positions are filled. That is, there each team must have exactly numRow*numRowPlayers players.
	 * 2. There can be at most MAXEACHTYPE players of each type in each team. For example, if MAXEACHTYPE = 3
	 * then each team can have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
	 * 
	 * Returns true if all the conditions above are satisfied, false otherwise.
	 * @return
	 */
	public boolean validatePlayers()
	{
		//INSERT YOUR CODE HERE	
		int a[][] = new int[2][6];
		for(int k=0;k<2;k++) {
			for(int i=0;i<2;i++) {
				for(int j=0;j<numRowPlayers;j++) {
					if(k==0) {
						switch(teamA[i][j].getType()) {
							case Healer :{
								a[k][0]++;
								break;
							}
							case Tank :{
								a[k][1]++;
								break;
							}
							case Samurai :{
								a[k][2]++;
								break;
							}
							case BlackMage :{
								a[k][3]++;
								break;
							}
							case Phoenix :{
								a[k][4]++;
								break;
							}
							case Cherry :{
								a[k][5]++;
								break;
							}
							default :{
								return false;
							}
						}
					}
					if(k==1) {
						switch(teamB[i][j].getType()) {
							case Healer :{
								a[k][0]++;
								break;
							}
							case Tank :{
								a[k][1]++;
								break;
							}
							case Samurai :{
								a[k][2]++;
								break;
							}
							case BlackMage :{
								a[k][3]++;
								break;
							}
							case Phoenix :{
								a[k][4]++;
								break;
							}
							case Cherry :{
								a[k][5]++;
								break;
							}
							default :{
								return false;
							}
						}
					}
				}
			}
		}
		for(int i=0;i<2;i++) {
			for(int j=0;j<6;j++) {
				if(a[i][j]>MAXEACHTYPE)
					return false;
			}
		}
		return true;
	}
	/**
	 * Returns the sum of HP of all the players in the given "team"
	 * @param team
	 * @return
	 */
	public static double getSumHP(Player[][] team)
	{
		//INSERT YOUR CODE HERE
		double sum = 0;
		for(int i=0;i<2;i++) {
			for(int j=0;j<team[i].length;j++) {
				sum += team[i][j].getCurrentHP();
			}
			
		}
		return sum;
	}
	/**
	 * Return the team (either teamA or teamB) whose number of alive players is higher than the other. 
	 * 
	 * If the two teams have an equal number of alive players, then the team whose sum of HP of all the
	 * players is higher is returned.
	 * 
	 * If the sums of HP of all the players of both teams are equal, return teamA.
	 * @return
	 */
	public Player[][] getWinningTeam()
	{
		//INSERT YOUR CODE HERE	
		if(getSumHP(teamA)<=0)
			return teamB;
		else if(getSumHP(teamB)<=0)
			return teamA;
		else
			return null;
	}
	/**
	 * This method simulates the battle between teamA and teamB. The method should have a loop that signifies
	 * a round of the battle. In each round, each player in teamA invokes the method takeAction(). The players'
	 * turns are ordered by its position in the team. Once all the players in teamA have invoked takeAction(),
	 * not it is teamB's turn to do the same. 
	 * 
	 * The battle terminates if one of the following two conditions is met:
	 * 
	 * 1. All the players in a team has been eliminated.
	 * 2. The number of rounds exceeds MAXROUNDS
	 * 
	 * After the battle terminates, report the winning team, which is determined by getWinningTeam().
	 */
	public void startBattle()
	{
		myteam = teamA;
		theirteam = teamB;
		boolean xxx  = false;
		while(getSumHP(teamA)>0&&getSumHP(teamB)>0&&numRounds<MAXROUNDS) {
			numRounds++;
			System.out.println("@ Round "+(numRounds));
			if(xxx==false) {
				for(int i=0;i<2;i++) {
					for(int j=0;j<2;j++) {
						for(int k=0;k<myteam[j].length;k++) {
							if(myteam[j][k].isAlive()==true&&myteam[j][k].isSleeping()==false) {
								System.out.print("# "+checkteam(myteam[j][k]).toString()+"["+checkrow(myteam[j][k]).toString()+"]["+(k+1)+"] {"+myteam[j][k].getType()+"}");
								if(myteam[j][k].numsp%myteam[j][k].getSpecial()==0&&myteam[j][k].numsp!=0) {
									switch(myteam[j][k].getType()) {
										case Samurai:{
											System.out.println(" Double-Slashes "+checkteam(target()).toString()+"["+checkrow(target()).toString()+"]["+position()+"] {"+target().getType()+"}");
											break;
										}
										case BlackMage:{
											System.out.println(" Curses "+checkteam(target()).toString()+"["+checkrow(target()).toString()+"]["+position()+"] {"+target().getType()+"}");
											break;
										}
										case Cherry:{
											for(int x=0;x<2;x++) {
												for(int y=0;y<theirteam[x].length;y++) {
													if(theirteam[x][y].isAlive()) {
														if(x!=0||y!=0)
															System.out.print("# "+checkteam(myteam[j][k]).toString()+"["+checkrow(myteam[j][k]).toString()+"]["+(k+1)+"] {"+myteam[j][k].getType()+"}");
														System.out.println(" Feeds a Fortune Cookie to "+checkteam(theirteam[x][y]).toString()+"["+checkrow(theirteam[x][y]).toString()+"]["+(y+1)+"] {"+theirteam[x][y].getType()+"}");
													}
												}
											}
											xxx = true;
											break;
										}
										case Healer:{
											double min = 10000;
											int[] po = {0,0};
											for(int x=0;x<2;x++) {
												for(int y=0;y<myteam[x].length;y++) {
													if(myteam[x][y].curse==false&&myteam[x][y].isAlive()&&min>myteam[x][y].getCurrentHP()&&myteam[x][y].getCurrentHP()!=myteam[x][y].getMaxHP()) {
														min = myteam[x][y].getCurrentHP();
														po[0] = x;
														po[1] = y;
													}
												}
											}
											System.out.println(" Heals "+checkteam(myteam[po[0]][po[1]]).toString()+"["+checkrow(myteam[po[0]][po[1]]).toString()+"]["+(po[1]+1)+"] {"+myteam[po[0]][po[1]].getType()+"}");
											break;
										}
										case Tank:{
											System.out.println(" is Taunting");
											break;
										}
										case Phoenix:{
											int x=0,y=0;
											for(x=0;x<2;x++) {
												for(y=0;y<myteam[x].length;y++) {
													if(myteam[x][y].isAlive()==false) {
														System.out.println(" revives "+checkteam(myteam[x][y]).toString()+"["+checkrow(myteam[x][y]).toString()+"]["+(1+y)+"] {"+myteam[x][y].getType()+"}");
														break;
													}
												}
												if(myteam[x][y].isAlive()==false)
													break;
											}
											break;
										}
									}
								}
								else {
									System.out.println(" Attacks "+checkteam(target()).toString()+"["+checkrow(target()).toString()+"]["+position()+"] {"+target().getType()+"}");
								}
								myteam[j][k].takeAction(Arena.this);
							}
						}
					}
					if(xxx==false) {
						swap();
						resetall(myteam);
					}
					else {
						break;
					}
				}
			}
			logAfterEachRound();
			displayArea(this, true);
			resetall(myteam);
			for(int ii=0;ii<2;ii++) {
				for(int jj=0;jj<theirteam[ii].length;jj++) {
					theirteam[ii][jj].curse = false;
				}
			}
			xxx = false;
		}
		System.out.println("@@@ Team "+checkteam(getWinningTeam()[0][0])+" won.");
	}
	/**
	 * This method displays the current area state, and is already implemented for you.
	 * In startBattle(), you should call this method once before the battle starts, and 
	 * after each round ends. 
	 * 
	 * @param arena
	 * @param verbose
	 */
	public static void displayArea(Arena arena, boolean verbose)
	{
		StringBuilder str = new StringBuilder();
		if(verbose)
		{
			str.append(String.format("%43s   %40s","Team A","")+"\t\t"+String.format("%-38s%-40s","","Team B")+"\n");
			str.append(String.format("%43s","BACK ROW")+String.format("%43s","FRONT ROW")+"  |  "+String.format("%-43s","FRONT ROW")+"\t"+String.format("%-43s","BACK ROW")+"\n");
			for(int i = 0; i < arena.numRowPlayers; i++)
			{
				str.append(String.format("%43s",arena.teamA[1][i])+String.format("%43s",arena.teamA[0][i])+"  |  "+String.format("%-43s",arena.teamB[0][i])+String.format("%-43s",arena.teamB[1][i])+"\n");
			}
		}
	
		str.append("@ Total HP of Team A = "+getSumHP(arena.teamA)+". @ Total HP of Team B = "+getSumHP(arena.teamB)+"\n\n");
		System.out.print(str.toString());
	}
	/**
	 * This method writes a log (as round number, sum of HP of teamA, and sum of HP of teamB) into the log file.
	 * You are not to modify this method, however, this method must be call by startBattle() after each round.
	 * 
	 * The output file will be tested against the auto-grader, so make sure the output look something like:
	 * 
	 * 1	47415.0	49923.0
	 * 2	44977.0	46990.0
	 * 3	42092.0	43525.0
	 * 4	44408.0	43210.0
	 * 
	 * Where the numbers of the first, second, and third columns specify round numbers, sum of HP of teamA, and sum of HP of teamB respectively. 
	 */
	private void logAfterEachRound()
	{
		try {
			Files.write(logFile, Arrays.asList(new String[]{numRounds+"\t"+getSumHP(teamA)+"\t"+getSumHP(teamB)}), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
//----------------------------------------------------------------------------------------------
	public Player target() {
		int[][] po = new int[2][2];
		double[] m = {10000,10000};
		boolean y = false;
		for(int i=0;i<2;i++) {
			for(int j=0;j<theirteam[i].length;j++) {
				if(theirteam[i][j].isTaunting()) {
					return theirteam[i][j];
				}
			}
		}
		for(int i=0;i<2;i++) {
			for(int j=0;j<theirteam[i].length;j++) {
				if(i==0) {
					if(theirteam[i][j].getCurrentHP()!=0) {
						y = true;
					}
				}
				if(m[i]>theirteam[i][j].getCurrentHP()&&theirteam[i][j].getCurrentHP()!=0) {
					m[i] = theirteam[i][j].getCurrentHP();
					po[i][0] = i;
					po[i][1] = j;
				}
			}
		}
		if(y) {
			return theirteam[po[0][0]][po[0][1]];
		}
		return theirteam[po[1][0]][po[1][1]];
	}
	public Player[][] getMyteam() {
		return myteam;
	}
	public Player[][] getTheirteam() {
		return theirteam;
	}
	public void swap() {
		Player[][] Null = null;
		Null = theirteam;
		theirteam = myteam;
		myteam = Null;
	}
	public int position() {
		int i=0,j=0;
		for(i=0;i<2;i++) {
			for(j=0;j<theirteam[i].length;j++) {
				if(theirteam[i][j]==target())
					return j+1;
			}
		}
		return 0;
	}
	public Team checkteam(Player p) {
		for(int i=0;i<2;i++) {
			for(int j=0;j<teamA[i].length;j++) {
				if(teamA[i][j]==p) {
					return Team.A;
				}
			}
		}
		for(int i=0;i<2;i++) {
			for(int j=0;j<teamB[i].length;j++) {
				if(teamB[i][j]==p) {
					return Team.B;
				}
			}
		}
		return null;
	}
	public Row checkrow(Player p) {
		for(int i=0;i<2;i++) {
			for(int j=0;j<teamA[i].length;j++) {
				if(teamA[i][j]==p) {
					if(i==0)
						return Row.Front;
					else
						return Row.Back;
				}
			}
		}
		for(int i=0;i<2;i++) {
			for(int j=0;j<teamB[i].length;j++) {
				if(teamB[i][j]==p) {
					if(i==0)
						return Row.Front;
					else
						return Row.Back;
				}
			}
		}
		return null;
	}
	public void resetall(Player[][] p) {
		for(int i=0;i<2;i++) {
			for(int j=0;j<p[i].length;j++) {
				p[i][j].sleep = false;
				p[i][j].taunt = false;
			}
		}
	}
}
