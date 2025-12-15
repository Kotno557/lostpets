package br.lostpets.project.model;

/**
 * Value Object representing an address with geographic coordinates.
 * Refactored from Address for English naming consistency.
 * 
 * This class encapsulates address-related fields to address the Data Clumps code smell.
 */
public class Address {

	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;
	private String unidade;
	private String ibge;
	private String gia;

	private double latitude;
	private double longitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public String getUf() {
		return uf;
	}

	public String getUnidade() {
		return unidade;
	}

	public String getIbge() {
		return ibge;
	}

	public String getGia() {
		return gia;
	}

	public Address setCep(String cep) {
		this.cep = cep;
		return this;
	}

	public Address setLogradouro(String logradouro) {
		this.logradouro = logradouro;
		return this;
	}

	public Address setComplemento(String complemento) {
		this.complemento = complemento;
		return this;
	}

	public Address setBairro(String bairro) {
		this.bairro = bairro;
		return this;
	}

	public Address setLocalidade(String localidade) {
		this.localidade = localidade;
		return this;
	}

	public Address setUf(String uf) {
		this.uf = uf;
		return this;
	}

	public Address setUnidade(String unidade) {
		this.unidade = unidade;
		return this;
	}

	public Address setIbge(String ibge) {
		this.ibge = ibge;
		return this;
	}

	public Address setGia(String gia) {
		this.gia = gia;
		return this;
	}

	@Override
	public String toString() {
		return "Address [cep=" + cep + ", logradouro=" + logradouro + ", complemento=" + complemento + ", bairro="
				+ bairro + ", localidade=" + localidade + ", uf=" + uf + ", unidade=" + unidade + ", ibge=" + ibge
				+ ", gia=" + gia + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
