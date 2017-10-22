package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
	void wafat(@Param("nik") String nik);

	@Update("UPDATE keluarga SET is_tidak_berlaku = 1 where nomor_kk = #{nomor_kk}")
	void is_tidak_berlaku(@Param("nomor_kk") String nomor_kk);
	
	@Update("UPDATE keluarga SET is_tidak_berlaku = 0 where nomor_kk = #{nomor_kk}")
	void is_berlaku(@Param("nomor_kk") String nomor_kk);
	
	@Select("select DISTINCT keluarga.nomor_kk as NKK, keluarga.is_tidak_berlaku as TidakBerlaku from penduduk join keluarga on penduduk.id_keluarga = keluarga.id where penduduk.id_keluarga = #{id_keluarga}")
	PendudukModel jumlahKeluarga(@Param("id_keluarga") Long id_keluarga);

	@Select("SELECT is_wafat FROM penduduk join keluarga on penduduk.id_keluarga = keluarga.id WHERE keluarga.nomor_kk = #{nomor_kk}")
	List<PendudukModel> cekPenduduk(@Param("nomor_kk") String nomor_kk);

	@Select("select DISTINCT kota.id as idKota, kota.nama_kota as NamaKota FROM kota")
	List<PendudukModel> listKota();

	@Select("SELECT DISTINCT kecamatan.id as idKecamatan, kecamatan.nama_kecamatan as NamaKecamatan FROM kecamatan "
			+ "WHERE kecamatan.id_kota = #{idKota}")
	List<PendudukModel> listKecamatan(@Param("idKota") Long idKota);

	@Select("SELECT DISTINCT kelurahan.id as idKelurahan, kelurahan.nama_kelurahan as NamaKelurahan FROM "
			+ "kelurahan JOIN kecamatan on kelurahan.id_kecamatan = kecamatan.id JOIN "
			+ "kota ON kecamatan.id_kota = kota.id WHERE kelurahan.id_kecamatan = #{idKecamatan}")
	List<PendudukModel> listKelurahan(@Param("idKecamatan") Long idKecamatan);

	@Select("SELECT nik, nama, jenis_kelamin FROM penduduk JOIN keluarga on penduduk.id_keluarga = keluarga.id JOIN "
			+ "kelurahan on keluarga.id_kelurahan = kelurahan.id JOIN kecamatan on kelurahan.id_kecamatan = kecamatan.id JOIN "
			+ "kota ON kecamatan.id_kota = kota.id WHERE keluarga.id_kelurahan = #{idKelurahan}")
	List<PendudukModel> listPenduduk(@Param("idKelurahan") Long idKelurahan);
	
	@Select("SELECT kelurahan.nama_kelurahan as NamaKelurahan, kecamatan.nama_kecamatan as NamaKecamatan, "
			+ "kota.nama_kota as NamaKota FROM kelurahan JOIN kecamatan on kelurahan.id_kecamatan = kecamatan.id JOIN "
			+ "kota ON kecamatan.id_kota = kota.id WHERE kelurahan.id = #{idKelurahan}")
	PendudukModel tempatPenduduk(@Param("idKelurahan") Long idKelurahan);

	@Select("SELECT DISTINCT nama, nik, tanggal_lahir, (YEAR(CURDATE())-YEAR(tanggal_lahir)) AS umur FROM penduduk JOIN "
			+ "keluarga on penduduk.id_keluarga = keluarga.id WHERE keluarga.id_kelurahan = #{idKelurahan} ORDER BY umur ASC LIMIT 1")
	PendudukModel muda(@Param("idKelurahan") Long idKelurahan);

	@Select("SELECT DISTINCT nama, nik, tanggal_lahir, (YEAR(CURDATE())-YEAR(tanggal_lahir)) AS umur FROM penduduk JOIN "
			+ "keluarga on penduduk.id_keluarga = keluarga.id WHERE keluarga.id_kelurahan = #{idKelurahan} ORDER BY umur DESC LIMIT 1")
	PendudukModel tua(@Param("idKelurahan") Long idKelurahan);
	
	@Delete("DELETE from penduduk where nik = #{nik}")
	void deletePenduduk(@Param("nik") String nik);

}
