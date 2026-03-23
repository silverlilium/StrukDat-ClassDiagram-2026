**Deskripsi Kasus**

Dalam kehidupan sehari-hari kita pasti memiliki banyak jenis pakaian seperti aju, celana, dan sepatu. Namun kadang kita lupa berapa kali pakaian sudah terpakai, kapan pakaian harus masuk laundry, dan apakah baju ada di lemari atau masih di laundry. 

Program ini dibuat untuk mensimulasikan sistem lemari yang mampu:
1. Menyimpan berbagai jenis pakaian
2. Mengelompokkan pakaian berdasarkan kategori
3. Menggunakan pakaian dalam bentuk satu set outfit
4. Mengatur sistem laundry otomatis berdasarkan frekuensi pemakaian
5. Mensimulasikannya dengan berjalannya waktu

Program ini diimplementasikan menggunakan konsep Object-Oriented Programming (OOP) dalam bahasa Java.

**Class Diagram**

<img width="1574" height="3700" alt="class diagram simulasi lemari" src="https://github.com/user-attachments/assets/c4683da0-5460-4d20-961a-49cb1431ea1d" />

**Kode Program Java**

import java.util.*;

class Pakaian {
    String warna;
    String nama;
    int countPemakaian;
    String status;
    int hitunganLaundry;

    public Pakaian(String warna, String nama) {
        this.warna = warna;
        this.nama = nama;
        this.countPemakaian = 0;
        this.status = "Bisa dipakai";
        this.hitunganLaundry = 0;
    }

    public boolean ready() {
        return status.equals("Bisa dipakai");
    }

    public void pakai() {
        countPemakaian++;
        System.out.println(warna + " " + nama + " dipakai (" + countPemakaian + "x)");
        cekLaundry();
    }

    public void hariEsok() {
        if (status.equals("Sedang laundry")) {
            hitunganLaundry--;
            if (hitunganLaundry <= 0) {
                status = "Bisa dipakai";
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
        String extra = status.equals("Sedang laundry") ? " (" + hitunganLaundry + " hari)" : "";
        System.out.println(warna + " " + nama + " (" + kategori() + ") | Status: " + status + extra);
    }
}

class Baju extends Pakaian {
    public Baju(String warna, String nama) {
        super(warna, nama);
    }

    @Override
    public void cekLaundry() {
        if (countPemakaian >= 3) {
            status = "Sedang laundry";
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
            status = "Sedang laundry";
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

**Screenshot Output**

Contoh penggunaan Menu Pilihan 1 untuk menambah pakaian ke dalam lemari

<img width="733" height="814" alt="Screenshot 2026-03-24 020153" src="https://github.com/user-attachments/assets/aeb294f3-8364-453b-b566-d782f38da83b" />


Contoh penggunaan Menu Pilihan 2 untuk memakai outfit

<img width="567" height="434" alt="Screenshot 2026-03-24 020225" src="https://github.com/user-attachments/assets/73847022-c131-429d-a965-4bddef96b819" />


Contoh penggunaan Menu Pilihan 3 untuk melihat isi lemari   

<img width="566" height="280" alt="Screenshot 2026-03-24 020240" src="https://github.com/user-attachments/assets/0af99beb-4885-43a2-8475-994f90d594e1" />


Contoh ketika pakaian tidak mencukupi (tidak ada set outfit)

<img width="570" height="595" alt="Screenshot 2026-03-24 020307" src="https://github.com/user-attachments/assets/122a38eb-ba24-4b1d-8db2-8a7147a332c2" />


Keluar program

<img width="537" height="128" alt="Screenshot 2026-03-24 020419" src="https://github.com/user-attachments/assets/06d3589d-8226-4865-b446-91827ef7dd5d" />



**Prinsip OOP yang Diterapkan**

1. Inheritance: Class Baju, Celana, AlasKaki adalah turunan dari class Pakaian
2. Poltmorphysm: Method cekLaundry() di-override pada masing-masing class:
- Baju → laundry setelah 3 kali pemakaian
- Celana → laundry setelah 6 kali pemakaian
- AlasKaki → tidak memiliki aturan laundry
3. Encapsulation: Data seperti countPemakaian, status, hitunganLaundry disimpan dalam class melalui method
4. Abstraction: User tidak perlu mengetahui detail proses laundry dan user cukup menggunakan menu yang tersedia untuk:
- Menambah pakaian
- Menggunakan set outfit
- Melihat isi lemari
- Keluar program

**Keunikan Program**

1. Sistem Outfit Berbasis Set
Program mengharuskan pengguna memilih satu set pakaian lengkap (baju, celana, dan alas kaki) dalam setiap penggunaan. Karena secara logika tidak mungkin jika kita tidak menggunakan salah satu dari pakaian tersebut.

2. Filtering Pakaian Saat Laundry
Pakaian yang sedang dalam kondisi laundry tidak akan ditampilkan dalam pilihan outfit. Sistem hanya menampilkan pakaian yang siap dipakai, sehingga mencegah kesalahan sejak awal dan meningkatkan validasi program.

3. Simulasi Waktu Otomatis
Setiap kali outfit digunakan, sistem secara otomatis menganggap satu hari telah berlalu. Sistem ini membuat program lebih fleksibel karena tidak memerlukan input tambahan lagi dari user.

4. Penanganan Memory Leak (Scanner)
Program menutup penggunaan Scanner dengan sc.close(); untuk mencegah pemborosan resource. Fitur ini menunjukkan penggunaan praktik pemrograman yang baik dalam mengelola memori.

**Kesimpulan**

Program ini merupakan implementasi sederhana dari konsep Object-Oriented Programming (OOP) dalam kasus nyata, yaitu manajemen pakaian dan laundry. Dengan menggunakan program ini, kita dapat menangani berbagai jenis pakaian dengan aturan berbeda secara efisien dan terstruktur.
