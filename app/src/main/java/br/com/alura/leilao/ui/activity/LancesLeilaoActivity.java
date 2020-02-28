package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;

public class LancesLeilaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);
        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra("leilao")){
            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");
            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            descricao.setText(leilao.getDescricao());
            TextView maxLance = findViewById(R.id.lances_leilao_maior_lance);
            maxLance.setText(String.valueOf(leilao.getMaxLance()));

            TextView minLance = findViewById(R.id.lances_leilao_menor_lance);
            minLance.setText(String.valueOf(leilao.getMinLance()));
            TextView maioresLances = findViewById(R.id.lances_leilao_maiores_lances);
            StringBuilder sb = new StringBuilder();

            for (Lance lance : leilao.threeHighestBids()){
                sb.append(lance.getValor() + "\n");
            }
            maioresLances.setText(sb.toString());
        }
    }
}
