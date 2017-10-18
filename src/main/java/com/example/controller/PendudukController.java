package com.example.controller;

import java.text.DecimalFormat;

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

		PendudukModel camat = pendudukService.kodeCamat(penduduk.getId_keluarga());
		
		// 6 digit kode kecamatan
		String kodeNIK = camat.getKodeKecamatan().substring(0, 6);
		System.out.println(kodeNIK);

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
		System.out.println(kodeTerakhir.getTempat_lahir());
		if (penduduk.getTempat_lahir().equals(kodeTerakhir.getTempat_lahir())) {
			if (penduduk.getTanggal_lahir().equals(kodeTerakhir.getTanggal_lahir())) {
				System.out.println(penduduk.getTanggal_lahir());
				System.out.println(kodeTerakhir.getTanggal_lahir());
				if (penduduk.getJenis_kelamin().equals(kodeTerakhir.getJenis_kelamin())) {
					System.out.println(penduduk.getJenis_kelamin());
					System.out.println(kodeTerakhir.getJenis_kelamin());
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
		System.out.println(kodeNIK);
		penduduk.setNik(kodeNIK);
		pendudukService.addPenduduk(penduduk);
		model.addAttribute("penduduk", penduduk);
		return "penduduk/addSuccess";
	}
	
	@GetMapping("/penduduk/ubah/{nik}")
	public String getPenduduk(@Valid @PathVariable String nik, Model model) {
		PendudukModel penduduk = pendudukService.getPenduduk(nik);
		model.addAttribute("penduduk", penduduk);
		return "penduduk/form-update";
	}
	
	@PostMapping("penduduk/ubah/submit")
	public String updatePenduduk(@Valid @ModelAttribute PendudukModel penduduk, Model model) {
		
		PendudukModel camat = pendudukService.kodeCamat(penduduk.getId_keluarga());
		
		// 6 digit kode kecamatan
		String kodeNIK = camat.getKodeKecamatan().substring(0, 6);
		System.out.println(kodeNIK);

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
		System.out.println(kodeTerakhir.getTempat_lahir());
		if (penduduk.getTempat_lahir().equals(kodeTerakhir.getTempat_lahir())) {
			if (penduduk.getTanggal_lahir().equals(kodeTerakhir.getTanggal_lahir())) {
				System.out.println(penduduk.getTanggal_lahir());
				System.out.println(kodeTerakhir.getTanggal_lahir());
				if (penduduk.getJenis_kelamin().equals(kodeTerakhir.getJenis_kelamin())) {
					System.out.println(penduduk.getJenis_kelamin());
					System.out.println(kodeTerakhir.getJenis_kelamin());
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
		System.out.println("nik baru " + kodeNIK);
		
		pendudukService.updatePenduduk(penduduk);
		return "penduduk/successUpdate";
	}
	
	@PostMapping("penduduk/mati")
	public String updateWafat(@Valid @RequestParam(value = "npm") String npm, Model model) {
		
		PendudukModel wafat = pendudukService.wafat(npm);
		model.addAttribute("wafat", wafat);
		
		
		return "penduduk/wafat";
		
		
	}
	

}
