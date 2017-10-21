package com.example.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

import com.example.model.PendudukModel;
import com.example.service.PendudukService;

@Controller
public class PendudukController {

	@Autowired
	PendudukService pendudukService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/penduduk/home")
	public String homePenduduk() {
		return "penduduk/home";
	}

	@RequestMapping("/penduduk/cari")
	public String cariPenduduk(@Valid @RequestParam(value = "idKota", required = false) Long idKota,
			@RequestParam(value = "idKecamatan", required = false) Long idKecamatan,
			@RequestParam(value = "idKelurahan", required = false) Long idKelurahan, Model model) {
		List<PendudukModel> listKota = pendudukService.listKota();
		model.addAttribute("kota", listKota);
		model.addAttribute("idKota", idKota);

		List<PendudukModel> listKecamatan = pendudukService.listKecamatan(idKota);
		model.addAttribute("kecamatan", listKecamatan);
		model.addAttribute("idKecamatan", idKecamatan);

		List<PendudukModel> listKelurahan = pendudukService.listKelurahan(idKecamatan);
		model.addAttribute("kelurahan", listKelurahan);
		model.addAttribute("idKelurahan", idKelurahan);

		if (idKota != null && idKecamatan != null && idKelurahan != null) {
			List<PendudukModel> viewPenduduk = pendudukService.listPenduduk(idKelurahan);
			model.addAttribute("penduduk", viewPenduduk);
			
			PendudukModel tempatPenduduk = pendudukService.tempatPenduduk(idKelurahan);
			model.addAttribute("tempat", tempatPenduduk);

			PendudukModel muda = pendudukService.muda(idKelurahan);
			model.addAttribute("muda", muda);

			PendudukModel tua = pendudukService.tua(idKelurahan);
			model.addAttribute("tua", tua);
			return "penduduk/view";
		}
		return "penduduk/cariKota";
	}

	@RequestMapping("/penduduk/view")
	public String viewPenduduk(@Valid @RequestParam(value = "nik", required = false) String nik, Model model) {
		PendudukModel viewPenduduk = pendudukService.detailpenduduk(nik);
		if (nik.isEmpty()) {
			return homePenduduk();
		} else {
			if (viewPenduduk == null) {
				model.addAttribute("nik", nik);
				return "penduduk/error-penduduk";
			} else {
				model.addAttribute("penduduk", viewPenduduk);
				return "penduduk/view-penduduk";
			}
		}
	}

	@RequestMapping("/penduduk/add")
	public String addPenduduk() {
		return "penduduk/addPenduduk";
	}

	@RequestMapping("/penduduk/tambah")
	public String tambahPenduduk(@Valid @ModelAttribute PendudukModel penduduk, Model model) {
		try {
			PendudukModel camat = pendudukService.kodeCamat(penduduk.getId_keluarga());

			// 6 digit kode kecamatan
			String kodeNIK = camat.getKodeKecamatan().substring(0, 6);

			// 6 digit tanggal lahir
			String dateString = penduduk.getTanggal_lahir();
			String[] seluruhtanggal = dateString.split("-");
			String tahun = seluruhtanggal[0].substring(2, 4);
			String bulan = seluruhtanggal[1];
			String tanggal = seluruhtanggal[2];
			String tanggalLahir = tanggal + bulan + tahun;

			// penentuan jenis kelamin
			if (penduduk.getJenis_kelamin().equals(1)) {
				Integer cewek = Integer.parseInt(tanggal) + 40;
				String tanggalcewe = cewek + bulan + tahun;
				kodeNIK += tanggalcewe;
			} else {
				kodeNIK += tanggalLahir;
			}

			// 4 digit kode akhir NIK
			PendudukModel kodeTerakhir = pendudukService.lastNIK();
			if (penduduk.getTempat_lahir().equals(kodeTerakhir.getTempat_lahir())) {
				if (penduduk.getTanggal_lahir().equals(kodeTerakhir.getTanggal_lahir())) {
					if (penduduk.getJenis_kelamin().equals(kodeTerakhir.getJenis_kelamin())) {
						String nilai = kodeTerakhir.getNik().substring(12, 16);
						Integer value = Integer.parseInt(nilai);
						value += 1;
						String akhir = new DecimalFormat("0000").format(value);
						kodeNIK += akhir;

					} else {
						kodeNIK += "0001";
					}

				} else {
					kodeNIK += "0001";
				}

			} else {
				kodeNIK += "0001";
			}

			penduduk.setNik(kodeNIK);
			pendudukService.addPenduduk(penduduk);
			model.addAttribute("penduduk", penduduk);
			return "penduduk/addSuccess";
		} catch (NullPointerException e) {
			return addPenduduk();
		}
	}

