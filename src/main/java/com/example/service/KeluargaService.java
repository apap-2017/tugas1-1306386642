package com.example.service;

import java.util.List;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

public interface KeluargaService {
	
	KeluargaModel viewKeluarga(String nomor_kk);
	List<PendudukModel> detailKeluarga(String nomor_kk);
	void addKeluarga(KeluargaModel keluarga);
	KeluargaModel nomorID(String NamaKelurahan, String NamaKecamatan, String NamaKota);
	KeluargaModel lastKeluarga();
	
	KeluargaModel getKeluarga(String nomor_kk);
	void updateKeluarga(KeluargaModel keluarga);
}
