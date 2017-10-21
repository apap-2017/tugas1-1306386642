package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Service
public class KeluargaServiceDatabase implements KeluargaService {

	@Autowired
	KeluargaMapper keluargaMapper;

	@Override
	public KeluargaModel viewKeluarga(String nomor_kk) {
		// TODO Auto-generated method stub
		return keluargaMapper.viewKeluarga(nomor_kk);
	}

	@Override
	public List<PendudukModel> detailKeluarga(String nomor_kk) {
		// TODO Auto-generated method stub
		return keluargaMapper.pendudukByKeluarga(nomor_kk);
	}

	@Override
	public KeluargaModel nomorID(String NamaKelurahan, String NamaKecamatan, String NamaKota) {
		// TODO Auto-generated method stub
		return keluargaMapper.nomorID(NamaKelurahan, NamaKecamatan, NamaKota);
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public KeluargaModel lastKeluarga() {
		// TODO Auto-generated method stub
		return keluargaMapper.lastKeluarga();
	}

	@Override
	public KeluargaModel getKeluarga(String nomor_kk) {
		// TODO Auto-generated method stub
		return keluargaMapper.getKeluarga(nomor_kk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.updateKeluarga(keluarga);

	}

}
