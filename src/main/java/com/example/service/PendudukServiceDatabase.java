package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {

	@Autowired
	PendudukMapper pendudukMapper;
	
	@Override
	public PendudukModel detailpenduduk(String nik) {
		// TODO Auto-generated method stub
		log.info(nik);
		return pendudukMapper.viewPenduduk(nik);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		pendudukMapper.addPenduduk(penduduk);
		
	}

	@Override
	public PendudukModel kodeCamat(Long id_keluarga) {
		// TODO Auto-generated method stub
		return pendudukMapper.kodeCamat(id_keluarga);
	}

	@Override
	public PendudukModel lastNIK() {
		// TODO Auto-generated method stub
		return pendudukMapper.lastPenduduk();
	}

	@Override
	public PendudukModel getPenduduk(String nik) {
		// TODO Auto-generated method stub
		return pendudukMapper.getPenduduk(nik);
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		pendudukMapper.updatePenduduk(penduduk);
		
	}
	
	@Override
	public PendudukModel wafat(String nik) {
		// TODO Auto-generated method stub
		return pendudukMapper.wafat(nik);
	}
}
