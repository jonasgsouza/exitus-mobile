package br.ueg.mobile.exitus.controle;

import android.content.Context;

import com.j256.ormlite.table.TableUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Map;

import br.ueg.exitus.sincronizador.tarefa.Sincronizavel;
import br.ueg.mobile.exitus.dao.DAO;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.utils.MyApplication;

/**
 * Created by jonas on 28/08/2018.
 */

public abstract class Controle<M extends Sincronizavel, ID, D extends DAO<M,ID>> {
    protected Context context;
    protected D dao;

    @SuppressWarnings("unchecked")
    public Controle(){
        Class<M> classeModelo = ((Class<M>) ((ParameterizedType)
                (getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
        try {
            Class<D> classeDAO = ((Class<D>) ((ParameterizedType)
                    (getClass().getGenericSuperclass())).getActualTypeArguments()[2]);
            Constructor<D> constructor = classeDAO.getConstructor(Context.class, Class.class);
            dao = constructor.newInstance(MyApplication.getInstancia(), classeModelo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível instanciar o DAO");
        }
    }

    public Retorno persistir (M modelo){
        Retorno ret = persistirDependencias(modelo);
        if(!ret.isOk()) return ret;
        return dao.persistir(modelo);
    }

    protected abstract Retorno persistirDependencias(M modelo);

    public Retorno atualizar(M modelo){
        Retorno ret = persistirDependencias(modelo);
        if(!ret.isOk()) return ret;
        return dao.atualizar(modelo);
    }

    public Retorno salvarOuAtualizar(M modelo){
        Retorno ret = persistirDependencias(modelo);
        if(!ret.isOk()) return ret;
        return dao.salvarOuAtualizar(modelo);
    }

    public Retorno filtrarPorCampo(Map<String, Object> map){
        return dao.filtrarPorCampo(map);
    }

    public long numeroDeLinhas(){
        return dao.numeroDeLinhas();
    }

    public Retorno buscar(ID id){
        return dao.buscar(id);
    }

    public Retorno listar(){
        return dao.listar();
    }

    public void limparTabela(){
        dao.limparTabela();
    }

    public void dropTable(){
       dao.dropTable();
    }
}
