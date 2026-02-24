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
import br.ueg.mobile.exitus.visao.modelo.CampusVisao;

@DatabaseTable
public class Campus extends Entidade implements KvmSerializable, Serializable {

	@DatabaseField(canBeNull = false, id = true)
	private Long id;

	@DatabaseField(canBeNull = false, width = 45)
	private String nome;

	private transient java.lang.Object __source;

	public Campus(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CampusVisao getCampusVisao(){
		return new CampusVisao(nome);
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
			return this.nome!=null?this.nome:SoapPrimitive.NullSkip;
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
			info.type = PropertyInfo.LONG_CLASS;
			info.name = "id";
			info.namespace= "";
		}
		if(propertyIndex==count+1)
		{
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "nome";
			info.namespace= "";
		}
		super.getPropertyInfo(propertyIndex,arg1,info);
	}

	@Override
	public void loadFromSoap(java.lang.Object paramObj, ExtendedSoapSerializationEnvelope __envelope)
	{
		if (paramObj == null)
			return;
		AttributeContainer inObj=(AttributeContainer)paramObj;
		super.loadFromSoap(paramObj, __envelope);

	}

	@Override
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
						this.id = new Long(j.toString());
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
		return super.loadProperty(info,soapObject,__envelope);
	}

	public java.lang.Object getOriginalXmlSource()
	{
		return __source;
	}
}
