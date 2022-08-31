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
@Table(name="ITEMORDENPROD")
public class ItemOrdenprod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_ITEMORDENPROD", length = 15, nullable = false)
	private Long ID_ITEMORDENPROD;
	
	@Column(name="CANTIDAD", nullable = false)
	private double CANTIDAD;
	
	@Column(name="LINE", nullable = false)
	private int LINE;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto id_PRODUCTO;

	public Long getID_ITEMORDENPROD() {
		return ID_ITEMORDENPROD;
	}

	public void setID_ITEMORDENPROD(Long iD_ITEMORDENPROD) {
		ID_ITEMORDENPROD = iD_ITEMORDENPROD;
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

	public Producto getId_PRODUCTO() {
		return id_PRODUCTO;
	}

	public void setId_PRODUCTO(Producto id_PRODUCTO) {
		this.id_PRODUCTO = id_PRODUCTO;
	}

	

}
