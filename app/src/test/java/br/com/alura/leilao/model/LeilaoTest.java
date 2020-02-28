package br.com.alura.leilao.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LeilaoTest {

    public static final double DELTA = 0.0001;
    private Leilao console = new Leilao("Console");
    private Usuario alex = new Usuario("Alex");
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
    public void deve_DevolveMaiorLance_QuandoRecebeUmLanceEmOrdemDecressente(){

        console.proposes(new Lance(alex, 200.0));
        console.proposes(new Lance(new Usuario("Frans"), 100.0));
        double maxLance = console.getMaxLance();

        assertEquals(200, maxLance, DELTA);
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
    public void deve_DevolveMenorLance_QuandoRecebeUmLanceEmOrdemDecressente(){

        console.proposes(new Lance(alex, 200.0));
        console.proposes(new Lance(new Usuario("Frans"), 100.0));
        double minTVLance = console.getMinLance();

        assertEquals(100, minTVLance, DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeTresLances(){
        console.proposes(new Lance(alex, 200.0));
        console.proposes(new Lance(alex, 300.0));
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
        console.proposes(new Lance(alex, 300.0));
        console.proposes(new Lance(alex, 200.0));

        List<Lance> tresMaioresLancesDevolvidos = console.threeHighestBids();
        assertEquals(300, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(200, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);

    }
    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeQuatroLances(){
        final Usuario fran = new Usuario("Fran");

        console.proposes(new Lance(fran, 340.0));
        console.proposes(new Lance(alex, 300.0));
        console.proposes(new Lance(fran, 50.0));
        console.proposes(new Lance(alex, 30.0));

        final List<Lance> tresmaioresLances = console.threeHighestBids();
        assertEquals(3, tresmaioresLances.size());

        assertEquals(340.0, tresmaioresLances.get(0).getValor(), DELTA);
        assertEquals(300.0, tresmaioresLances.get(1).getValor(), DELTA);
        assertEquals(50.0, tresmaioresLances.get(2).getValor(), DELTA);

    }
}