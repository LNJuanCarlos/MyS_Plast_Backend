package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CENTROCOSTO")
public class Centrocosto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_CENTROCOSTO", nullable=false)
	private Long ID_CENTROCOSTO;
	
	@Column(name="NOM_CENTROCOSTO", length=150, nullable=false)
	private String NOM_CENTROCOSTO;

	public Long getID_CENTROCOSTO() {
		return ID_CENTROCOSTO;
	}

	public void setID_CENTROCOSTO(Long iD_CENTROCOSTO) {
		ID_CENTROCOSTO = iD_CENTROCOSTO;
	}

	public String getNOM_CENTROCOSTO() {
		return NOM_CENTROCOSTO;
	}

	public void setNOM_CENTROCOSTO(String nOM_CENTROCOSTO) {
		NOM_CENTROCOSTO = nOM_CENTROCOSTO;
	}
	

}
