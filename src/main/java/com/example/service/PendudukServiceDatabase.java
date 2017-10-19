package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
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
	public void wafat(String nik) {
		// TODO Auto-generated method stub
		pendudukMapper.wafat(nik);
	}

	@Override
	public List<PendudukModel> cekPenduduk(String nomor_kk) {
		// TODO Auto-generated method stub
		return pendudukMapper.cekPenduduk(nomor_kk);
	}

	@Override
	public PendudukModel jumlahKeluarga(Long id_keluarga) {
		// TODO Auto-generated method stub
		return pendudukMapper.jumlahKeluarga(id_keluarga);
	}

	@Override
	public void is_tidak_berlaku(String nomor_kk) {
		// TODO Auto-generated method stub
		pendudukMapper.is_tidak_berlaku(nomor_kk);
		
	}
}
