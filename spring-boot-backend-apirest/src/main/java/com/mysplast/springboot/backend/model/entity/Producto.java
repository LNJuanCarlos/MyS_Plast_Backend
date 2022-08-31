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
@Table(name="PRODUCTO")
public class Producto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="ProductoSEQ"
			)
	@GenericGenerator(
			name = "ProductoSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PRODT"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_PRODUCTO", length=15, nullable=false)
	private String ID_PRODUCTO;
	
	@Column(name="NOM_PRODUCTO", length=250, nullable=false)
	private String NOMBRE;
	
	@Column(name="DESC_PRODUCTO", length=500, nullable=true)
	private String DESC_PRODUCTO;
	
	@Column(name="ESTADO", length=1, nullable=false)
	private String ESTADO;
	
	@Column(name="FLAG_PRODUCCION", length=1, nullable=false)
	private String FLAG_PRODUCCION;
	
	@Column(name="FLAG_INSUMO", length=1, nullable=false)
	private String FLAG_INSUMO;
	
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
	@JoinColumn(name="ID_UNMEDIDA", referencedColumnName ="ID_UNMEDIDA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Unmedida id_UNMEDIDA;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CATEGORIA", referencedColumnName ="ID_CATEGORIA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Categoria id_CATEGORIA;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MARCA", referencedColumnName ="ID_MARCA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Marca id_MARCA;

	public String getID_PRODUCTO() {
		return ID_PRODUCTO;
	}

	public void setID_PRODUCTO(String iD_PRODUCTO) {
		ID_PRODUCTO = iD_PRODUCTO;
	}

	public String getNOMBRE() {
		return NOMBRE;
	}

	public void setNOMBRE(String nOMBRE) {
		NOMBRE = nOMBRE;
	}

	public String getDESC_PRODUCTO() {
		return DESC_PRODUCTO;
	}

	public void setDESC_PRODUCTO(String dESC_PRODUCTO) {
		DESC_PRODUCTO = dESC_PRODUCTO;
	}

	public String getFLAG_PRODUCCION() {
		return FLAG_PRODUCCION;
	}

	public void setFLAG_PRODUCCION(String fLAG_PRODUCCION) {
		FLAG_PRODUCCION = fLAG_PRODUCCION;
	}

	public String getFLAG_INSUMO() {
		return FLAG_INSUMO;
	}

	public void setFLAG_INSUMO(String fLAG_INSUMO) {
		FLAG_INSUMO = fLAG_INSUMO;
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

	public Categoria getId_CATEGORIA() {
		return id_CATEGORIA;
	}

	public void setId_CATEGORIA(Categoria id_CATEGORIA) {
		this.id_CATEGORIA = id_CATEGORIA;
	}

	public Unmedida getId_UNMEDIDA() {
		return id_UNMEDIDA;
	}

	public void setId_UNMEDIDA(Unmedida id_UNMEDIDA) {
		this.id_UNMEDIDA = id_UNMEDIDA;
	}

	public Marca getId_MARCA() {
		return id_MARCA;
	}

	public void setId_MARCA(Marca id_MARCA) {
		this.id_MARCA = id_MARCA;
	}

}
