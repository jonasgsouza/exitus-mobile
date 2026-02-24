package br.ueg.mobile.exitus.modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.Serializable;
import java.util.Hashtable;

import br.ueg.mobile.exitus.servico_cliente.ExtendedSoapSerializationEnvelope;
import br.ueg.mobile.exitus.visao.modelo.AlunoVisao;

@DatabaseTable(tableName = "oferta_disciplina_aluno")
public class OfertaDisciplinaAluno extends Entidade implements KvmSerializable, Serializable {

	@DatabaseField(canBeNull = false, id = true)
	private Long id;
	
	@DatabaseField(columnName = "aluno_id", canBeNull = false, foreign = true,  foreignAutoRefresh = true)
	@ForeignCollectionField(orderColumnName = "nome")
	private Aluno aluno;
	
	@DatabaseField(columnName = "oferta_disciplina_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private OfertaDisciplina ofertaDisciplina;
	
	public OfertaDisciplinaAluno(){}
	
	public OfertaDisciplinaAluno(Aluno aluno, OfertaDisciplina ofertaDisciplina) {
		super();
		this.aluno = aluno;
		this.ofertaDisciplina = ofertaDisciplina;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public OfertaDisciplina getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

	public AlunoVisao getAlunoVisao(){
		return  new AlunoVisao(aluno.getNome(), aluno.getId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		if (info.name.equals("aluno"))
		{
			if(obj!=null)
			{
				java.lang.Object j = obj;
				this.aluno = (Aluno)__envelope.get(j,Aluno.class,false);
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
		return super.loadProperty(info,soapObject,__envelope);
	}

	@Override
	public java.lang.Object getProperty(int propertyIndex) {
		int count = super.getPropertyCount();
		//!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
		//!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
		if(propertyIndex==count+0)
		{
			return this.aluno!=null?this.aluno:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==count+1)
		{
			return this.id!=null?this.id:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==count+2)
		{
			return this.ofertaDisciplina!=null?this.ofertaDisciplina:SoapPrimitive.NullSkip;
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
			info.type = Aluno.class;
			info.name = "aluno";
			info.namespace= "";
		}
		if(propertyIndex==count+1)
		{
			info.type = PropertyInfo.LONG_CLASS;
			info.name = "id";
			info.namespace= "";
		}
		if(propertyIndex==count+2)
		{
			info.type = OfertaDisciplina.class;
			info.name = "ofertaDisciplina";
			info.namespace= "";
		}
		super.getPropertyInfo(propertyIndex,arg1,info);
	}

	@Override
	public void setProperty(int arg0, java.lang.Object arg1)
	{
	}
	
}
