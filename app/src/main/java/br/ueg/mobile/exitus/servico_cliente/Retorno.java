package br.ueg.mobile.exitus.servico_cliente;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Retorno extends AttributeContainer implements Serializable,KvmSerializable {
	private String mensagem;
	private String severidadeMensagem;
	public java.lang.Object valor;
	public List< Object> lista = new ArrayList<>();
	private boolean ok;
	private transient java.lang.Object __source;
	
	public Retorno(){}

	public Retorno(boolean ok){
		this.ok = ok;
	}

	public Retorno(boolean ok, String mensagem, String severidadeMensagem) {
		this.mensagem = mensagem;
		this.severidadeMensagem = severidadeMensagem;
		this.ok = ok;
	}

    public Retorno(boolean ok, String mensagem, String severidadeMensagem, Object valor, List lista) {
        this.mensagem = mensagem;
        this.severidadeMensagem = severidadeMensagem;
        this.valor = valor;
        this.lista = lista;
        this.ok = ok;
    }

    public Retorno(boolean ok, String mensagem) {
		this.mensagem = mensagem;
		this.ok = ok;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	public List<Object> getLista() {
		return lista;
	}

    public <T> List<T> getLista(T type) {
	    List<T> list = new ArrayList<>(getLista().size());
	    for(Object o : getLista()){
	        list.add((T) o);
        }
        return list;
    }

	public void setLista(List<Object> lista) {
		this.lista = lista;
	}

	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getSeveridadeMensagem() {
		return severidadeMensagem;
	}
	
	public void setSeveridadeMensagem(String severidadeMensagem) {
		this.severidadeMensagem = severidadeMensagem;
	}
	
	public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	@Override
	public Object getProperty(int propertyIndex) {
		if(propertyIndex>=0 && propertyIndex < 0+this.lista.size())
		{
			java.lang.Object lista = this.lista.get(propertyIndex-(0));
			return lista!=null?lista:SoapPrimitive.NullNilElement;
		}
		if(propertyIndex==0+this.lista.size())
		{
			return this.mensagem!=null?this.mensagem:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==1+this.lista.size())
		{
			return this.severidadeMensagem!=null?this.severidadeMensagem:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==2+this.lista.size())
		{
			return this.valor !=null?this.valor :SoapPrimitive.NullSkip;
		}
		if(propertyIndex==3+this.lista.size())
		{
			return ok;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 4+lista.size();
	}

	@Override
	public void setProperty(int i, Object o) {

	}

	@Override
	public void getPropertyInfo(int propertyIndex, Hashtable hashtable, PropertyInfo info) {
		if(propertyIndex>=0 && propertyIndex < 0+this.lista.size())
		{
			info.type = ExtendedSoapSerializationEnvelope.getXSDType(lista);
			info.name = "lista";
			info.namespace= "";
		}
		if(propertyIndex==0+this.lista.size())
		{
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "mensagem";
			info.namespace= "";
		}
		if(propertyIndex==1+this.lista.size())
		{
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "severidadeMensagem";
			info.namespace= "";
		}
		if(propertyIndex==2+this.lista.size())
		{
			info.type = ExtendedSoapSerializationEnvelope.getXSDType(valor);
			info.name = "valor";
			info.namespace= "";
		}
		if(propertyIndex==3+this.lista.size())
		{
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "ok";
			info.namespace= "";
		}
	}

	public void loadFromSoap(java.lang.Object paramObj,ExtendedSoapSerializationEnvelope __envelope) {
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
		if (info.name.equals("lista"))
		{
			if(obj!=null)
			{
				if(this.lista==null)
				{
					this.lista = new java.util.ArrayList<Object>();
				}
				java.lang.Object j =obj;
				java.lang.Object j1= __envelope.getSpecificType(j);
				this.lista.add(j1);
			}
			return true;
		}
		if (info.name.equals("mensagem"))
		{
			if(obj!=null)
			{
				if (obj.getClass().equals(SoapPrimitive.class))
				{
					SoapPrimitive j =(SoapPrimitive) obj;
					if(j.toString()!=null)
					{
						this.mensagem = j.toString();
					}
				}
				else if (obj instanceof String){
					this.mensagem = (String)obj;
				}
			}
			return true;
		}
		if (info.name.equals("severidadeMensagem"))
		{
			if(obj!=null)
			{
				if (obj.getClass().equals(SoapPrimitive.class))
				{
					SoapPrimitive j =(SoapPrimitive) obj;
					if(j.toString()!=null)
					{
						this.severidadeMensagem = j.toString();
					}
				}
				else if (obj instanceof String){
					this.severidadeMensagem = (String)obj;
				}
			}
			return true;
		}
		if (info.name.equals("valor"))
		{
			if(obj!=null)
			{
				if (obj.getClass().equals(SoapPrimitive.class))
				{
					SoapPrimitive j =(SoapPrimitive) obj;
					if(j.toString()!=null)
					{
						this.valor = __envelope.getSpecificType(j);
					}
				}
				else {
					this.valor = __envelope.getSpecificType(obj);
				}
			}
			return true;
		}
		if (info.name.equals("ok"))
		{
			if(obj!=null)
			{
				if (obj.getClass().equals(SoapPrimitive.class))
				{
					SoapPrimitive j =(SoapPrimitive) obj;
					if(j.toString()!=null)
					{
						this.ok = new Boolean(j.toString());
					}
				}
				else if (obj instanceof Boolean){
					this.ok = (Boolean)obj;
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
