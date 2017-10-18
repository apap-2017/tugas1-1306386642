package com.example.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	
	
	@Select("SELECT nik, nama, tempat_lahir, tanggal_lahir, keluarga.alamat as Alamat, keluarga.rt as RT, keluarga.rw as RW, kelurahan.nama_kelurahan as NamaKelurahan, kecamatan.nama_kecamatan as NamaKecamatan, "
			+ "kota.nama_kota as NamaKota, golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat from penduduk join keluarga on penduduk.id_keluarga = keluarga.id join "
			+ "kelurahan on keluarga.id_kelurahan = kelurahan.id join kecamatan on kelurahan.id_kecamatan = kecamatan.id join kota on kecamatan.id_kota = kota.id WHERE "
			+ "penduduk.nik = #{nik}")
	PendudukModel viewPenduduk(@Param("nik") String nik);
	
	@Insert("INSERT INTO penduduk(nik, nama, tempat_lahir, tanggal_lahir, is_wni, id_keluarga, agama, jenis_kelamin,pekerjaan, status_perkawinan, "
			+ "status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{is_wni}, "
			+ "#{id_keluarga}, #{agama}, #{jenis_kelamin}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(PendudukModel penduduk);
	
	
	@Select("SELECT DISTINCT kecamatan.kode_kecamatan as KodeKecamatan FROM penduduk JOIN keluarga on penduduk.id_keluarga = keluarga.id JOIN "
			+ "kelurahan on keluarga.id_kelurahan = kelurahan.id JOIN kecamatan on kelurahan.id_kecamatan = kecamatan.id "
			+ "WHERE penduduk.id_keluarga = #{id_keluarga}")
	PendudukModel kodeCamat(@Param("id_keluarga") Long id_keluarga);
	
	@Select("SELECT * FROM penduduk ORDER BY id DESC LIMIT 1")
	PendudukModel lastPenduduk();
	
	@Select("SELECT * from penduduk WHERE penduduk.nik = #{nik}")
	PendudukModel getPenduduk(@Param("nik") String nik);
	
	@Update("Update penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, "
			+ "status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, "
			+ "is_wafat = #{is_wafat} WHERE nik = #{niklama}")
	void updatePenduduk(PendudukModel penduduk);
	
	@Update("UPDATE penduduk SET is_wafat = 1 where nik = #{nik}")
	PendudukModel wafat(@Param("nik") String nik);

}
