package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DEPARTAMENTO")
public class Departamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_departamento",length = 8,nullable = false)
	private String id_departamento;
	
	@Column(name="ine_departamento", length=8, nullable=false)
	private String ine_departamento;
	
	@Column(name="nom_departamento", length=50, nullable=false)
	private String nom_departamento;

	public String getId_departamento() {
		return id_departamento;
	}

	public void setId_departamento(String id_departamento) {
		this.id_departamento = id_departamento;
	}

	public String getIne_departamento() {
		return ine_departamento;
	}

	public void setIne_departamento(String ine_departamento) {
		this.ine_departamento = ine_departamento;
	}

	public String getNom_departamento() {
		return nom_departamento;
	}

	public void setNom_departamento(String nom_departamento) {
		this.nom_departamento = nom_departamento;
	}
	

}
