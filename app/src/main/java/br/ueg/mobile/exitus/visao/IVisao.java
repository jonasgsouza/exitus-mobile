package br.ueg.mobile.exitus.visao;

import br.ueg.mobile.exitus.servico_cliente.Retorno;

/**
 * Created by jonas on 23/08/2018.
 */

public interface IVisao {

    void processarRetorno(String acao, Retorno retorno);
}
