package br.lostpets.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.lostpets.project.service.DateTimeService;

@Entity
@Table(name = "USUARIO")
public class Usuario{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "ID_PESSOA")
	private int idPessoa;
	
	@OneToMany(mappedBy = "usuario")
	private List<PetPerdido> idAnimal;
	
	@Column(nullable = false, name = "NOME") private String nome;
	@Column(nullable = true, name = "TELEFONE_FIXO") private String telefoneFixo;
	@Column(nullable = true, name = "TELEFONE_CELULAR") private String telefoneCelular;
	@Column(nullable = false, name = "EMAIL") private String email;
	@Column(nullable = true, name = "SENHA") @JsonIgnore private String senha;
	@Column(nullable = true, name = "PATH_IMG") private String idImagem;
	@Column(nullable = true, name = "CEP") @JsonIgnore private String cep;
	@Column(nullable = true, name = "RUA") @JsonIgnore private String rua;
	@Column(nullable = true, name = "BAIRRO") private String bairro;
	@Column(nullable = true, name = "CIDADE") @JsonIgnore private String cidade;
	@Column(nullable = true, name = "UF") private String uf;
	@Column(nullable = true, name = "LATITUDE") @JsonIgnore private double latitude;
	@Column(nullable = true, name = "LONGITUDE") @JsonIgnore private double longitude;
	@Column(nullable = false, name = "ADD_CADASTRO") private String addCadastro = dataHora();
	@Column(nullable = true, name = "ULTIMO_ACESSO") @JsonIgnore private String ultimoAcesso;

	@OneToMany(mappedBy = "usuarioAchou")
	private List<AnimaisAchados> animaisAchados;	
	
	private String dataHora() {
		return new DateTimeService().getDateHour();
	}

	public Usuario() {}
	
	public Usuario(String nome, String telefoneFixo, String telefoneCelular, String email, String senha,
			String idImagem, String cep, String rua, String bairro, String cidade, String uf, double latitude,
			double longitude) {
		this.nome = nome;
		this.telefoneFixo = telefoneFixo;
		this.telefoneCelular = telefoneCelular;
		this.email = email;
		this.senha = senha;
		this.idImagem = idImagem;
		this.cep = cep;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.latitude = latitude;
		this.longitude = longitude;
		this.addCadastro = dataHora();
	}
	
	public Usuario(String nome, String email, String celular, String telefone) {
		this.nome = nome;
		this.email = email;	
		this.telefoneCelular = celular;
		this.telefoneFixo = telefone;
		this.addCadastro = dataHora();
	}
	
	/*private String cripSenha(String senhaOriginal){
		String senhaSerializado = Base64.getEncoder().encodeToString(senhaOriginal.getBytes());
    	return senhaSerializado;
	}
	private String descripSenha(String senhaSerializado) {
		String senhaDeserializado;
	    senhaDeserializado = new String(Base64.getDecoder().decode(senhaSerializado));
	    return senhaDeserializado;
	}*/

	public int getIdPessoa() {
		return idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getIdImagem() {
		return idImagem;
	}

	public void setIdImagem(String idImagem) {
		this.idImagem = idImagem;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getAddCadastro() {
		return addCadastro;
	}

	public void setAddCadastro(String addCadastro) {
		this.addCadastro = addCadastro;
	}

	public String getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(String ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
	
	
	
	@Override
	public String toString() {
		return "Usuario [idPessoa=" + idPessoa + ", nome=" + nome + ", telefoneFixo=" + telefoneFixo
				+ ", telefoneCelular=" + telefoneCelular + ", email=" + email + ", senha=" + senha + ", idImagem="
				+ idImagem + ", cep=" + cep + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", uf="
				+ uf + ", latitude=" + latitude + ", longitude=" + longitude + ", addCadastro=" + addCadastro
				+ ", ultimoAcesso=" + ultimoAcesso + "]";
	}

	/**
	 * Sets the address information from an Address value object.
	 * Addresses the Data Clumps code smell by centralizing address field management.
	 */
	public void setAddress(Address address) {
		if (address == null) {
			return;
		}
		this.bairro = address.getBairro();
		this.cidade = address.getLocalidade();
		this.uf = address.getUf();
		this.rua = address.getLogradouro();
		this.latitude = (address.getLatitude() == 0.0) ? 0 : address.getLatitude();
		this.longitude = address.getLongitude();
		this.cep = address.getCep();
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
	 * Builder pattern implementation for Usuario.
	 * Addresses the Long Parameter List code smell.
	 * 
	 * Usage example:
	 * <pre>
	 * Usuario usuario = Usuario.builder()
	 *     .nome("João Silva")
	 *     .email("joao@email.com")
	 *     .telefoneCelular("11987654321")
	 *     .address(address)
	 *     .build();
	 * </pre>
	 */
	public static class Builder {
		private String nome;
		private String telefoneFixo;
		private String telefoneCelular;
		private String email;
		private String senha;
		private String idImagem;
		private String cep;
		private String rua;
		private String bairro;
		private String cidade;
		private String uf;
		private double latitude;
		private double longitude;
		
		public Builder nome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public Builder telefoneFixo(String telefoneFixo) {
			this.telefoneFixo = telefoneFixo;
			return this;
		}
		
		public Builder telefoneCelular(String telefoneCelular) {
			this.telefoneCelular = telefoneCelular;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder senha(String senha) {
			this.senha = senha;
			return this;
		}
		
		public Builder idImagem(String idImagem) {
			this.idImagem = idImagem;
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
		 * Builds and returns the Usuario instance.
		 * 
		 * @return Usuario instance with all configured fields
		 */
		public Usuario build() {
			Usuario usuario = new Usuario();
			usuario.nome = this.nome;
			usuario.telefoneFixo = this.telefoneFixo;
			usuario.telefoneCelular = this.telefoneCelular;
			usuario.email = this.email;
			usuario.senha = this.senha;
			usuario.idImagem = this.idImagem;
			usuario.cep = this.cep;
			usuario.rua = this.rua;
			usuario.bairro = this.bairro;
			usuario.cidade = this.cidade;
			usuario.uf = this.uf;
			usuario.latitude = this.latitude;
			usuario.longitude = this.longitude;
			usuario.addCadastro = usuario.dataHora();
			return usuario;
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
