public class Card 
{
	private int id;
	private String name;
	private String type;
	private String subtype;
	private int def;
	private int atk;
	private int cost;
	
	public Card(int id, String name, String type, String subtype, int def, int atk, int cost)
	{
		this.id = id;
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.def = def;
		this.atk = atk;
		this.cost = cost;
	}

	public int getId()
	{ 
		return id;
	}
	
	public String getName()
	{ 
		return name;
	}
	
	public String getType()
	{ 
		return type;
	}
	
	public String getSubtype()
	{ 
		return subtype;
	}
	
	public int getDef()
	{ 
		return def;
	}
	
	public int getAtk()
	{ 
		return atk;
	}
	
	public int getCost()
	{ 
		return cost;
	}
	
	
}