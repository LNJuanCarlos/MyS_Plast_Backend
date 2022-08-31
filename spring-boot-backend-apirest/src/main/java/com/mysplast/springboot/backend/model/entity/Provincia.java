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
@Table(name="PROVINCIA")
public class Provincia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id_provincia", length=8, nullable=false)
	private String id_provincia;
	
	@Column(name="ine_dprovincia",length=8, nullable=false)
	private String ine_dprovincia;
	
	@Column(name="nom_provincia",length=50, nullable=false)
	private String nom_provincia;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="id_departamento", referencedColumnName = "id_departamento")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Departamento id_departamento;

	public String getId_provincia() {
		return id_provincia;
	}

	public void setId_provincia(String id_provincia) {
		this.id_provincia = id_provincia;
	}

	public String getIne_dprovincia() {
		return ine_dprovincia;
	}

	public void setIne_dprovincia(String ine_dprovincia) {
		this.ine_dprovincia = ine_dprovincia;
	}

	public String getNom_provincia() {
		return nom_provincia;
	}

	public void setNom_provincia(String nom_provincia) {
		this.nom_provincia = nom_provincia;
	}

	public Departamento getId_departamento() {
		return id_departamento;
	}

	public void setId_departamento(Departamento id_departamento) {
		this.id_departamento = id_departamento;
	}


}
