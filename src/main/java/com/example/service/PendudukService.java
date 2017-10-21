package com.example.service;

import java.util.List;

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
	
	List<PendudukModel> listKota();
	
	List<PendudukModel> listKecamatan(Long idKota);
	
	List<PendudukModel> listKelurahan(Long idKecamatan);
	
	List<PendudukModel> listPenduduk(Long idKelurahan);
	
	PendudukModel muda(Long idKelurahan);
	
	PendudukModel tua(Long idKelurahan);
	
}
