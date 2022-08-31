package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="JURIDICA")
@PrimaryKeyJoinColumn(referencedColumnName = "ID_PERSONA")
public class Juridica extends Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="RAZONSOCIAL", length = 250, nullable=false)
	private String RAZONSOCIAL;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="REPRESENTANTE", referencedColumnName = "ID_PERSONA", nullable=true)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Natural REPRESENTANTE;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD", nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Actividad id_ACTIVIDAD;

	public String getRAZONSOCIAL() {
		return RAZONSOCIAL;
	}

	public void setRAZONSOCIAL(String rAZONSOCIAL) {
		RAZONSOCIAL = rAZONSOCIAL;
	}

	public Natural getREPRESENTANTE() {
		return REPRESENTANTE;
	}

	public void setREPRESENTANTE(Natural rEPRESENTANTE) {
		REPRESENTANTE = rEPRESENTANTE;
	}

	public Actividad getId_ACTIVIDAD() {
		return id_ACTIVIDAD;
	}

	public void setId_ACTIVIDAD(Actividad id_ACTIVIDAD) {
		this.id_ACTIVIDAD = id_ACTIVIDAD;
	}

}
