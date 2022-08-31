package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVIDAD")
public class Actividad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_ACTIVIDAD", nullable=false)
	private Long ID_ACTIVIDAD;
	
	@Column(name="NOM_ACTIVIDAD", nullable=false)
	private String NOM_ACTIVIDAD;

	public Long getID_ACTIVIDAD() {
		return ID_ACTIVIDAD;
	}

	public void setID_ACTIVIDAD(Long iD_ACTIVIDAD) {
		ID_ACTIVIDAD = iD_ACTIVIDAD;
	}

	public String getNOM_ACTIVIDAD() {
		return NOM_ACTIVIDAD;
	}

	public void setNOM_ACTIVIDAD(String nOM_ACTIVIDAD) {
		NOM_ACTIVIDAD = nOM_ACTIVIDAD;
	}
	

}
