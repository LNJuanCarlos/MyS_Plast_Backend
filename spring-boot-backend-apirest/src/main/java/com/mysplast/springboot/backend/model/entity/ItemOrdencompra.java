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
@Table(name="ITEMORDENCOMPRA")
public class ItemOrdencompra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_ITEMORDENCOMPRA", length = 15, nullable = false)
	private String ID_ITEMORDENCOMPRA;
	
	@Column(name="LINE", nullable = false)
	private int LINE;
	
	@Column(name="CANTIDAD", nullable = false)
	private double CANTIDAD;
	
	@Column(name="PRECIO", nullable = false)
	private double PRECIO;
	
	@Column(name="TOTAL", nullable = false)
	private double TOTAL;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto id_PRODUCTO;

	public String getID_ITEMORDENCOMPRA() {
		return ID_ITEMORDENCOMPRA;
	}

	public void setID_ITEMORDENCOMPRA(String iD_ITEMORDENCOMPRA) {
		ID_ITEMORDENCOMPRA = iD_ITEMORDENCOMPRA;
	}

	public int getLINE() {
		return LINE;
	}

	public void setLINE(int lINE) {
		LINE = lINE;
	}

	public double getCANTIDAD() {
		return CANTIDAD;
	}

	public void setCANTIDAD(double cANTIDAD) {
		CANTIDAD = cANTIDAD;
	}

	public double getPRECIO() {
		return PRECIO;
	}

	public void setPRECIO(double pRECIO) {
		PRECIO = pRECIO;
	}

	public double getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(double tOTAL) {
		TOTAL = tOTAL;
	}

	public Producto getId_PRODUCTO() {
		return id_PRODUCTO;
	}

	public void setId_PRODUCTO(Producto id_PRODUCTO) {
		this.id_PRODUCTO = id_PRODUCTO;
	}
	

}
