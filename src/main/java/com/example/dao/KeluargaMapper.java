package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {

	@Select("SELECT nomor_kk, alamat, rt, rw, kelurahan.nama_kelurahan as NamaKelurahan, kecamatan.nama_kecamatan as NamaKecamatan, "
			+ "kota.nama_kota as NamaKota FROM keluarga JOIN kelurahan on keluarga.id_kelurahan = kelurahan.id JOIN "
			+ "kecamatan on kelurahan.id_kecamatan = kecamatan.id JOIN kota on kecamatan.id_kota = kota.id "
			+ "WHERE nomor_kk = #{nomor_kk}")
	KeluargaModel viewKeluarga(@Param("nomor_kk") String nomor_kk);

	@Select("SELECT * FROM penduduk JOIN keluarga on penduduk.id_keluarga = keluarga.id WHERE keluarga.nomor_kk = #{nomor_kk}")
	List<PendudukModel> pendudukByKeluarga(@Param("nomor_kk") String nomor_kk);

	@Insert("INSERT INTO keluarga(nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{nomor_kk}, "
			+ "#{alamat}, #{rt}, #{rw}, #{id_kelurahan}, '0')")
	void addKeluarga(KeluargaModel keluarga);

	@Select("SELECT kelurahan.id as idKelurahan, kecamatan.kode_kecamatan as KodeKecamatan, kelurahan.nama_kelurahan as NamaKelurahan, kecamatan.nama_kecamatan as NamaKecamatan, "
			+ "kota.nama_kota as NamaKota from kelurahan join kecamatan on kelurahan.id_kecamatan = kecamatan.id JOIN "
			+ "kota on kecamatan.id_kota = kota.id where kelurahan.nama_kelurahan = #{NamaKelurahan} and kecamatan.nama_kecamatan "
			+ "= #{NamaKecamatan} and kota.nama_kota = #{NamaKota}")
	KeluargaModel nomorID(@Param("NamaKelurahan") String NamaKelurahan, @Param("NamaKecamatan") String NamaKecamatan,
			@Param("NamaKota") String NamaKota);

	@Select("select nomor_kk, alamat from keluarga ORDER BY id DESC LIMIT 1")
	KeluargaModel lastKeluarga();

	@Select("SELECT nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku, kelurahan.nama_kelurahan as NamaKelurahan, kecamatan.nama_kecamatan as NamaKecamatan, kota.nama_kota as NamaKota "
			+ "from keluarga join kelurahan on keluarga.id_kelurahan = kelurahan.id join kecamatan on kelurahan.id_kecamatan = kecamatan.id "
			+ "join kota on kecamatan.id_kota = kota.id where keluarga.nomor_kk = #{nomor_kk}")
	KeluargaModel getKeluarga(@Param("nomor_kk") String nomor_kk);

	@Update("Update keluarga SET nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan} WHERE nomor_kk = #{nomor_kklama}")
	void updateKeluarga(KeluargaModel keluarga);

}
