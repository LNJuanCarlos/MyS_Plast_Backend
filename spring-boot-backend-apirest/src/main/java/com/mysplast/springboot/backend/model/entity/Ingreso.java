package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="INGRESO")
@PrimaryKeyJoinColumn(referencedColumnName = "ID_TRAN")
public class Ingreso extends Transaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="NRO_ORDEN", length=15, nullable=true)
	private String NRO_ORDEN;
	
	@Column(name="GUIA_REF", length=15, nullable=true)
	private String GUIA_REF;
	
	@Column(name="FECHA_INGRESO", nullable=true)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECHA_INGRESO;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROVEEDOR", referencedColumnName = "ID_PERSONA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Juridica proveedor;

	public String getNRO_ORDEN() {
		return NRO_ORDEN;
	}

	public void setNRO_ORDEN(String nRO_ORDEN) {
		NRO_ORDEN = nRO_ORDEN;
	}

	public String getGUIA_REF() {
		return GUIA_REF;
	}

	public void setGUIA_REF(String gUIA_REF) {
		GUIA_REF = gUIA_REF;
	}

	public String getFECHA_INGRESO() {
		return FECHA_INGRESO;
	}

	public void setFECHA_INGRESO(String fECHA_INGRESO) {
		FECHA_INGRESO = fECHA_INGRESO;
	}

	public Juridica getProveedor() {
		return proveedor;
	}

	public void setProveedor(Juridica proveedor) {
		this.proveedor = proveedor;
	}
}
