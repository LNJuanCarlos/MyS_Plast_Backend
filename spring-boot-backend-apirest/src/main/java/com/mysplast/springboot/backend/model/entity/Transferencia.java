package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="TRANSFERENCIA")
@PrimaryKeyJoinColumn(referencedColumnName = "ID_TRAN")
public class Transferencia extends Transaccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTORDEST", referencedColumnName = "ID_SECTOR")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sector id_SECTORDEST;

	public Sector getId_SECTORDEST() {
		return id_SECTORDEST;
	}

	public void setId_SECTORDEST(Sector id_SECTORDEST) {
		this.id_SECTORDEST = id_SECTORDEST;
	}

}
