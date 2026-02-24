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

@DatabaseTable
public class Matriz extends Entidade implements KvmSerializable, Serializable {

	@DatabaseField(canBeNull = false, generatedId = true)
	private Long id;

	@DatabaseField(canBeNull = false, width = 10)
	private String descricao;

	private transient java.lang.Object __source;
	
	public Matriz(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public java.lang.Object getProperty(int propertyIndex) {
		int count = super.getPropertyCount();
		//!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
		//!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
		if(propertyIndex==count+0)
		{
			return this.descricao!=null?this.descricao:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==count+1)
		{
			return this.id!=null?this.id:SoapPrimitive.NullSkip;
		}
		return super.getProperty(propertyIndex);
	}

	@Override
	public int getPropertyCount() {
		return super.getPropertyCount()+2;
	}

	@Override
	public void setProperty(int i, Object o) {

	}

	@Override
	public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
	{
		int count = super.getPropertyCount();
		if(propertyIndex==count+0)
		{
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "descricao";
			info.namespace= "";
		}
		if(propertyIndex==count+1)
		{
			info.type = PropertyInfo.LONG_CLASS;
			info.name = "id";
			info.namespace= "";
		}
		super.getPropertyInfo(propertyIndex,arg1,info);
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
	protected boolean loadProperty(PropertyInfo info,SoapObject soapObject, ExtendedSoapSerializationEnvelope __envelope)
	{
		java.lang.Object obj = info.getValue();
		if (info.name.equals("descricao"))
		{
			if(obj!=null)
			{
				if (obj.getClass().equals(SoapPrimitive.class))
				{
					SoapPrimitive j =(SoapPrimitive) obj;
					if(j.toString()!=null)
					{
						this.descricao = j.toString();
					}
				}
				else if (obj instanceof String){
					this.descricao = (String)obj;
				}
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
		return super.loadProperty(info,soapObject,__envelope);
	}

	public java.lang.Object getOriginalXmlSource()
	{
		return __source;
	}
}
