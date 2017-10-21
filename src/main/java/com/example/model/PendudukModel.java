package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {

	private Long id;
	private Long id_keluarga;
	private String nik;
	private String niklama;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private Integer jenis_kelamin;
	private Integer is_wni;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private Integer is_wafat;
	private String NamaKelurahan;
	private String NamaKecamatan;
	private String NamaKota;
	private String Alamat;
	private String RT;
	private String RW;
	private String KodeKecamatan;
	private String NKK;
	private Integer TidakBerlaku;
	private Long idKota;
	private Long idKecamatan;
	private Long idKelurahan;
	private String umur;
}
