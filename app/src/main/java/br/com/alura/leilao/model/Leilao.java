package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.leilao.exception.BidLessThanTheLastBidException;
import br.com.alura.leilao.exception.FollowedBidByTheSameUserException;
import br.com.alura.leilao.exception.UserHasAlreadyMadeFiveBidsException;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maxLance = 0.0;
    private double minLance = 0.0;

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
        valid(lance);
        lances.add(lance);
        double lanceValue = lance.getValor();
        if (OrderOneBid(lanceValue)) return;
        Collections.sort(lances);
        calculatesMaxLance(lanceValue);
        calculatesMinLance(lanceValue);
    }

    private boolean OrderOneBid(double lanceValue) {
        if (lances.size() == 1){
            maxLance = lanceValue;
            minLance = lanceValue;
            return true;
        }
        return false;
    }

    private void valid(Lance lance) {
        if (isBidSmallerThanMaxBig(lance))
            throw new BidLessThanTheLastBidException();

        if (!lances.isEmpty()){
            Usuario newUser = lance.getUsuario();
            if (isUserEqualstoUserLastBid(newUser))
                throw new FollowedBidByTheSameUserException();

            if (ifUserGiveMoreThanFiveBids(newUser))
                throw new UserHasAlreadyMadeFiveBidsException();
        }
    }

    private boolean ifUserGiveMoreThanFiveBids(Usuario newUser) {
        int userBid = 0;
        for (Lance l: lances
             ) {
            Usuario existentUser = l.getUsuario();
            if (existentUser.equals(newUser)){
                userBid++;
                if (userBid == 5){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isUserEqualstoUserLastBid(Usuario newUser) {
        Usuario lastUser = lances.get(0).getUsuario();
        return newUser.equals(lastUser);
    }

    private boolean isBidSmallerThanMaxBig(Lance lance) {
        double lanceValue = lance.getValor();
        if (lanceValue < maxLance){
            return true;
        }
        return false;
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

    public int bidQuantity() {
        return lances.size();
    }
}
