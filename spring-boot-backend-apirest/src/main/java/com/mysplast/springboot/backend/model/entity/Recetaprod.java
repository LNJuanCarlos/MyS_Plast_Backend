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
@Table(name="RECETAPROD")
public class Recetaprod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="RecetaSEQ"
			)
	@GenericGenerator(
			name = "RecetaSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "RECTA"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_RECETA", length = 15, nullable = false)
	private String ID_RECETA;
	
	@Column(name="NRO_RECETA", length = 20, nullable = true)
	private String NRO_RECETA;
	
	@Column(name="NOM_RECETA", length = 250, nullable = false)
	private String NOM_RECETA;
	
	@Column(name="FECHA", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String FECHA;
	
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
	
	@Column(name="ESTADO", length = 1, nullable = true)
	private String ESTADO;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto id_PRODUCTO;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="ID_RECETA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<ItemRecetaprod> items;

	public List<ItemRecetaprod> getItems() {
		return items;
	}

	public void setItems(List<ItemRecetaprod> items) {
		this.items = items;
	}

	public String getID_RECETA() {
		return ID_RECETA;
	}

	public void setID_RECETA(String iD_RECETA) {
		ID_RECETA = iD_RECETA;
	}

	public String getNRO_RECETA() {
		return NRO_RECETA;
	}

	public void setNRO_RECETA(String nRO_RECETA) {
		NRO_RECETA = nRO_RECETA;
	}

	public String getNOM_RECETA() {
		return NOM_RECETA;
	}

	public void setNOM_RECETA(String nOM_RECETA) {
		NOM_RECETA = nOM_RECETA;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
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

	public String getESTADO() {
		return ESTADO;
	}

	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}

	public Producto getId_PRODUCTO() {
		return id_PRODUCTO;
	}

	public void setId_PRODUCTO(Producto id_PRODUCTO) {
		this.id_PRODUCTO = id_PRODUCTO;
	}

}
