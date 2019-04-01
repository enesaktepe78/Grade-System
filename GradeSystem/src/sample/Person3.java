package sample;

import javafx.beans.property.SimpleIntegerProperty;

public class Person3 {
    private final SimpleIntegerProperty kota;

    public Person3( Integer kota) {
        super();
        this.kota = new SimpleIntegerProperty(kota);
    }

    public int getKota() {
        return kota.get();
    }

    public SimpleIntegerProperty kotaProperty() {
        return kota;
    }

    public void setKota(int kota) {
        this.kota.set(kota);
    }
}
