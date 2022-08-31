package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator;

@Entity
@Table(name="TRANSACCION")
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="TransaccionSEQ"
			)
	@GenericGenerator(
			name = "TransaccionSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TRNSC"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_TRAN", length=15, nullable=false)
	private String ID_TRAN;
	
	@Column(name="NRO_TRAN", length=15, nullable=true)
	private String NRO_TRAN;
	
	@Column(name="FECHA_TRAN", nullable=false)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECHATRAN;
	
	@Column(name="DESC_TRAN", length=15, nullable=true)
	private String DESC_TRAN;
	
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
	@JoinColumn(name="ID_SECTOR", referencedColumnName = "ID_SECTOR")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sector id_SECTOR;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERSONA", referencedColumnName = "ID_PERSONA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Natural id_PERSONA;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPOTRANSACCION", referencedColumnName = "ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Tipotransaccion id_TIPOTRANSACCION;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CATEGORIATRANSACCION", referencedColumnName = "ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Categoriatransaccion categoriatransaccion;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="ID_TRAN")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<Itemtransaccion> items;
	
	public Transaccion() {
		this.items = new ArrayList<Itemtransaccion>();
	}

	public String getID_TRAN() {
		return ID_TRAN;
	}

	public void setID_TRAN(String iD_TRAN) {
		ID_TRAN = iD_TRAN;
	}

	public String getNRO_TRAN() {
		return NRO_TRAN;
	}

	public void setNRO_TRAN(String nRO_TRAN) {
		NRO_TRAN = nRO_TRAN;
	}

	public String getFECHATRAN() {
		return FECHATRAN;
	}

	public void setFECHATRAN(String fECHATRAN) {
		FECHATRAN = fECHATRAN;
	}

	public List<Itemtransaccion> getItems() {
		return items;
	}

	public void setItems(List<Itemtransaccion> items) {
		this.items = items;
	}

	public String getDESC_TRAN() {
		return DESC_TRAN;
	}

	public void setDESC_TRAN(String dESC_TRAN) {
		DESC_TRAN = dESC_TRAN;
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

	public Tipotransaccion getId_TIPOTRANSACCION() {
		return id_TIPOTRANSACCION;
	}

	public void setId_TIPOTRANSACCION(Tipotransaccion id_TIPOTRANSACCION) {
		this.id_TIPOTRANSACCION = id_TIPOTRANSACCION;
	}

	public Categoriatransaccion getCategoriatransaccion() {
		return categoriatransaccion;
	}

	public void setCategoriatransaccion(Categoriatransaccion categoriatransaccion) {
		this.categoriatransaccion = categoriatransaccion;
	}


}
