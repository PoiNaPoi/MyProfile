
public class Player {
	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;			//Attack power of this player
	public int Special;
	public int numsp;
	public boolean sleep;
	public boolean curse;
	public boolean DS;
	public boolean taunt;
	
	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	public Player(PlayerType _type)
	{	
		//INSERT YOUR CODE HERE
		type = _type;
		sleep = false;
		curse = false;
		numsp = 1;
		switch(type.name()) {
			case "Healer" :{
				maxHP = 4790;
				currentHP = maxHP;
				atk = 238;
				Special = 4;
				break;
			}
			case "Tank" :{
				maxHP = 5340;
				currentHP = maxHP;
				atk = 255;
				Special = 4;
				taunt = false;
				break;
			}
			case "Samurai" :{
				maxHP = 4005;
				currentHP = maxHP;
				atk = 368;
				Special = 3;
				DS = false;
				break;
			}
			case "BlackMage" :{
				maxHP = 4175;
				currentHP = maxHP;
				atk = 303;
				Special = 4;
				break;
			}
			case "Phoenix" :{
				maxHP = 4175;
				currentHP = maxHP;
				atk = 209;
				Special = 8;
				break;
			}
			case "Cherry" :{
				maxHP = 3560;
				currentHP = maxHP;
				atk = 198;
				Special = 4;
				break;
			}
		}
	}
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		return currentHP;
	}	
	/**
	 * Returns type of this player
	 * @return
	 */
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		return type;
	}	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE
		return maxHP;
	}	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		if(sleep==true)
			return true;
		else
			return false;
	}
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		if(curse==true)
			return true;
		else
			return false;
	}
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		if(currentHP>0)
			return true;
		else
			return false;
	}	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		if(taunt==true)
			return true;
		else
			return false;
	}
	public void attack(Player target)
	{	
		//INSERT YOUR CODE HERE
		if(target.getCurrentHP()-atk>0) {
			target.currentHP -= atk;
		}
		else {
			target.currentHP = 0;
			target.sleep = false;
			target.curse = false;
			target.taunt = false;
			target.numsp = 1;
		}
	}	
	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		switch(getType()) {
			case Healer:{
				double min = 10000;
				double max = 0;
				double heal = 0;
				int[] po = {0,0};
				for(int x=0;x<2;x++) {
					for(int y=0;y<myTeam[x].length;y++) {
						if(myTeam[x][y].curse==false&&myTeam[x][y].isAlive()&&min>myTeam[x][y].currentHP&&myTeam[x][y].currentHP!=myTeam[x][y].maxHP) {
							min = myTeam[x][y].currentHP;
							max = myTeam[x][y].maxHP;
							po[0] = x;
							po[1] = y;
						}
					}
				}
				heal = max/4;
				if(min+heal>=max) {
					myTeam[po[0]][po[1]].currentHP = max;
				}
				else {
					myTeam[po[0]][po[1]].currentHP += heal;
				}
				break;
			}
			case Tank:{
				taunt = true;
				break;
			}
			case Samurai:{
				DS = true;
				break;
			}
			case BlackMage:{
				int[][] po = new int[2][2];
				double[] m = {10000,10000};
				boolean y = false;
				boolean z = false;
				for(int i=0;i<2;i++) {
					for(int j=0;j<theirTeam[i].length;j++) {
						if(theirTeam[i][j].isTaunting()) {
							theirTeam[i][j].curse = true;
							z = true;
							break;
						}
					}
				}
				if(z==false) {
					for(int i=0;i<2;i++) {
						for(int j=0;j<theirTeam[i].length;j++) {
							if(i==0) {
								if(theirTeam[i][j].getCurrentHP()!=0) {
									y = true;
								}
							}
							if(m[i]>theirTeam[i][j].getCurrentHP()&&theirTeam[i][j].getCurrentHP()!=0) {
								m[i] = theirTeam[i][j].getCurrentHP();
								po[i][0] = i;
								po[i][1] = j;
							}
						}
					}
					if(y) {
						theirTeam[po[0][0]][po[0][1]].curse = true;
					}
					else theirTeam[po[1][0]][po[1][1]].curse = true;
				}
				break;
			}
			case Cherry:{
				for(int k=0;k<2;k++) {
					for(int l=0;l<theirTeam[k].length;l++) {
						if(theirTeam[k][l].isAlive()) {
							theirTeam[k][l].sleep = true;
						}
					}
				}
				break;
			}
			case Phoenix:{
				int k=0,l=0;
				for(k=0;k<2;k++) {
					for(l=0;l<myTeam[k].length;l++) {
						if(myTeam[k][l].isAlive()==false) {
							myTeam[k][l].currentHP += myTeam[k][l].maxHP*0.3;
							break;
						}
					}
					if(myTeam[k][l].isAlive())
						break;
				}
				break;
			}
		}
	}
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		Player x = null;
		if(isAlive()&&isSleeping()==false) {
			if(numsp%Special==0&&numsp!=0) {
				useSpecialAbility(arena.getMyteam(), arena.getTheirteam());
				if(DS) {
					x = arena.target();
					attack(arena.target());
					if(arena.target()==x) {
						attack(arena.target());
					}
				}
			}
			else {
				attack(arena.target());
			}
			numsp++;
		}
		else {
			
		}
		
	}
	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
	public int getSpecial() {
		return Special;
	}
}
