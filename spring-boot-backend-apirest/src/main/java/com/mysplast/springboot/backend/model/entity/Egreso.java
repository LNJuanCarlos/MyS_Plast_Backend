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
@Table(name="EGRESO")
@PrimaryKeyJoinColumn(referencedColumnName = "ID_TRAN")
public class Egreso extends Transaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CENTROCOSTO", referencedColumnName = "ID_CENTROCOSTO")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Centrocosto id_CENTROCOSTO;
	
	
	@Column(name="NRO_ORDEN", length=20, nullable=true)
	private String NRO_ORDEN;

	public Centrocosto getId_CENTROCOSTO() {
		return id_CENTROCOSTO;
	}

	public void setId_CENTROCOSTO(Centrocosto id_CENTROCOSTO) {
		this.id_CENTROCOSTO = id_CENTROCOSTO;
	}

	public String getNRO_ORDEN() {
		return NRO_ORDEN;
	}

	public void setNRO_ORDEN(String nRO_ORDEN) {
		NRO_ORDEN = nRO_ORDEN;
	}

	

}
