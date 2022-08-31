	package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator;

@Entity
@Table(name="PERSONA")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="PersonaSEQ"
			)
	@GenericGenerator(
			name = "PersonaSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PERSN"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_PERSONA", length=15, nullable=false)
	private String ID_PERSONA;
	
	@Column(name="NRODOC", length=15, nullable=false)
	private String NRODOC;
	
	@Column(name="TELEFONO", length=15, nullable=false)
	private String TELEFONO;
	
	@Column(name="CORREO", length=100, nullable=false)
	private String CORREO;
	
	@Column(name="DIRECCION", length=250, nullable=false)
	private String DIRECCION;
	
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPODOC", referencedColumnName = "ID_TIPODOC")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Tipodoc id_TIPODOC;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISTRITO", referencedColumnName = "id_distrito")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Distrito id_DISTRITO;

	public String getID_PERSONA() {
		return ID_PERSONA;
	}

	public void setID_PERSONA(String iD_PERSONA) {
		ID_PERSONA = iD_PERSONA;
	}
	

	public String getNRODOC() {
		return NRODOC;
	}

	public void setNRODOC(String nRODOC) {
		NRODOC = nRODOC;
	}

	public String getTELEFONO() {
		return TELEFONO;
	}

	public void setTELEFONO(String tELEFONO) {
		TELEFONO = tELEFONO;
	}

	public String getCORREO() {
		return CORREO;
	}

	public void setCORREO(String cORREO) {
		CORREO = cORREO;
	}

	public String getDIRECCION() {
		return DIRECCION;
	}

	public void setDIRECCION(String dIRECCION) {
		DIRECCION = dIRECCION;
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

	public Tipodoc getId_TIPODOC() {
		return id_TIPODOC;
	}

	public void setId_TIPODOC(Tipodoc id_TIPODOC) {
		this.id_TIPODOC = id_TIPODOC;
	}

	public Distrito getId_DISTRITO() {
		return id_DISTRITO;
	}

	public void setId_DISTRITO(Distrito id_DISTRITO) {
		this.id_DISTRITO = id_DISTRITO;
	}
	
}
