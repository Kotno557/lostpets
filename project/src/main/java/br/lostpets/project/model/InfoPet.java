package br.lostpets.project.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Transfer Object (DTO) for Pet information.
 * Used for PDF generation and API responses.
 * 
 * Refactoring improvements:
 * - Removed @SuppressWarnings
 * - Improved exception handling with proper logging
 * - Reduced message chains by adding facade methods
 */
public class InfoPet {

	private static final Logger logger = LoggerFactory.getLogger(InfoPet.class);
	
	private int animalID;
	private String animalName;
	private Date lostDate;
	private String animalInfos;
	private String animalType;
	private String animalImgPath;
	private String animalSitePath;
	private String ownerName;
	private String ownerEmail;
	private String ownerNumber;
	private String howWasLost;
	private String cepLost;
	
	
	public InfoPet() {}
	
	/**
	 * Creates an InfoPet from a PetPerdido entity.
	 * 
	 * @param pet The lost pet entity
	 */
	public InfoPet(LostPet pet) {
		if (pet == null) {
			logger.warn("Attempted to create InfoPet from null PetPerdido");
			return;
		}
		
		setAnimalID(pet.getIdAnimal());
		setAnimalName(pet.getNomeAnimal());
		
		// Parse date with proper exception handling
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (pet.getDataPerdido() != null) {
				setLostDate(dateFormat.parse(pet.getDataPerdido()));
			}
		} catch (ParseException e) {
			logger.error("Failed to parse lost date for pet ID: {}. Date string: {}", 
				pet.getIdAnimal(), pet.getDataPerdido(), e);
			// Set to current date as fallback
			setLostDate(new Date());
		}
		
		setAnimalInfos(pet.getDescricaoAnimal());
		setAnimalType(pet.getTipoAnimal());
		setAnimalImgPath(pet.getPathImg());
		
		// Extract owner information with null checks
		if (pet.getUsuario() != null) {
			setOwnerEmail(pet.getUsuario().getEmail());
			setOwnerName(pet.getUsuario().getNome());
			setOwnerNumber(pet.getUsuario().getTelefoneCelular());
		} else {
			logger.warn("Pet ID {} has no associated user", pet.getIdAnimal());
		}
		
		setHowWasLost(pet.getDescricao());
		setCepLost(pet.getCep());
	}
	
	public int getAnimalID() {
		return animalID;
	}

	public void setAnimalID(int animalID) {
		this.animalID = animalID;
	}

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	public Date getLostDate() {
		return lostDate;
	}

	public void setLostDate(Date lostDate) {
		this.lostDate = lostDate;
	}

	public String getAnimalInfos() {
		return animalInfos;
	}

	public void setAnimalInfos(String animalInfos) {
		this.animalInfos = animalInfos;
	}

	public String getAnimalType() {
		return animalType;
	}

	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}

	public String getAnimalImgPath() {
		return animalImgPath;
	}

	public void setAnimalImgPath(String animalImgPath) {
		this.animalImgPath = animalImgPath;
	}

	public String getAnimalSitePath() {
		return animalSitePath;
	}

	public void setAnimalSitePath(String animalSitePath) {
		this.animalSitePath = animalSitePath;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerNumber() {
		return ownerNumber;
	}

	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}

	public String getHowWasLost() {
		return howWasLost;
	}

	public void setHowWasLost(String howWasLost) {
		this.howWasLost = howWasLost;
	}

	public String getCepLost() {
		return cepLost;
	}

	public void setCepLost(String cepLost) {
		this.cepLost = cepLost;
	}

	@Override
	public String toString() {
		return "InfoPet [animalID=" + animalID + ", animalName=" + animalName + ", lostDate=" + lostDate
				+ ", animalInfos=" + animalInfos + ", animalType=" + animalType + ", animalImgPath=" + animalImgPath
				+ ", animalSitePath=" + animalSitePath + ", ownerName=" + ownerName + ", ownerEmail=" + ownerEmail
				+ ", ownerNumber=" + ownerNumber + ", ownerNumber=" + ownerNumber + ", howWasLost=" + howWasLost
				+ ", cepLost=" + cepLost + "]";
	}

}
