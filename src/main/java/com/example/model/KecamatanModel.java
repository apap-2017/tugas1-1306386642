package com.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	
	private Long id;
	private String nama_kecamatan;
	private String kode_kecamatan;
	private Long id_kota;

}
