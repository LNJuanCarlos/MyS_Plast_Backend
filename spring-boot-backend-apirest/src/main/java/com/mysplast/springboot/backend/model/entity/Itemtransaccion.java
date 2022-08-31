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
@Table(name="ITEMTRANSACCION")
public class Itemtransaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_ITEMTRANSACCION", nullable=false)
	private Long ID_ITEMTRANSACCION;
	
	@Column(name="LINEA", nullable = false)
	private int LINEA;
	
	@Column(name="CANTIDAD", nullable = false)
	private float CANTIDAD;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto id_PRODUCTO;
	
	public Itemtransaccion() {
	}

	public Long getID_ITEMTRANSACCION() {
		return ID_ITEMTRANSACCION;
	}

	public void setID_ITEMTRANSACCION(Long iD_ITEMTRANSACCION) {
		ID_ITEMTRANSACCION = iD_ITEMTRANSACCION;
	}

	public int getLINEA() {
		return LINEA;
	}

	public void setLINEA(int lINEA) {
		LINEA = lINEA;
	}

	public float getCANTIDAD() {
		return CANTIDAD;
	}

	public void setCANTIDAD(float cANTIDAD) {
		CANTIDAD = cANTIDAD;
	}

	public Producto getId_PRODUCTO() {
		return id_PRODUCTO;
	}

	public void setId_PRODUCTO(Producto id_PRODUCTO) {
		this.id_PRODUCTO = id_PRODUCTO;
	}


}
