package br.ueg.mobile.exitus.modelo;

import android.content.Context;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import br.ueg.exitus.sincronizador.utils.Comparable;
import br.ueg.mobile.exitus.controle.FrequenciaAlunoControle;
import br.ueg.mobile.exitus.servico_cliente.ExtendedSoapSerializationEnvelope;
import br.ueg.mobile.exitus.servico_cliente.Helper;
import br.ueg.mobile.exitus.servico_cliente.Retorno;
import br.ueg.mobile.exitus.visao.modelo.FrequenciaAlunoVisao;
import br.ueg.mobile.exitus.visao.modelo.FrequenciaVisao;

public class Frequencia extends Entidade implements KvmSerializable, Serializable {

    @DatabaseField(canBeNull = false, generatedId = true, allowGeneratedIdInsert = true)
    private Long id;

    @Comparable
    @DatabaseField(canBeNull = false, columnName = "data_frequencia", dataType = DataType.DATE_STRING,
            format = "yyyy-MM-dd")
    private Date dataFrequencia;

    @DatabaseField(canBeNull = false)
    private Integer aula;

    @DatabaseField(canBeNull = false, width = 3)
    private String status;

    @DatabaseField(columnName = "oferta_disciplina_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private OfertaDisciplina ofertaDisciplina;

    @DatabaseField(persisted = false)
    private List<FrequenciaAluno> frequenciaAlunos;

    public FrequenciaVisao getFrequenciaVisao(Context context){
        FrequenciaVisao frequenciaVisao = new FrequenciaVisao();
        frequenciaVisao.setFrequencia(this);
        Map<String, Object> map = new HashMap<>();
        map.put("frequencia_id", getId());
        FrequenciaAlunoControle frequenciaAlunoControle = new FrequenciaAlunoControle();
        Retorno retorno = frequenciaAlunoControle.filtrarPorCampo(map);
        List<FrequenciaAluno> frequenciaAlunoList = retorno.getLista(new FrequenciaAluno());
        setFrequenciaAlunos(frequenciaAlunoList);
        List<FrequenciaAlunoVisao> frequenciaAlunoVisaoList = new ArrayList<>();
        for(FrequenciaAluno frequenciaAluno : frequenciaAlunoList){
            frequenciaAlunoVisaoList.add(frequenciaAluno.getFrequenciaAlunoVisao(context));
        }
        frequenciaVisao.setFrequenciaAlunos(frequenciaAlunoVisaoList);
        return frequenciaVisao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataFrequencia() {
        return dataFrequencia;
    }

    public void setDataFrequencia(Date dataFrequencia) {
        this.dataFrequencia = dataFrequencia;
    }

    public Integer getAula() {
        return aula;
    }

    public void setAula(Integer aula) {
        this.aula = aula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OfertaDisciplina getOfertaDisciplina() {
        return ofertaDisciplina;
    }

    public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
        this.ofertaDisciplina = ofertaDisciplina;
    }

    public List<FrequenciaAluno> getFrequenciaAlunos() {
        return frequenciaAlunos;
    }

    public void setFrequenciaAlunos(List<FrequenciaAluno> frequenciaAlunos) {
        this.frequenciaAlunos = frequenciaAlunos;
    }

    public void addFrequenciaAluno(FrequenciaAluno frequenciaAluno){
        if(frequenciaAlunos == null){
            frequenciaAlunos = new ArrayList<>();
        }
        frequenciaAlunos.add(frequenciaAluno);
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
        if (info.name.equals("aula"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.aula = Integer.parseInt(j.toString());
                    }
                }
                else if (obj instanceof Integer){
                    this.aula = (Integer)obj;
                }
            }
            return true;
        }
        if (info.name.equals("dataFrequencia"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        try {
                            this.dataFrequencia = new SimpleDateFormat("yyyy-MM-dd").parse(j.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if (obj instanceof java.util.Date){
                    this.dataFrequencia = (java.util.Date)obj;
                }
            }
            return true;
        }
        if (info.name.equals("frequenciaAlunos"))
        {
            if(obj!=null)
            {
                if(this.frequenciaAlunos==null)
                {
                    this.frequenciaAlunos = new java.util.ArrayList<>();
                }
                java.lang.Object j =obj;
                FrequenciaAluno j1= (FrequenciaAluno)__envelope.get(j,FrequenciaAluno.class,false);
                this.frequenciaAlunos.add(j1);
            }
            return true;
        }
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
        if (info.name.equals("ofertaDisciplina"))
        {
            if(obj!=null)
            {
                java.lang.Object j = obj;
                this.ofertaDisciplina = (OfertaDisciplina)__envelope.get(j,OfertaDisciplina.class,false);
            }
            return true;
        }
        if (info.name.equals("status"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.status = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.status = (String)obj;
                }
            }
            return true;
        }
        return super.loadProperty(info,soapObject,__envelope);
    }

    @Override
    public java.lang.Object getProperty(int propertyIndex) {
        int count = super.getPropertyCount();
        //!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
        //!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
        if(propertyIndex==count+0)
        {
            return this.aula!=null?this.aula:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+1)
        {
            return this.dataFrequencia!=null?Helper.getDateTimeFormat().format(this.dataFrequencia):SoapPrimitive.NullSkip;
        }
        if(propertyIndex>=count+2 && propertyIndex < count+2+this.frequenciaAlunos.size())
        {
            java.lang.Object frequenciaAlunos = this.frequenciaAlunos.get(propertyIndex-(count+2));
            return frequenciaAlunos!=null?frequenciaAlunos:SoapPrimitive.NullNilElement;
        }
        if(propertyIndex==count+2+this.frequenciaAlunos.size())
        {
            return this.id!=null?this.id:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+3+this.frequenciaAlunos.size())
        {
            return this.ofertaDisciplina!=null?this.ofertaDisciplina:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+4+this.frequenciaAlunos.size())
        {
            return this.status!=null?this.status:SoapPrimitive.NullSkip;
        }
        return super.getProperty(propertyIndex);
    }


    @Override
    public int getPropertyCount() {
        return super.getPropertyCount()+5+frequenciaAlunos.size();
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        int count = super.getPropertyCount();
        if(propertyIndex==count+0)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "aula";
            info.namespace= "";
        }
        if(propertyIndex==count+1)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "dataFrequencia";
            info.namespace= "";
        }
        if(propertyIndex>=count+2 && propertyIndex < count+2+this.frequenciaAlunos.size())
        {
            info.type = FrequenciaAluno.class;
            info.name = "frequenciaAlunos";
            info.namespace= "";
        }
        if(propertyIndex==count+2+this.frequenciaAlunos.size())
        {
            info.type = PropertyInfo.LONG_CLASS;
            info.name = "id";
            info.namespace= "";
        }
        if(propertyIndex==count+3+this.frequenciaAlunos.size())
        {
            info.type = OfertaDisciplina.class;
            info.name = "ofertaDisciplina";
            info.namespace= "";
        }
        if(propertyIndex==count+4+this.frequenciaAlunos.size())
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "status";
            info.namespace= "";
        }
        super.getPropertyInfo(propertyIndex,arg1,info);
    }

    @Override
    public void setProperty(int arg0, java.lang.Object arg1)
    {
    }
}
