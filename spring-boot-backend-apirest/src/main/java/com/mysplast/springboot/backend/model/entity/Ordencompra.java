package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator;

@Entity
@Table(name="ORDENCOMPRA")
public class Ordencompra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="OrdencompraSEQ"
			)
	@GenericGenerator(
			name = "OrdencompraSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ORDEN"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_ORDENCOMPRA", length = 15, nullable = false)
	private String ID_ORDENCOMPRA;
	
	@Column(name="FECHA", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String FECHA;
	
	@Column(name="NRO_ORDENCOMPRA", length = 20, nullable = true)
	private String NRO_ORDENCOMPRA;
	
	@Column(name="MONEDA", length = 20, nullable = false)
	private String MONEDA;
	
	@Column(name="TIPOPAGO", length = 50, nullable = false)
	private String TIPOPAGO;
	
	@Column(name="SUBTOTAL", nullable = false)
	private double SUBTOTAL;
	
	@Column(name="IGV", nullable = false)
	private double IGV;
	
	@Column(name="TOTAL", nullable = false)
	private double TOTAL;
	
	@Column(name="ESTADO", length = 1, nullable = true)
	private String ESTADO;
	
	@Column(name="DESC_ORDENCOMPRA", length = 250, nullable = true)
	private String DESC_ORDENCOMPRA;
	
	@Column(name="REG_USER", length=25, nullable=true)
	private String REG_USER;
	
	@Column(name="FECH_REG_USER", nullable=true)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECH_REG_USER;
	
	@Column(name="MOD_USER", length=25, nullable=true)
	private String MOD_USER;
	
	@Column(name="FECH_MOD_USER", nullable=true)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECH_MOD_USER;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPOTRANSACCION", referencedColumnName = "ID")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Tipotransaccion id_TIPOTRANSACCION;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="SECTOR", referencedColumnName = "ID_SECTOR")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Sector sector;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="EMPLEADO", referencedColumnName = "ID_PERSONA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Natural empleado;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="PROVEEDOR", referencedColumnName = "ID_PERSONA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Juridica proveedor;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ORDENCOMPRA")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<ItemOrdencompra> items;

	public String getID_ORDENCOMPRA() {
		return ID_ORDENCOMPRA;
	}

	public void setID_ORDENCOMPRA(String iD_ORDENCOMPRA) {
		ID_ORDENCOMPRA = iD_ORDENCOMPRA;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	public String getNRO_ORDENCOMPRA() {
		return NRO_ORDENCOMPRA;
	}

	public void setNRO_ORDENCOMPRA(String nRO_ORDENCOMPRA) {
		NRO_ORDENCOMPRA = nRO_ORDENCOMPRA;
	}

	public String getMONEDA() {
		return MONEDA;
	}

	public void setMONEDA(String mONEDA) {
		MONEDA = mONEDA;
	}

	public String getTIPOPAGO() {
		return TIPOPAGO;
	}

	public void setTIPOPAGO(String tIPOPAGO) {
		TIPOPAGO = tIPOPAGO;
	}

	public double getSUBTOTAL() {
		return SUBTOTAL;
	}

	public void setSUBTOTAL(double sUBTOTAL) {
		SUBTOTAL = sUBTOTAL;
	}

	public double getIGV() {
		return IGV;
	}

	public void setIGV(double iGV) {
		IGV = iGV;
	}

	public double getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(double tOTAL) {
		TOTAL = tOTAL;
	}

	public String getESTADO() {
		return ESTADO;
	}

	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}

	public String getDESC_ORDENCOMPRA() {
		return DESC_ORDENCOMPRA;
	}

	public void setDESC_ORDENCOMPRA(String dESC_ORDENCOMPRA) {
		DESC_ORDENCOMPRA = dESC_ORDENCOMPRA;
	}

	public String getREG_USER() {
		return REG_USER;
	}

	public void setREG_USER(String rEG_USER) {
		REG_USER = rEG_USER;
	}

	public String getFECH_REG_USER() {
		return FECH_REG_USER;
	}

	public void setFECH_REG_USER(String fECH_REG_USER) {
		FECH_REG_USER = fECH_REG_USER;
	}

	public String getMOD_USER() {
		return MOD_USER;
	}

	public void setMOD_USER(String mOD_USER) {
		MOD_USER = mOD_USER;
	}

	public String getFECH_MOD_USER() {
		return FECH_MOD_USER;
	}

	public void setFECH_MOD_USER(String fECH_MOD_USER) {
		FECH_MOD_USER = fECH_MOD_USER;
	}

	public Tipotransaccion getId_TIPOTRANSACCION() {
		return id_TIPOTRANSACCION;
	}

	public void setId_TIPOTRANSACCION(Tipotransaccion id_TIPOTRANSACCION) {
		this.id_TIPOTRANSACCION = id_TIPOTRANSACCION;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Natural getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Natural empleado) {
		this.empleado = empleado;
	}

	public Juridica getProveedor() {
		return proveedor;
	}

	public void setProveedor(Juridica proveedor) {
		this.proveedor = proveedor;
	}

	public List<ItemOrdencompra> getItems() {
		return items;
	}

	public void setItems(List<ItemOrdencompra> items) {
		this.items = items;
	}
	

}
