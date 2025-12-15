package br.lostpets.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.lostpets.project.service.DateTimeService;

@Entity
@Table(name="PETS_PERDIDO")
public class LostPet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_ANIMAL") private int idAnimal;
	
	@ManyToOne(cascade=CascadeType.ALL)	
	@JoinColumn(name = "ID_USUARIO")
	private User usuario;
	
	@Column(name="NOME_ANIMAL") private String nomeAnimal;
	@Column(name="DATA_PERDIDO") private String dataPerdido;
	@Column(name="ATIVO") private String status;
	@Column(name="DESCRICAO") private String descricao;
	@Column(name="DESCRICAO_ANIMAL") private String descricaoAnimal;
	@Column(name="TIPO_ANIMAL") private String tipoAnimal;
	@Column(name="PATH_IMG")private String pathImg;
	@Column(name="CEP")private String cep;
	@Column(name = "RUA") private String rua;
	@Column(name = "BAIRRO") private String bairro;
	@Column(name = "CIDADE") private String cidade;
	@Column(name = "UF") private String uf;
	@Column(name="LATITUDE") private double latitude;
	@Column(name="LONGITUDE") private double longitude;
	@Column(name="ADD_DATA") private String addData = dataHora();
	
	@OneToMany(mappedBy = "petPerdido")
	private List<FoundAnimal> animaisAchados;
	
	private String dataHora() {
		return new DateTimeService().getDateHour();
	}

	public LostPet() {}

	public LostPet(User usuario, String nomeAnimal, String dataPerdido, String descricao,
			String tipoAnimal, String pathImg, String cep, double latitude, double longitude) {
		this.usuario = usuario;
		this.nomeAnimal = nomeAnimal;
		this.dataPerdido = dataPerdido;
		this.status = "P";
		this.descricao = descricao;
		this.tipoAnimal = tipoAnimal;
		this.pathImg = pathImg;
		this.cep = cep;
		this.latitude = latitude;
		this.longitude = longitude;
		this.addData = dataHora();
	}

	public LostPet(User usuario, LostPet petPerdido) {
		this.usuario = usuario;
		this.nomeAnimal = petPerdido.getNomeAnimal();
		this.dataPerdido = petPerdido.getDataPerdido();
		this.status = "P";
		this.cep = petPerdido.getCep();
		this.addData = dataHora();
	}

	public int getIdAnimal() {
		return idAnimal;
	}

	public User getUsuario() {
		return usuario;
	}

	public String getNomeAnimal() {
		return nomeAnimal;
	}

	public void setNomeAnimal(String nomeAnimal) {
		this.nomeAnimal = nomeAnimal;
	}

	public String getDataPerdido() {
		return dataPerdido;
	}

	public void setDataPerdido(String dataPerdido) {
		this.dataPerdido = dataPerdido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoAnimal() {
		return tipoAnimal;
	}

	public void setTipoAnimal(String tipoAnimal) {
		this.tipoAnimal = tipoAnimal;
	}

	public String getPathImg() {
		return pathImg;
	}

	public void setPathImg(String pathImg) {
		this.pathImg = pathImg;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double d) {
		this.latitude = d;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddData() {
		return addData;
	}

	public void setAddData(String addData) {
		this.addData = addData;
	}
	
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getDescricaoAnimal() {
		return descricaoAnimal;
	}

	public void setDescricaoAnimal(String descricaoAnimal) {
		this.descricaoAnimal = descricaoAnimal;
	}

	@Override
	public String toString() {
		return "PetPerdido [idAnimal=" + idAnimal + ", usuario=" + usuario + ", nomeAnimal=" + nomeAnimal
				+ ", dataPerdido=" + dataPerdido + ", status=" + status + ", descricao=" + descricao
				+ ", descricaoAnimal=" + descricaoAnimal + ", tipoAnimal=" + tipoAnimal + ", pathImg=" + pathImg
				+ ", cep=" + cep + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", addData=" + addData + ", animaisAchados="
				+ animaisAchados + "]";
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Facade methods to reduce message chains (Law of Demeter)
	 * These methods encapsulate access to Usuario properties
	 */
	
	public String getOwnerEmail() {
		return usuario != null ? usuario.getEmail() : null;
	}
	
	public String getOwnerName() {
		return usuario != null ? usuario.getNome() : null;
	}
	
	public String getOwnerPhone() {
		return usuario != null ? usuario.getTelefoneCelular() : null;
	}
	
	public int getOwnerId() {
		return usuario != null ? usuario.getIdPessoa() : 0;
	}
	
	/**
	 * Sets the address information from an Address value object.
	 * Addresses the Data Clumps code smell by centralizing address field management.
	 */
	public void setAddress(Address address) {
		if (address == null) {
			return;
		}
		this.cep = address.getCep();
		this.rua = address.getLogradouro();
		this.bairro = address.getBairro();
		this.cidade = address.getLocalidade();
		this.uf = address.getUf();
		this.latitude = address.getLatitude();
		this.longitude = address.getLongitude();
	}
	
	/**
	 * Gets the address information as an Address value object.
	 * Addresses the Data Clumps code smell by encapsulating address fields.
	 * 
	 * @return Address object containing all address fields
	 */
	public Address getAddress() {
		Address address = new Address();
		address.setCep(this.cep);
		address.setLogradouro(this.rua);
		address.setBairro(this.bairro);
		address.setLocalidade(this.cidade);
		address.setUf(this.uf);
		address.setLatitude(this.latitude);
		address.setLongitude(this.longitude);
		return address;
	}
	
	/**
	 * Builder pattern implementation for PetPerdido.
	 * Addresses the Long Parameter List code smell.
	 * 
	 * Usage example:
	 * <pre>
	 * LostPet pet = PetPerdido.builder()
	 *     .usuario(usuario)
	 *     .nomeAnimal("Rex")
	 *     .tipoAnimal("Cachorro")
	 *     .address(address)
	 *     .build();
	 * </pre>
	 */
	public static class Builder {
		private User usuario;
		private String nomeAnimal;
		private String dataPerdido;
		private String status = "P";  // Default status
		private String descricao;
		private String descricaoAnimal;
		private String tipoAnimal;
		private String pathImg;
		private String cep;
		private String rua;
		private String bairro;
		private String cidade;
		private String uf;
		private double latitude;
		private double longitude;
		
		public Builder usuario(User usuario) {
			this.usuario = usuario;
			return this;
		}
		
		public Builder nomeAnimal(String nomeAnimal) {
			this.nomeAnimal = nomeAnimal;
			return this;
		}
		
		public Builder dataPerdido(String dataPerdido) {
			this.dataPerdido = dataPerdido;
			return this;
		}
		
		public Builder status(String status) {
			this.status = status;
			return this;
		}
		
		public Builder descricao(String descricao) {
			this.descricao = descricao;
			return this;
		}
		
		public Builder descricaoAnimal(String descricaoAnimal) {
			this.descricaoAnimal = descricaoAnimal;
			return this;
		}
		
		public Builder tipoAnimal(String tipoAnimal) {
			this.tipoAnimal = tipoAnimal;
			return this;
		}
		
		public Builder pathImg(String pathImg) {
			this.pathImg = pathImg;
			return this;
		}
		
		public Builder cep(String cep) {
			this.cep = cep;
			return this;
		}
		
		public Builder rua(String rua) {
			this.rua = rua;
			return this;
		}
		
		public Builder bairro(String bairro) {
			this.bairro = bairro;
			return this;
		}
		
		public Builder cidade(String cidade) {
			this.cidade = cidade;
			return this;
		}
		
		public Builder uf(String uf) {
			this.uf = uf;
			return this;
		}
		
		public Builder latitude(double latitude) {
			this.latitude = latitude;
			return this;
		}
		
		public Builder longitude(double longitude) {
			this.longitude = longitude;
			return this;
		}
		
		/**
		 * Sets all address-related fields from an Address value object.
		 * Addresses the Data Clumps code smell.
		 */
		public Builder address(Address address) {
			if (address != null) {
				this.cep = address.getCep();
				this.rua = address.getLogradouro();
				this.bairro = address.getBairro();
				this.cidade = address.getLocalidade();
				this.uf = address.getUf();
				this.latitude = address.getLatitude();
				this.longitude = address.getLongitude();
			}
			return this;
		}
		
		/**
		 * Builds and returns the PetPerdido instance.
		 * 
		 * @return PetPerdido instance with all configured fields
		 */
		public LostPet build() {
			LostPet pet = new LostPet();
			pet.usuario = this.usuario;
			pet.nomeAnimal = this.nomeAnimal;
			pet.dataPerdido = this.dataPerdido;
			pet.status = this.status;
			pet.descricao = this.descricao;
			pet.descricaoAnimal = this.descricaoAnimal;
			pet.tipoAnimal = this.tipoAnimal;
			pet.pathImg = this.pathImg;
			pet.cep = this.cep;
			pet.rua = this.rua;
			pet.bairro = this.bairro;
			pet.cidade = this.cidade;
			pet.uf = this.uf;
			pet.latitude = this.latitude;
			pet.longitude = this.longitude;
			pet.addData = pet.dataHora();
			return pet;
		}
	}
	
	/**
	 * Creates a new Builder instance for fluent object construction.
	 * 
	 * @return new Builder instance
	 */
	public static Builder builder() {
		return new Builder();
	}

}
