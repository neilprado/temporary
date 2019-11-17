package br.com.bank.rabbitmq.pagamento.model;

import lombok.Data;

@Data
public class Banco {
    private String agencia;
    private String conta;
    private String cartao;
    private float valor;
    private String senha;
    private int bandeira;

    public Banco(String agencia, String conta, String cartao, float valor, String senha, int bandeira) {
        this.agencia = agencia;
        this.conta = conta;
        this.cartao = cartao;
        this.valor = valor;
        this.senha = senha;
        this.bandeira = bandeira;
    }
}
