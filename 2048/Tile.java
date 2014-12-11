import java.awt.Color;

class Tile
{
	int value;

	public Tile()
	{
		this(0);
	}

	public Tile(int num)
	{
		value = num;
	}

	public boolean isEmpty()
	{
		return value == 0;
	}
	
	@Override
	public String toString()
	{
		return value + "";
	}

	public Color getForeground()
	{
		return value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2);
	}
	
	public boolean equals(Tile other)
	{
		return this.value == other.value;
	}
	
	public Tile clone() {
		return new Tile(this.value);
	}

	public Color getBackground()
	{
		switch (value)
		{
		case 2:
			return new Color(0xeee4da);
		case 4:
			return new Color(0xede0c8);
		case 8:
			return new Color(0xf2b179);
		case 16:
			return new Color(0xf59563);
		case 32:
			return new Color(0xf67c5f);
		case 64:
			return new Color(0xf65e3b);
		case 128:
			return new Color(0xedcf72);
		case 256:
			return new Color(0xedcc61);
		case 512:
			return new Color(0xedc850);
		case 1024:
			return new Color(0xedc53f);
		case 2048:
			return new Color(0xedc22e);
		case 4096:
			return new Color(0xff9999);
		case 8192:
			return new Color(0xff6666);
		case 16384:
			return new Color(0xff3333);
		case 3276:
			return new Color(0x66b2ff);
		case 65536:
			return new Color(0x3399ff);
		case 131072:
			return new Color(0x0080ff);
		}
		return new Color(0xcdc1b4);
	}
}
