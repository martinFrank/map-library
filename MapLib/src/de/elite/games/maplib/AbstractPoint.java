package de.elite.games.maplib;


public abstract class AbstractPoint implements Point {
	
	private int x;
	private int y;
	
	private int px = 0;
	private int py = 0;
	
	private int x_scaled = 0;
	private int y_scaled = 0;
	
	public AbstractPoint(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	@Override
	public void scale(float scale){
		x_scaled = (int)(x * scale);
		y_scaled = (int)(y * scale);
	}
	
	@Override
	public void pan(int dx, int dy) {
		px = dx;
		py = dy;
	}
	
	@Override
	public int xScaled(){
		return x_scaled+px;
	}
	
	@Override
	public int yScaled(){
		return y_scaled+py;
	}

	@Override
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int x() {
		return x;
	}

	@Override
	public int y() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPoint other = (AbstractPoint) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ""+x()+"/"+y();
				
	}
	
	

}
