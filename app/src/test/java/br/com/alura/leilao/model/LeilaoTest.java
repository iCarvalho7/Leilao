package br.com.alura.leilao.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.exception.BidLessThanTheLastBidException;
import br.com.alura.leilao.exception.FollowedBidByTheSameUserException;
import br.com.alura.leilao.exception.UserHasAlreadyMadeFiveBidsException;

import static org.junit.Assert.*;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private Leilao console = new Leilao("Console");
    private Usuario alex = new Usuario("Alex");
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void deve_DevolverDescricao_QuandoRecebeaDescricao() {

        String descricaoDevolvida = console.getDescricao();
        assertEquals("Console", descricaoDevolvida);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeUmLance(){

        console.proposes(new Lance(alex, 200.0));
        double maxLanceConsole = console.getMaxLance();
        assertEquals(200.0, maxLanceConsole, DELTA);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeUmLanceEmOrdemCressente(){

        console.proposes(new Lance(alex, 300.00));
        console.proposes(new Lance(new Usuario("Frans"), 400.00));
        double maxLance = console.getMaxLance();

        assertEquals(400, maxLance, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeUmLance(){

        console.proposes(new Lance(alex, 200.0));
        double minLance = console.getMinLance();
        assertEquals(200.0, minLance, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisdeUmLanceEmOrdemCressente(){

        console.proposes(new Lance(alex, 300.00));
        console.proposes(new Lance(new Usuario("Frans"), 400.00));
        double minLance = console.getMinLance();

        assertEquals(300, minLance, DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeTresLances(){
        console.proposes(new Lance(alex, 200.0));
        console.proposes(new Lance(new Usuario("fran"), 300.0));
        console.proposes(new Lance(alex, 400.0));

        List<Lance> tresmaioresLances = console.threeHighestBids();
        assertEquals(3, tresmaioresLances.size());

        assertEquals(400, tresmaioresLances.get(0).getValor(), DELTA);
        assertEquals(300, tresmaioresLances.get(1).getValor(), DELTA);
        assertEquals(200, tresmaioresLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeNenhumLance(){
        List<Lance> tresMaioresLancesDevolvidos = console.threeHighestBids();
        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeUmLance(){
        console.proposes(new Lance(alex, 200.0));
        List<Lance> tresmaioresLances = console.threeHighestBids();

        assertEquals(1, tresmaioresLances.size());
        assertEquals(200.0, tresmaioresLances.get(0).getValor(), DELTA);

    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeDoisLance(){
        console.proposes(new Lance(alex, 200.0));
        console.proposes(new Lance(new Usuario("fran"), 300.0));

        List<Lance> tresMaioresLancesDevolvidos = console.threeHighestBids();
        assertEquals(300, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(200, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);

    }
    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeQuatroLances(){
        final Usuario fran = new Usuario("Fran");

        console.proposes(new Lance(alex, 30.0));
        console.proposes(new Lance(fran, 50.0));
        console.proposes(new Lance(alex, 300.0));
        console.proposes(new Lance(fran, 340.0));

        final List<Lance> tresmaioresLances = console.threeHighestBids();
        assertEquals(3, tresmaioresLances.size());

        assertEquals(340.0, tresmaioresLances.get(0).getValor(), DELTA);
        assertEquals(300.0, tresmaioresLances.get(1).getValor(), DELTA);
        assertEquals(50.0, tresmaioresLances.get(2).getValor(), DELTA);
    }

    @Test
    public void When_DontHaveLance_Expect_GiveBackZero(){
        double maxLance = console.getMaxLance();
        assertEquals(0.0, maxLance, DELTA);
    }

    @Test
    public void When_DontHavePurpose_Except_GiveBackZeroToSmallerPurpose(){
        double minLance = console.getMinLance();
        assertEquals(0.0, minLance, DELTA);
    }

    @Test(expected = BidLessThanTheLastBidException.class)
    public void When_PruposeIsSmallerThanBiggerPurpose_NoExcepts_AddPurpose(){
        console.proposes(new Lance(alex, 500));
        console.proposes(new Lance(new Usuario("Fran"), 400));
    }
    @Test(expected = FollowedBidByTheSameUserException.class)
    public void testShouldNOtAddBid_When_IstheSameUser(){
        console.proposes(new Lance(alex, 500));
        console.proposes(new Lance(new Usuario("Alex"), 600));
    }
    @Test(expected = UserHasAlreadyMadeFiveBidsException.class)
    public void testShouldNot_AddBid_When_IsMoreThanFiveBids(){
        final Usuario fran = new Usuario("Fran");

        console.proposes(new Lance(alex, 100));
        console.proposes(new Lance(fran, 200));
        console.proposes(new Lance(alex, 300));
        console.proposes(new Lance(fran, 400));
        console.proposes(new Lance(alex, 500));
        console.proposes(new Lance(fran, 600));
        console.proposes(new Lance(alex, 700));
        console.proposes(new Lance(fran, 800));
        console.proposes(new Lance(alex, 900));
        console.proposes(new Lance(fran, 1000));

            console.proposes(new Lance(alex, 1100));

    }
}