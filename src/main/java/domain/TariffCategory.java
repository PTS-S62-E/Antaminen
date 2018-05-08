package domain;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name = "TariffCategory.getAll", query = "SELECT t FROM TariffCategory t"),
})
public class TariffCategory {

	@Id
	String name;
	@Column(nullable = false) //tariff in cents
	int tariff;
	String description;

	public TariffCategory(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTariff() {
		return tariff;
	}

	public void setTariff(int tariff) {
		this.tariff = tariff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
