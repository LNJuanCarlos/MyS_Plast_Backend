package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator;

@Entity
@Table(name="ORDENPROD")
public class Ordenprod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="OrdenprodSEQ"
			)
	@GenericGenerator(
			name = "OrdenprodSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ORPRD"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_ORDENPROD", length = 15, nullable = false)
	private String ID_ORDENPROD;
	
	@Column(name="NRO_ORDENPROD", length=20, nullable = true)
	private String NRO_ORDENPROD;
	
	@Column(name="DESC_ORDENPROD", length=250, nullable = false)
	private String DESC_ORDENPROD;
	
	@Column(name="FECHA", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String FECHA;
	
	@Column(name="ESTADO", length = 1, nullable = true)
	private String ESTADO;
	
	@Column(name="REG_USER", length=25, nullable=true)
	private String REG_USER;
	
	@Column(name="FECH_REG_USER", nullable=true)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECH_REG_USER;
	
	@Column(name="MOD_USER", length=25, nullable=true)
	private String MOD_USER;
	
	@Column(name="FECH_MOD_USER", nullable=true)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECH_MOD_USER;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR", referencedColumnName = "ID_SECTOR")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sector id_SECTOR;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTORINSUMOS", referencedColumnName = "ID_SECTOR")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sector id_SECTORINSUMOS;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERSONA", referencedColumnName = "ID_PERSONA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Natural id_PERSONA;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ORDENPROD")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<ItemOrdenprod> items;

	
	public Sector getId_SECTORINSUMOS() {
		return id_SECTORINSUMOS;
	}

	public void setId_SECTORINSUMOS(Sector id_SECTORINSUMOS) {
		this.id_SECTORINSUMOS = id_SECTORINSUMOS;
	}

	public String getID_ORDENPROD() {
		return ID_ORDENPROD;
	}

	public void setID_ORDENPROD(String iD_ORDENPROD) {
		ID_ORDENPROD = iD_ORDENPROD;
	}

	public String getNRO_ORDENPROD() {
		return NRO_ORDENPROD;
	}

	public void setNRO_ORDENPROD(String nRO_ORDENPROD) {
		NRO_ORDENPROD = nRO_ORDENPROD;
	}

	public String getDESC_ORDENPROD() {
		return DESC_ORDENPROD;
	}

	public void setDESC_ORDENPROD(String dESC_ORDENPROD) {
		DESC_ORDENPROD = dESC_ORDENPROD;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	public String getESTADO() {
		return ESTADO;
	}

	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}

	public String getREG_USER() {
		return REG_USER;
	}

	public void setREG_USER(String rEG_USER) {
		REG_USER = rEG_USER;
	}

	public String getFECH_REG_USER() {
		return FECH_REG_USER;
	}

	public void setFECH_REG_USER(String fECH_REG_USER) {
		FECH_REG_USER = fECH_REG_USER;
	}

	public String getMOD_USER() {
		return MOD_USER;
	}

	public void setMOD_USER(String mOD_USER) {
		MOD_USER = mOD_USER;
	}

	public String getFECH_MOD_USER() {
		return FECH_MOD_USER;
	}

	public void setFECH_MOD_USER(String fECH_MOD_USER) {
		FECH_MOD_USER = fECH_MOD_USER;
	}

	public Sector getId_SECTOR() {
		return id_SECTOR;
	}

	public void setId_SECTOR(Sector id_SECTOR) {
		this.id_SECTOR = id_SECTOR;
	}

	public Natural getId_PERSONA() {
		return id_PERSONA;
	}

	public void setId_PERSONA(Natural id_PERSONA) {
		this.id_PERSONA = id_PERSONA;
	}

	public List<ItemOrdenprod> getItems() {
		return items;
	}

	public void setItems(List<ItemOrdenprod> items) {
		this.items = items;
	}
	
	

}
