package pl.poznan.put.cie.coffee;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class Coffee {

	private final SimpleStringProperty name;
	private final SimpleStringProperty supplierId;
	private final SimpleStringProperty price;
	private final SimpleIntegerProperty sales;
	private final SimpleIntegerProperty total;

	public Coffee() {
		name = new SimpleStringProperty("kawa");
		supplierId = new SimpleStringProperty("0");
		price = new SimpleStringProperty("0");
		sales = new SimpleIntegerProperty(0);
		total = new SimpleIntegerProperty(0);
	}

	public Coffee(String name, int supplierId, BigDecimal price, int sales, int total) {
		this.name = new SimpleStringProperty(name);
		this.supplierId = new SimpleStringProperty(Integer.toString(supplierId));
		this.price = new SimpleStringProperty(price.toString());
		this.sales = new SimpleIntegerProperty(sales);
		this.total = new SimpleIntegerProperty(total);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getSupplierId() {
		return supplierId.get();
	}

	public void setSupplierId(String supplierId) {
		this.supplierId.set(supplierId);
	}

	public String getPrice() {
		return price.get();
	}

	public void setPrice(String price)
	{
		this.price.set(price);
	}

	public int getSales() {
		return sales.get();
	}

	public void setSales(int sales) {
		this.sales.set(sales);

	}

	public int getTotal() {
		return total.get();
	}

	public void setTotal(int total) {
		this.total.set(total);
	}

	@Override
	public String toString() {
		return "Coffee{" + "name=" + name + ", supplierId=" + supplierId + ", price=" + price + ", sales=" + sales + ", total=" + total + '}';
	}

}
