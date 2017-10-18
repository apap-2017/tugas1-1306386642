package com.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	
	private Long id;
	private String nama_kelurahan;
	private Long id_kecamatan;
	private String kode_kelurahan;

}
