package id.ac.ui.cs.advprog.statustrackingorder.enums;

public enum OrderStatus {
    MENUNGGU_PERSETUJUAN_ADMIN("Menunggu Persetujuan Admin"),
    SELESAI("Selesai"),
    GAGAL("Gagal");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
