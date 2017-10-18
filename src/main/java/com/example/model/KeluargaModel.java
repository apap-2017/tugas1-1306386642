package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	
	private Long id;
	private Long idKelurahan;
	private String nomor_kk;
	private String nomor_kklama;
	private String alamat;
	private String rt;
	private String rw;
	private Long id_kelurahan;
	private Integer is_tidak_berlaku;
	private List<PendudukModel> penduduk;
	private String NamaKelurahan;
	private String NamaKecamatan;
	private String NamaKota;	
	private String KodeKecamatan;

}
