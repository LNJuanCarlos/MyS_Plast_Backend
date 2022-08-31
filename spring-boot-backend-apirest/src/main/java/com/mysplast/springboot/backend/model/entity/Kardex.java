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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="KARDEX")
public class Kardex implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long ID;
	
	@Column(name="CONDICION", length=50)
	private String CONDICION;
	
	@Column(name="OPERACION", length=1)
	private String OPERACION;
	
	@Column(name="FECHA")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String FECHA;
	
	@Column(name="CANTIDAD")
	private double CANTIDAD;
	
	@Column(name="STOCKFECHA")
	private double STOCKFECHA;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR", referencedColumnName = "ID_SECTOR")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sector id_SECTOR;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto id_PRODUCTO;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TRAN", referencedColumnName = "ID_TRAN")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Transaccion id_TRAN;


	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getCONDICION() {
		return CONDICION;
	}

	public void setCONDICION(String cONDICION) {
		CONDICION = cONDICION;
	}

	public String getOPERACION() {
		return OPERACION;
	}

	public void setOPERACION(String oPERACION) {
		OPERACION = oPERACION;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	public double getCANTIDAD() {
		return CANTIDAD;
	}

	public void setCANTIDAD(double cANTIDAD) {
		CANTIDAD = cANTIDAD;
	}

	public double getSTOCKFECHA() {
		return STOCKFECHA;
	}

	public void setSTOCKFECHA(double sTOCKFECHA) {
		STOCKFECHA = sTOCKFECHA;
	}

	public Sector getId_SECTOR() {
		return id_SECTOR;
	}

	public void setId_SECTOR(Sector id_SECTOR) {
		this.id_SECTOR = id_SECTOR;
	}

	public Producto getId_PRODUCTO() {
		return id_PRODUCTO;
	}

	public void setId_PRODUCTO(Producto id_PRODUCTO) {
		this.id_PRODUCTO = id_PRODUCTO;
	}

	public Transaccion getId_TRAN() {
		return id_TRAN;
	}

	public void setId_TRAN(Transaccion id_TRAN) {
		this.id_TRAN = id_TRAN;
	}
	
	

}
