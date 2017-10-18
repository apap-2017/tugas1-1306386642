package com.example.service;

import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel detailpenduduk(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	PendudukModel kodeCamat(Long id_keluarga);
	
	PendudukModel lastNIK();
	
	PendudukModel getPenduduk(String nik);
	
	void updatePenduduk(PendudukModel penduduk);
	
	PendudukModel wafat(String nik);
}
