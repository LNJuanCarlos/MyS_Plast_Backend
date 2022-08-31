package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="NATURAL")
@PrimaryKeyJoinColumn(referencedColumnName = "ID_PERSONA")
public class Natural extends Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="NOMBRES", length=150, nullable=false)
	private String NOMBRES;
	
	@Column(name="APE_PAT", length=50, nullable=false)
	private String APE_PAT;
	
	@Column(name="APE_MAT", length=50, nullable=false)
	private String APE_MAT;
	
	@Column(name="FLAG_TRABAJADOR", length=1, nullable=false)
	private String FLAG_TRABAJADOR;

	public String getNOMBRES() {
		return NOMBRES;
	}

	public void setNOMBRES(String nOMBRES) {
		NOMBRES = nOMBRES;
	}

	public String getAPE_PAT() {
		return APE_PAT;
	}

	public void setAPE_PAT(String aPE_PAT) {
		APE_PAT = aPE_PAT;
	}

	public String getAPE_MAT() {
		return APE_MAT;
	}

	public void setAPE_MAT(String aPE_MAT) {
		APE_MAT = aPE_MAT;
	}

	public String getFLAG_TRABAJADOR() {
		return FLAG_TRABAJADOR;
	}

	public void setFLAG_TRABAJADOR(String fLAG_TRABAJADOR) {
		FLAG_TRABAJADOR = fLAG_TRABAJADOR;
	}
	
}