	@GetMapping("/penduduk/ubah/{nik}")
	public String getPenduduk(@Valid @PathVariable String nik, Model model) {
		try {
			PendudukModel penduduk = pendudukService.getPenduduk(nik);
			model.addAttribute("penduduk", penduduk);
			return "penduduk/form-update";
		} catch (NullPointerException e) {
			return homePenduduk();
		}
	}

	@PostMapping("penduduk/ubah/submit")
	public String updatePenduduk(@Valid @ModelAttribute PendudukModel penduduk, Model model) {
		PendudukModel camat = pendudukService.kodeCamat(penduduk.getId_keluarga());

		// 6 digit kode kecamatan
		String kodeNIK = camat.getKodeKecamatan().substring(0, 6);

		// 6 digit tanggal lahir
		String dateString = penduduk.getTanggal_lahir();
		String[] seluruhtanggal = dateString.split("-");
		String tahun = seluruhtanggal[0].substring(2, 4);
		String bulan = seluruhtanggal[1];
		String tanggal = seluruhtanggal[2];
		String tanggalLahir = tanggal + bulan + tahun;

		// penentuan jenis kelamin
		if (penduduk.getJenis_kelamin().equals(1)) {
			Integer cewek = Integer.parseInt(tanggal) + 40;
			String tanggalcewe = cewek + bulan + tahun;
			kodeNIK += tanggalcewe;
		} else {
			kodeNIK += tanggalLahir;
		}

		// 4 digit kode akhir NIK
		PendudukModel kodeTerakhir = pendudukService.lastNIK();
		if (penduduk.getTempat_lahir().equals(kodeTerakhir.getTempat_lahir())) {
			if (penduduk.getTanggal_lahir().equals(kodeTerakhir.getTanggal_lahir())) {
				if (penduduk.getJenis_kelamin().equals(kodeTerakhir.getJenis_kelamin())) {
					String nilai = kodeTerakhir.getNik().substring(12, 16);
					Integer value = Integer.parseInt(nilai);
					value += 1;
					String akhir = new DecimalFormat("0000").format(value);
					kodeNIK += akhir;

				} else {
					kodeNIK += "0001";
				}

			} else {
				kodeNIK += "0001";
			}

		} else {
			kodeNIK += "0001";
		}

		// nik lama
		model.addAttribute("nik", penduduk.getNik());

		// nik baru
		penduduk.setNiklama(penduduk.getNik());
		penduduk.setNik(kodeNIK);

		pendudukService.updatePenduduk(penduduk);
		return "penduduk/successUpdate";
	}

	@RequestMapping("penduduk/mati")
	public String is_wafat(@Valid @RequestParam(value = "nik", required = false) String nik, Model model) {
		pendudukService.wafat(nik);
		model.addAttribute("nik", nik);

		// mendapatkan attribut penduduk
		PendudukModel getPenduduk = pendudukService.getPenduduk(nik);

		// mendapatkan nomor_kk dan is_tidak_berlaku pada keluarga
		PendudukModel nkk = pendudukService.jumlahKeluarga(getPenduduk.getId_keluarga());

		// membuat daftar is_wafat
		List<PendudukModel> berlaku = pendudukService.cekPenduduk(nkk.getNKK());
		ArrayList<Integer> intList = new ArrayList<>();
		for (int i = 0; i < berlaku.size(); i++) {
			intList.add(berlaku.get(i).getIs_wafat());
		}
		if (intList.contains(0)) {
			nkk.setTidakBerlaku(0);
		} else {
			pendudukService.is_tidak_berlaku(nkk.getNKK());
		}
		return "redirect:/penduduk/view?nik=" + nik;
	}
	
	@RequestMapping("/penduduk/delete/{nik}")
	public String deletePenduduk(@Valid @PathVariable(value = "nik") String nik, Model model) {
		pendudukService.deletePenduduk(nik);
		return "penduduk/delete";
	}

}
