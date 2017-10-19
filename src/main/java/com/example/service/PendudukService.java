package com.example.service;

import java.util.List;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel detailpenduduk(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	PendudukModel kodeCamat(Long id_keluarga);
	
	PendudukModel lastNIK();
	
	PendudukModel getPenduduk(String nik);
	
	void updatePenduduk(PendudukModel penduduk);
	
	void wafat(String nik);
	
	void is_tidak_berlaku(String nomor_kk);
	
	PendudukModel jumlahKeluarga(Long id_keluarga);
	
	List<PendudukModel> cekPenduduk(String nomor_kk);
}
