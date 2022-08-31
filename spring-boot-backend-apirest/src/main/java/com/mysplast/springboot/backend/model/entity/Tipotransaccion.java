package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIPOTRANSACCION")
public class Tipotransaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false)
	private Long ID;
	
	@Column(name="NOM_TIPOTRANSACCION",length=250, nullable=false)
	private String NOM_TIPOTRANSACCION;
	
	@Column(name="TIPO",length=5, nullable=false)
	private String TIPO;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getNOM_TIPOTRANSACCION() {
		return NOM_TIPOTRANSACCION;
	}

	public void setNOM_TIPOTRANSACCION(String nOM_TIPOTRANSACCION) {
		NOM_TIPOTRANSACCION = nOM_TIPOTRANSACCION;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String tIPO) {
		TIPO = tIPO;
	}
	

}
