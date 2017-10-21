package com.example.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;
import com.example.service.KeluargaService;

@Controller
public class KeluargaController {

	@Autowired
	KeluargaService keluargaService;

	@RequestMapping("/keluarga/home")
	public String homeKeluarga() {
		
		return "keluarga/home";
	}

	@RequestMapping("/keluarga/view")
	public String viewPenduduk(@Valid @RequestParam(value = "nomor_kk", required = false) String nomor_kk,
			Model model) {
		KeluargaModel viewKeluarga = keluargaService.viewKeluarga(nomor_kk);
		
		if (nomor_kk.isEmpty()) {
			return homeKeluarga();
		} else {
			if (viewKeluarga == null) {
				model.addAttribute("nomor_kk", nomor_kk);
				return "keluarga/error-keluarga";
			} else {
				model.addAttribute("keluarga", viewKeluarga);
				List<PendudukModel> lengkap = keluargaService.detailKeluarga(nomor_kk);
				model.addAttribute("penduduk", lengkap);
				return "keluarga/view-keluarga";
			}
		}
	}

	@RequestMapping("/keluarga/add")
	public String addKeluarga() {
		return "keluarga/addKeluarga";
	}

	@RequestMapping("/keluarga/tambah")
	public String tambahPenduduk(@Valid @ModelAttribute KeluargaModel keluarga, Model model, RedirectAttributes redirect) {
		
		try {
		KeluargaModel nomorID = keluargaService.nomorID(keluarga.getNamaKelurahan(), keluarga.getNamaKecamatan(),
				keluarga.getNamaKota());
		// 6 digit kode kecamatan dan mendapatkan idKelurahan
		String NomorNKK = "";
		if (keluarga.getNamaKelurahan().equals(nomorID.getNamaKelurahan())) {
			if (keluarga.getNamaKecamatan().equals(nomorID.getNamaKecamatan())) {
				if (keluarga.getNamaKota().equals(nomorID.getNamaKota())) {
					NomorNKK = nomorID.getKodeKecamatan().substring(0, 6);
					Long idKelurahan = nomorID.getIdKelurahan();
					keluarga.setId_kelurahan(idKelurahan);
				} else {
					System.out.println("tidak cocok");
				}

			} else {
				System.out.println("tidak cocok");
			}

		} else {
			System.out.println("tidak cocok");
		}
		
		// 6 digit tanggal
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("ddMMyy");
		String today = formatter.format(date);
		NomorNKK += today;

		KeluargaModel lastkeluarga = keluargaService.lastKeluarga();

		// 4 digit terakhir
		if (keluarga.getAlamat().equals(lastkeluarga.getAlamat())) {
			if (today.equals(lastkeluarga.getNomor_kk().substring(6, 12))) {
				String nilai = lastkeluarga.getNomor_kk().substring(12, 16);
				Integer value = Integer.parseInt(nilai);
				value += 1;
				String Nomor = new DecimalFormat("0000").format(value);
				NomorNKK += Nomor;
			} else {
				NomorNKK += "0001";
			}

		} else {
			NomorNKK += "0001";
		}

		keluarga.setNomor_kk(NomorNKK);
		model.addAttribute("keluarga", keluarga);
		keluargaService.addKeluarga(keluarga);

		return "keluarga/addSuccess";
		} catch (NullPointerException e) {
			return addKeluarga();
		}

	}
	
	
	@GetMapping("keluarga/ubah/{nkk}")
	public String getKeluarga(@Valid @PathVariable String nkk, Model model) {
		KeluargaModel keluarga = keluargaService.getKeluarga(nkk);
		model.addAttribute("keluarga", keluarga);
		return "keluarga/form-update";
	}
	
	@PostMapping("keluarga/ubah/submit")
	public String updateKeluarga(@Valid @ModelAttribute KeluargaModel keluarga, Model model) {
		
		try {
		KeluargaModel nomorID = keluargaService.nomorID(keluarga.getNamaKelurahan(), keluarga.getNamaKecamatan(),
				keluarga.getNamaKota());
		// pengecekan id kelurahan
		String NomorNKK = "";
		if (keluarga.getNamaKelurahan().equals(nomorID.getNamaKelurahan())) {
			if (keluarga.getNamaKecamatan().equals(nomorID.getNamaKecamatan())) {
				if (keluarga.getNamaKota().equals(nomorID.getNamaKota())) {
					NomorNKK = nomorID.getKodeKecamatan().substring(0, 6);
					Long idKelurahan = nomorID.getIdKelurahan();
					keluarga.setId_kelurahan(idKelurahan);
				} else {
					System.out.println("tidak cocok");
				}

			} else {
				System.out.println("tidak cocok");
			}

		} else {
			System.out.println("tidak cocok");
		}
		
		// 6 digit tanggal input
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("ddMMyy");
		String today = formatter.format(date);
		NomorNKK += today;

		KeluargaModel lastkeluarga = keluargaService.lastKeluarga();
		// 4 digit terakhir
		if (keluarga.getAlamat().equals(lastkeluarga.getAlamat())) {
			if (today.equals(lastkeluarga.getNomor_kk().substring(6, 12))) {
				String nilai = lastkeluarga.getNomor_kk().substring(12, 16);
				Integer value = Integer.parseInt(nilai);
				value += 1;
				String Nomor = new DecimalFormat("0000").format(value);
				NomorNKK += Nomor;
			} else {
				NomorNKK += "0001";
			}

		} else {
			NomorNKK += "0001";
		}
		
		// transfer nkk lama
		keluarga.setNomor_kklama(keluarga.getNomor_kk());
		model.addAttribute("nomor_kk", keluarga.getNomor_kklama());
		
		// pembuatan nkk baru
		keluarga.setNomor_kk(NomorNKK);
	
		keluargaService.updateKeluarga(keluarga);
		return "keluarga/successUpdate";
		} catch (NullPointerException e) {
			return "penduduk/form-update";
		}
	}
}
