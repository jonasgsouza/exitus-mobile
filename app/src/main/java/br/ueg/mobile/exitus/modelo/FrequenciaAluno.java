package br.ueg.mobile.exitus.modelo;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.Hashtable;

import br.ueg.exitus.sincronizador.utils.Comparable;
import br.ueg.mobile.exitus.servico_cliente.ExtendedSoapSerializationEnvelope;
import br.ueg.mobile.exitus.visao.modelo.FrequenciaAlunoVisao;

public class FrequenciaAluno extends Entidade implements KvmSerializable, Serializable {

    @DatabaseField(canBeNull = false, generatedId = true, allowGeneratedIdInsert = true)
    private Long id;

    @Comparable
    @DatabaseField(canBeNull = false)
    private Boolean situacao;

    @DatabaseField(columnName = "oferta_disciplina_aluno_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private OfertaDisciplinaAluno ofertaDisciplinaAluno;

    @DatabaseField(columnName = "frequencia_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Frequencia frequencia;

    public FrequenciaAlunoVisao getFrequenciaAlunoVisao(Context context){
        return new FrequenciaAlunoVisao(context, this);
    }

    public FrequenciaAluno(){
        situacao = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public OfertaDisciplinaAluno getOfertaDisciplinaAluno() {
        return ofertaDisciplinaAluno;
    }

    public void setOfertaDisciplinaAluno(OfertaDisciplinaAluno ofertaDisciplinaAluno) {
        this.ofertaDisciplinaAluno = ofertaDisciplinaAluno;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public void loadFromSoap(java.lang.Object paramObj,ExtendedSoapSerializationEnvelope __envelope)
    {
        if (paramObj == null)
            return;
        AttributeContainer inObj=(AttributeContainer)paramObj;
        super.loadFromSoap(paramObj, __envelope);

    }

    @Override
    protected boolean loadProperty(PropertyInfo info, SoapObject soapObject, ExtendedSoapSerializationEnvelope __envelope)
    {
        java.lang.Object obj = info.getValue();
        if (info.name.equals("id"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.id = new Long(j.toString());
                    }
                }
                else if (obj instanceof Long){
                    this.id = (Long)obj;
                }
            }
            return true;
        }
        if (info.name.equals("situacao"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.situacao = Boolean.valueOf(j.toString());
                    }
                }
                else if (obj instanceof Boolean){
                    this.situacao = (Boolean)obj;
                }
            }
            return true;
        }

        if (info.name.equals("ofertaDisciplinaAluno"))
        {
            if(obj!=null)
            {
                java.lang.Object j = obj;
                this.ofertaDisciplinaAluno = (OfertaDisciplinaAluno)__envelope.get(j,OfertaDisciplinaAluno.class,false);
            }
            return true;
        }

//        if (info.name.equals("frequencia"))
//        {
//            if(obj!=null)
//            {
//                java.lang.Object j = obj;
//                this.frequencia = (Frequencia)__envelope.get(j,Frequencia.class,false);
//            }
//            return true;
//        }
        return super.loadProperty(info,soapObject,__envelope);
    }

    @Override
    public java.lang.Object getProperty(int propertyIndex) {
        int count = super.getPropertyCount();
        //!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
        //!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
        if(propertyIndex==count+0)
        {
            return this.id!=null?this.id:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+1)
        {
            return this.situacao!=null?this.situacao:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+2)
        {
            return this.ofertaDisciplinaAluno!=null?this.ofertaDisciplinaAluno:SoapPrimitive.NullSkip;
        }
        return super.getProperty(propertyIndex);
    }


    @Override
    public int getPropertyCount() {
        return super.getPropertyCount()+3;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        int count = super.getPropertyCount();
        if(propertyIndex==count+0)
        {
            info.type = PropertyInfo.LONG_CLASS;
            info.name = "id";
            info.namespace= "";
        }
        if(propertyIndex==count+1)
        {
            info.type = PropertyInfo.BOOLEAN_CLASS;
            info.name = "situacao";
            info.namespace= "";
        }
        if(propertyIndex==count+2)
        {
            info.type = OfertaDisciplinaAluno.class;
            info.name = "ofertaDisciplinaAluno";
            info.namespace= "";
        }
        super.getPropertyInfo(propertyIndex,arg1,info);
    }

    @Override
    public void setProperty(int arg0, java.lang.Object arg1)
    {
    }
}
