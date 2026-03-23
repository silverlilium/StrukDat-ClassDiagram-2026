import java.util.*;

class Pakaian {
    String warna;
    String nama;
    int countPemakaian;
    String kondisi;
    int hitunganLaundry;

    public Pakaian(String warna, String nama) {
        this.warna = warna;
        this.nama = nama;
        this.countPemakaian = 0;
        this.kondisi = "Bisa dipakai";
        this.hitunganLaundry = 0;
    }

    public boolean ready() {
        return kondisi.equals("Bisa dipakai");
    }

    public void pakai() {
        countPemakaian++;
        System.out.println(warna + " " + nama + " dipakai (" + countPemakaian + "x)");
        cekLaundry();
    }

    public void hariEsok() {
        if (kondisi.equals("Sedang laundry")) {
            hitunganLaundry--;
            if (hitunganLaundry <= 0) {
                kondisi = "Bisa dipakai";
                System.out.println(nama + " sudah selesai laundry!");
            }
        }
    }

    public void cekLaundry() {
    }

    public String kategori() {
        return "Clothing";
    }

    public void info() {
        String extra = kondisi.equals("Sedang laundry") ? " (" + hitunganLaundry + " hari)" : "";
        System.out.println(warna + " " + nama + " (" + kategori() + ") | Status: " + kondisi + extra);
    }
}

class Baju extends Pakaian {
    public Baju(String warna, String nama) {
        super(warna, nama);
    }

    @Override
    public void cekLaundry() {
        if (countPemakaian >= 3) {
            kondisi = "Sedang laundry";
            hitunganLaundry = 3;
            countPemakaian = 0;
            System.out.println(nama + " masuk laundry (3 hari)");
        }
    }

    @Override
    public String kategori() {
        return "Baju";
    }
}

class Celana extends Pakaian {
    public Celana(String warna, String nama) {
        super(warna, nama);
    }

    @Override
    public void cekLaundry() {
        if (countPemakaian >= 6) {
            kondisi = "Sedang laundry";
            hitunganLaundry = 3;
            countPemakaian = 0;
            System.out.println(nama + " masuk laundry (3 hari)");
        }
    }

    @Override
    public String kategori() {
        return "Celana";
    }
}

class AlasKaki extends Pakaian {
    public AlasKaki(String warna, String nama) {
        super(warna, nama);
    }

    @Override
    public String kategori() {
        return "Alas Kaki";
    }
}

class Lemari {
    ArrayList<Pakaian> daftarPakaian = new ArrayList<>();

    public void masukkan(Pakaian p) {
        daftarPakaian.add(p);
    }

    public List<Pakaian> getKategori(String kat) {
        List<Pakaian> hasil = new ArrayList<>();
        for (Pakaian p : daftarPakaian) {
            if (p.kategori().equalsIgnoreCase(kat)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public void lihatKategori(String kat) {
        List<Pakaian> data = getKategori(kat);
        System.out.println("\n== " + kat + " ==");
        if (data.isEmpty()) {
            System.out.println("Kosong");
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            System.out.print((i + 1) + ". ");
            data.get(i).info();
        }
    }

    public void tampilkanAll() {
        lihatKategori("Baju");
        lihatKategori("Celana");
        lihatKategori("Alas Kaki");
    }

    public void prosesHari() {
        for (Pakaian p : daftarPakaian) {
            p.hariEsok();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Lemari lemari = new Lemari();
        int opsi;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Tambah Pakaian");
            System.out.println("2. Pakai Outfit");
            System.out.println("3. Lihat Lemari");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");

            opsi = sc.nextInt();
            sc.nextLine();

            switch (opsi) {
                case 1:
                    System.out.println("\nTambah apa?");
                    System.out.println("1. Baju");
                    System.out.println("2. Celana");
                    System.out.println("3. Alas Kaki");
                    System.out.print("Pilih: ");
                    int tipe = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Warna: ");
                    String wrn = sc.nextLine();

                    System.out.print("Jenis (contoh: tee, denim, sandals): ");
                    String nm = sc.nextLine();

                    Pakaian baru = null;
                    if (tipe == 1)
                        baru = new Baju(wrn, nm);
                    else if (tipe == 2)
                        baru = new Celana(wrn, nm);
                    else if (tipe == 3)
                        baru = new AlasKaki(wrn, nm);

                    if (baru != null) {
                        lemari.masukkan(baru);
                        System.out.println("Berhasil ditambahkan!");
                    } else {
                        System.out.println("Input tidak valid.");
                    }
                    break;

                case 2:
                    List<Pakaian> listBaju = new ArrayList<>();
                    for (Pakaian p : lemari.getKategori("Baju")) {
                        if (p.ready())
                            listBaju.add(p);
                    }

                    List<Pakaian> listCelana = new ArrayList<>();
                    for (Pakaian p : lemari.getKategori("Celana")) {
                        if (p.ready())
                            listCelana.add(p);
                    }

                    List<Pakaian> listKaki = new ArrayList<>();
                    for (Pakaian p : lemari.getKategori("Alas Kaki")) {
                        if (p.ready())
                            listKaki.add(p);
                    }

                    if (listBaju.isEmpty() || listCelana.isEmpty() || listKaki.isEmpty()) {
                        System.out.println("Tidak ada outfit tersedia, beberapa item sedang di laundry.");
                        break;
                    }

                    System.out.println("\nPilih Baju:");
                    for (int i = 0; i < listBaju.size(); i++) {
                        System.out.print((i + 1) + ". ");
                        listBaju.get(i).info();
                    }
                    int pBaju = sc.nextInt() - 1;

                    System.out.println("\nPilih Celana:");
                    for (int i = 0; i < listCelana.size(); i++) {
                        System.out.print((i + 1) + ". ");
                        listCelana.get(i).info();
                    }
                    int pCelana = sc.nextInt() - 1;

                    System.out.println("\nPilih Alas Kaki:");
                    for (int i = 0; i < listKaki.size(); i++) {
                        System.out.print((i + 1) + ". ");
                        listKaki.get(i).info();
                    }
                    int pKaki = sc.nextInt() - 1;

                    Pakaian pilBaju = listBaju.get(pBaju);
                    Pakaian pilCelana = listCelana.get(pCelana);
                    Pakaian pilKaki = listKaki.get(pKaki);

                    System.out.println("\nOutfit dipakai hari ini:");
                    pilBaju.pakai();
                    pilCelana.pakai();
                    pilKaki.pakai();

                    lemari.prosesHari();
                    System.out.println("Hari berlalu...\n");
                    break;

                case 3:
                    lemari.tampilkanAll();
                    break;
            }
        } while (opsi != 0);

        sc.close();
        System.out.println("Program selesai.");
    }
}