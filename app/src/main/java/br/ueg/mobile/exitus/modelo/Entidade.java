package br.ueg.mobile.exitus.modelo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import br.ueg.exitus.sincronizador.tarefa.Sincronizavel;
import br.ueg.mobile.exitus.servico_cliente.ExtendedSoapSerializationEnvelope;
import br.ueg.mobile.exitus.servico_cliente.Helper;

public abstract class Entidade extends AttributeContainer implements KvmSerializable, Sincronizavel {

    @DatabaseField(columnName = "data_atualizacao", width = 10, dataType = DataType.DATE_STRING,
            format = "yyyy-MM-dd")
    private Date dataAtualizacao;

    @Override
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Override
    public void setDataAtualizacao(Date date) {
        this.dataAtualizacao = date;
    }

    @Override
    public Object getChaveSecundaria() {
        return null;
    }

    private transient java.lang.Object __source;

    public void loadFromSoap(java.lang.Object paramObj,ExtendedSoapSerializationEnvelope __envelope)
    {
        if (paramObj == null)
            return;
        AttributeContainer inObj=(AttributeContainer)paramObj;
        __source=inObj;
        if(inObj instanceof SoapObject)
        {
            SoapObject soapObject=(SoapObject)inObj;
            int size = soapObject.getPropertyCount();
            for (int i0=0;i0< size;i0++)
            {
                PropertyInfo info=soapObject.getPropertyInfo(i0);
                if(!loadProperty(info,soapObject,__envelope))
                {
                }
            }
        }

    }

    protected boolean loadProperty(PropertyInfo info,SoapObject soapObject,ExtendedSoapSerializationEnvelope __envelope)
    {
        java.lang.Object obj = info.getValue();
        if (info.name.equals("dataAtualizacao"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        try {
                            this.dataAtualizacao = new SimpleDateFormat("yyyy-MM-dd").parse(j.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if (obj instanceof java.util.Date){
                    this.dataAtualizacao = (java.util.Date)obj;
                }
            }
            return true;
        }
        return false;
    }

    public java.lang.Object getOriginalXmlSource()
    {
        return __source;
    }


    @Override
    public java.lang.Object getProperty(int propertyIndex) {
        //!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
        //!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
        if(propertyIndex==0)
        {
            return this.dataAtualizacao!=null?Helper.getDateTimeFormat().format(this.dataAtualizacao):SoapPrimitive.NullSkip;
        }
        return null;
    }


    @Override
    public int getPropertyCount() {
        return 1;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "dataAtualizacao";
            info.namespace= "";
        }
    }

    @Override
    public void setProperty(int arg0, java.lang.Object arg1)
    {
    }
}
