package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator;

@Entity
@Table(name="ALMACEN")
public class Almacen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="AlmacenSEQ"
			)
	@GenericGenerator(
			name = "AlmacenSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ALMCN"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_ALMACEN", length=15, nullable=false)
	private String ID_ALMACEN;
	
	@Column(name="NOM_ALMACEN", length=150, nullable=false)
	private String NOM_ALMACEN;
	
	@Column(name="ESTADO", length=1, nullable=false)
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

	public String getID_ALMACEN() {
		return ID_ALMACEN;
	}

	public void setID_ALMACEN(String iD_ALMACEN) {
		ID_ALMACEN = iD_ALMACEN;
	}

	public String getNOM_ALMACEN() {
		return NOM_ALMACEN;
	}

	public void setNOM_ALMACEN(String nOM_ALMACEN) {
		NOM_ALMACEN = nOM_ALMACEN;
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
	

}
