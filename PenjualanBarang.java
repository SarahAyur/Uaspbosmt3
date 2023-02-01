    package uaspbo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Uaspbo {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/toko_ahtong";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertpenjual();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updatepenjual();
                    break;
                case 4:
                    deletepenjual();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM penjual";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|    DATA JUAL BELI BARANG  |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                int kode_transaksi = rs.getInt("kode_transaksi");
                String tanggal = rs.getString("tanggal");
                String kode_barang = rs.getString("kode_barang");
                String nama_barang = rs.getString("nama_barang");
                String harga = rs.getString("harga");
                String qty = rs.getString("qty");

                
                System.out.println(String.format("%d. %s -- (%s)", kode_transaksi, tanggal, kode_barang, nama_barang, harga, qty));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertpenjual() {
        try {
            // ambil input dari user
            System.out.print("kode_transaksi: ");
            String kode_transaksi = input.readLine().trim();
            System.out.print("tanggal: ");
            String tanggal = input.readLine().trim();
            System.out.print("kode_barang: ");
            String kode_barang = input.readLine().trim();
            System.out.print("nama_barang: ");
            String nama_barang = input.readLine().trim();
            System.out.print("harga: ");
            String harga = input.readLine().trim();
            System.out.print("qty: ");
            String qty = input.readLine().trim();
            
 
            // query simpan
            String sql = "INSERT INTO penjual (kode_transaksi, tanggal, kode_barang, nama_barang, harga, qty) VALUE('%s', '%s','%s', '%s','%s', '%s')";
            sql = String.format(sql, kode_transaksi, tanggal, kode_barang, nama_barang, harga, qty);

            // simpan buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updatepenjual() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int kode_transaksi = Integer.parseInt(input.readLine());
            System.out.print("tanggal: ");
            String tanggal = input.readLine().trim();
            System.out.print("kode_barang: ");
            String kode_barang = input.readLine().trim();
            System.out.print("nama_barang: ");
            String nama_barang = input.readLine().trim();
            System.out.print("harga: ");
            String harga = input.readLine().trim();
            System.out.print("qty: ");
            String qty = input.readLine().trim();

            // query update
            String sql = "UPDATE penjual SET tanggal='%s', kode_barang='%s', nama_barang='%s', harga='%s', qty='%s' WHERE kode_transaksi='%d'";
            sql = String.format(sql, tanggal, kode_barang, nama_barang,harga,qty, kode_transaksi);

            // update data buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deletepenjual() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int kode_transaksi = Integer.parseInt(input.readLine());
            
            // buat query hapus
            String sql = String.format("DELETE FROM penjual WHERE kode_transaksi=%d", kode_transaksi);

            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
