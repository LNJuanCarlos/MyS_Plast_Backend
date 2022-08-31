package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator;

@Entity
@Table(name="SECTOR")
public class Sector implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="SectorSEQ"
			)
	@GenericGenerator(
			name = "SectorSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "SECTR"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_SECTOR", length=15, nullable=false)
	private String ID_SECTOR;
	
	@Column(name="NOM_SECTOR", length=150, nullable=false)
	private String NOM_SECTOR;
	
	@Column(name="REG_USER", length=25, nullable=true)
	private String REG_USER;
	
	@Column(name="FECH_REG_USER", nullable=true)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECH_REG_USER;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ALMACEN", referencedColumnName ="ID_ALMACEN")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Almacen id_ALMACEN;

	public String getID_SECTOR() {
		return ID_SECTOR;
	}

	public void setID_SECTOR(String iD_SECTOR) {
		ID_SECTOR = iD_SECTOR;
	}

	public String getNOM_SECTOR() {
		return NOM_SECTOR;
	}

	public void setNOM_SECTOR(String nOM_SECTOR) {
		NOM_SECTOR = nOM_SECTOR;
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

	public Almacen getId_ALMACEN() {
		return id_ALMACEN;
	}

	public void setId_ALMACEN(Almacen id_ALMACEN) {
		this.id_ALMACEN = id_ALMACEN;
	}

}
