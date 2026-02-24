package br.ueg.mobile.exitus.modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.Hashtable;

import br.ueg.mobile.exitus.servico_cliente.ExtendedSoapSerializationEnvelope;

/**
 * Created by jonas on 09/06/2018.
 */

@DatabaseTable(tableName = "professor")
public class Professor extends Entidade implements KvmSerializable, Serializable{

    @DatabaseField(canBeNull = false, generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false, width = 100)
    private String nome;
    @DatabaseField(canBeNull = false, width = 100)
    private String usuario;
    @DatabaseField(canBeNull = false, width = 255)
    private String token;

    private transient java.lang.Object __source;

    public Professor(){}

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getProperty(int propertyIndex) {
        if(propertyIndex==0)
        {
            return this.id!=null?this.id:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==1)
        {
            return this.nome!=null?this.nome:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==2)
        {
            return this.usuario!=null?this.usuario:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==3)
        {
            return this.token!=null?this.token:SoapPrimitive.NullSkip;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int propertyIndex, Hashtable hashtable, PropertyInfo info) {
        if(propertyIndex==0)
        {
            info.type = PropertyInfo.LONG_CLASS;
            info.name = "id";
            info.namespace= "";
        }
        if(propertyIndex==1)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "nome";
            info.namespace= "";
        }
        if(propertyIndex==2)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "usuario";
            info.namespace= "";
        }
        if(propertyIndex==3)
        {
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "token";
            info.namespace= "";
        }
    }

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
        if (info.name.equals("id"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.id = Long.parseLong(j.toString());
                    }
                }
                else if (obj instanceof Long){
                    this.id = (Long)obj;
                }
            }
            return true;
        }
        if (info.name.equals("nome"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.nome = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.nome = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("usuario"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.usuario = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.usuario = (String)obj;
                }
            }
            return true;
        }
        if (info.name.equals("token"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.token = j.toString();
                    }
                }
                else if (obj instanceof String){
                    this.token = (String)obj;
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
}
