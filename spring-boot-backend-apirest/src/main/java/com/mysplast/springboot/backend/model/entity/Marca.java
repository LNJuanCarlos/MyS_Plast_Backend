package com.mysplast.springboot.backend.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator;

@Entity
@Table(name="MARCA")
public class Marca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="MarcaSEQ"
			)
	@GenericGenerator(
			name = "MarcaSEQ",
			strategy = "com.mysplast.springboot.backend.generator.StringPrefixedSequenceIdGenerator",
			parameters = {
					@Parameter(name= StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "MARCA"),
					@Parameter(name= StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%010d")
				}
			)
	@Column(name="ID_MARCA", length=15, nullable=false)
	private String ID_MARCA;
	
	@Column(name="NOM_MARCA", length=15, nullable=false)
	private String NOM_MARCA;
	
	@Column(name="REG_USER", length=25, nullable=true)
	private String REG_USER;
	
	@Column(name="FECH_REG_USER", nullable=true)
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String FECH_REG_USER;

	public String getID_MARCA() {
		return ID_MARCA;
	}

	public void setID_MARCA(String iD_MARCA) {
		ID_MARCA = iD_MARCA;
	}

	public String getNOM_MARCA() {
		return NOM_MARCA;
	}

	public void setNOM_MARCA(String nOM_MARCA) {
		NOM_MARCA = nOM_MARCA;
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
	
}
