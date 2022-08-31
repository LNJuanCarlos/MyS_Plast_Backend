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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="DISTRITO")
public class Distrito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String id_distrito;
	
	@Column(name="ine_distrito", length=8, nullable=false)
	private String ine_distrito;
	
	@Column(name="nom_distrito", length=8, nullable=false)
	private String nom_distrito;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_provincia", referencedColumnName = "id_provincia")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Provincia id_provincia;

	public String getId_distrito() {
		return id_distrito;
	}

	public void setId_distrito(String id_distrito) {
		this.id_distrito = id_distrito;
	}

	public String getIne_distrito() {
		return ine_distrito;
	}

	public void setIne_distrito(String ine_distrito) {
		this.ine_distrito = ine_distrito;
	}

	public String getNom_distrito() {
		return nom_distrito;
	}

	public void setNom_distrito(String nom_distrito) {
		this.nom_distrito = nom_distrito;
	}

	public Provincia getId_provincia() {
		return id_provincia;
	}

	public void setId_provincia(Provincia id_provincia) {
		this.id_provincia = id_provincia;
	}


}
