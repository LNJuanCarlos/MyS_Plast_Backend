package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIPODOC")
public class Tipodoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_TIPODOC", nullable=false)
	private Long ID_TIPODOC;
	
	@Column(name="NOM_TIPODOC",length=25,nullable=false)
	private String NOM_TIPODOC;
	
	@Column(name="CANT_TIPODOC", nullable=true)
	private String CANT_TIPODOC;

	public Long getID_TIPODOC() {
		return ID_TIPODOC;
	}

	public void setID_TIPODOC(Long iD_TIPODOC) {
		ID_TIPODOC = iD_TIPODOC;
	}

	public String getNOM_TIPODOC() {
		return NOM_TIPODOC;
	}

	public void setNOM_TIPODOC(String nOM_TIPODOC) {
		NOM_TIPODOC = nOM_TIPODOC;
	}

	public String getCANT_TIPODOC() {
		return CANT_TIPODOC;
	}

	public void setCANT_TIPODOC(String cANT_TIPODOC) {
		CANT_TIPODOC = cANT_TIPODOC;
	}


}
