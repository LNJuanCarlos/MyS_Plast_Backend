package com.mysplast.springboot.backend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysplast.springboot.backend.model.dao.IngresoDao;
import com.mysplast.springboot.backend.model.entity.Ingreso;
import com.mysplast.springboot.backend.model.entity.IngresosAlmacen;

@Service
@Transactional
public class IngresoService {
	
	@Autowired
	private IngresoDao ingresorepo;
	
	public List<Ingreso> listarIngresos(){
		return (List<Ingreso>) ingresorepo.findAll();
	}
	
	public List<Ingreso> listarTop50Ingresos(){
		return (List<Ingreso>) ingresorepo.findTop50ByOrderByFECHATRANDesc();
	}
	
	public Ingreso buscarIngresosId(String id){
		return ingresorepo.findById(id).get();
	}
	
	public Ingreso grabarIngreso(Ingreso ingreso) {
		return ingresorepo.save(ingreso);
	}
	
	public void eliminarIngreso(String id) {
		ingresorepo.deleteById(id);
	}
	
	public List<Ingreso> filtroIngreso(String subalmacen, String almacen, String fecha1, String fecha2){
		return (List<Ingreso>) ingresorepo.filtroIngreso(subalmacen, almacen, fecha1, fecha2);
	}
	
	public List<IngresosAlmacen> filtroIngresosxAlmacen(String producto, String fecha1, String fecha2){
		return (List<IngresosAlmacen>) ingresorepo.filtroIngresosxAlmacen(producto, fecha1, fecha2);
	}
	
	public Ingreso buscarPorNroOrden(String norden){
		return ingresorepo.buscarPorNroOrden(norden);
	}

}
