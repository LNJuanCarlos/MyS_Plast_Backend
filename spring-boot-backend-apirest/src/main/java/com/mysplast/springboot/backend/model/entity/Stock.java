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
@Table(name="STOCK")
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false)
	private Long ID;
	
	@Column(name="CANTIDAD",nullable=false)
	private double CANTIDAD;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto id_PRODUCTO;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR", referencedColumnName = "ID_SECTOR")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sector id_SECTOR;


	public Long getID() {
		return ID;
	}


	public void setID(Long iD) {
		ID = iD;
	}


	public double getCANTIDAD() {
		return CANTIDAD;
	}


	public void setCANTIDAD(double cANTIDAD) {
		CANTIDAD = cANTIDAD;
	}


	public Producto getId_PRODUCTO() {
		return id_PRODUCTO;
	}


	public void setId_PRODUCTO(Producto id_PRODUCTO) {
		this.id_PRODUCTO = id_PRODUCTO;
	}


	public Sector getId_SECTOR() {
		return id_SECTOR;
	}


	public void setId_SECTOR(Sector id_SECTOR) {
		this.id_SECTOR = id_SECTOR;
	}
	

}
