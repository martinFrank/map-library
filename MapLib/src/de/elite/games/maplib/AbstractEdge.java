package de.elite.games.maplib;


public abstract class AbstractEdge implements Edge{
	
	private final Point a;
	private final Point b;
	
	
	public AbstractEdge(Point a, Point b){
		this.a = a;
		this.b = b;
	}
	
	@Override
	public void scale(float scale){
		a.scale(scale);
		b.scale(scale);
	}

	@Override
	public void pan(int dx, int dy) {
		a.pan(dx,dy);
		b.pan(dx,dy);
	}


	@Override
	public Point a() {
		return a;
	}

	@Override
	public Point b() {
		return b;
	}
	
	@Override
	public String toString() {
		return ""+a().toString()+" --> "+b.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
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
		AbstractEdge other = (AbstractEdge) obj;
		if(a.equals(other.a) &&  b.equals(other.b)){
			return true;
		}
		if(a.equals(other.b) &&  b.equals(other.a)){
			return true;
		}
		return false;
	}
	
	
	
	
}
