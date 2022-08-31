package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.TransferenciaDao;
import com.mysplast.springboot.backend.model.entity.Transferencia;

@Service
@Transactional
public class TransferenciaService {
	
	@Autowired
	private TransferenciaDao transferenciarepo;
	
	public List<Transferencia> listarTransferencias(){
		return (List<Transferencia>) transferenciarepo.findAll();
	}
	
	public List<Transferencia> listarTop50Transferencia(){
		return (List<Transferencia>) transferenciarepo.findTop50ByOrderByFECHATRANDesc();
	}
	
	public Transferencia buscarTransferenciaId(String id){
		return transferenciarepo.findById(id).get();
	}
	
	public Transferencia grabarTransferencia(Transferencia whtransaferencia) {
		return transferenciarepo.save(whtransaferencia);
	}
	
	public void eliminarTransferencia(String id) {
		transferenciarepo.deleteById(id);
	}
	
	public List<Transferencia> filtroTransferencia(String subalmacen, String almacen, String fecha1, String fecha2){
		return (List<Transferencia>) transferenciarepo.filtroWhingreso(subalmacen, almacen, fecha1, fecha2);
	}

}
