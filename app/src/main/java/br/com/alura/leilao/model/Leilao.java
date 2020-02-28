package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maxLance = Double.NEGATIVE_INFINITY;
    private double minLance = Double.POSITIVE_INFINITY;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public double getMaxLance() {
        return maxLance;
    }

    public double getMinLance() {
        return minLance;
    }

    public void proposes(Lance lance){
        lances.add(lance);
        Collections.sort(lances);
        double lanceValue = lance.getValor();
        calculatesMaxLance(lanceValue);
        calculatesMinLance(lanceValue);
    }

    private void calculatesMinLance(double lanceValue) {
        if (lanceValue < minLance){
            minLance = lanceValue;
        }
    }

    private void calculatesMaxLance(double lanceValue) {
        if(lanceValue > maxLance){
            maxLance = lanceValue;
        }
    }

    public List<Lance> getLances() {
        return lances;
    }

    public List<Lance> threeHighestBids() {
        if (lances.size() <= 3){
            return lances.subList(0,lances.size());
        }else {
            return lances.subList(0, 3);
        }
    }

}
