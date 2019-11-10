package tsp_ec;

/**
 * Clase que define un objeto para cada ciudad a través de sus coordenadas cartesianas
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class City {

	private Double x;
	private Double y;

	/**
	 * Constructor para cada ciudad
	 * 
	 * @param x Coordenada en el eje X
	 * @param y Coordenada en el eje Y
	 */
	public City(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	/**
	 * Método para calcular la distancia entre dos ciudades
	 * 
	 * @param c Ciudad destino (hasta la que se desea calcular la distancia)
	 * @return Distancia entre ambas ciudades
	 */
	public Double getDistance(City c) {
		Double dist = null;
		Double x = null;
		Double y = null;

		if (!this.equals(c)) {

			x = this.getX() - c.getX();
			y = this.getY() - c.getY();

			dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

		}
		return dist;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City [x=" + x + ", y=" + y + "]";
	}

}
