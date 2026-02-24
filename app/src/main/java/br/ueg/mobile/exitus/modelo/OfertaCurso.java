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
import br.ueg.mobile.exitus.visao.modelo.CursoVisao;

@DatabaseTable(tableName = "oferta_curso")
public class OfertaCurso extends Entidade implements Serializable,KvmSerializable {
	
	public OfertaCurso(){}
	
	public OfertaCurso(Curso curso, Matriz matriz, AnoSemestre anoSemestre, Campus campus) {
		super();
		this.curso = curso;
		this.matriz = matriz;
		this.anoSemestre = anoSemestre;
		this.campus = campus;
	}

	private transient java.lang.Object __source;

	@DatabaseField(canBeNull = false, id = true)
	private Long id;

	
	@DatabaseField(columnName = "curso_id", canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel=1)
	private Curso curso;
	
	
	@DatabaseField(columnName = "matriz_id", canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel=1)
	private Matriz matriz;
	
	
	@DatabaseField(columnName = "ano_semestre_id", canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel=1)
	private AnoSemestre anoSemestre;
	
	
	@DatabaseField(columnName = "campus_id", canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel=1)
	private Campus campus;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Matriz getMatriz() {
		return matriz;
	}

	public void setMatriz(Matriz matriz) {
		this.matriz = matriz;
	}

	public AnoSemestre getAnoSemestre() {
		return anoSemestre;
	}

	public void setAnoSemestre(AnoSemestre anoSemestre) {
		this.anoSemestre = anoSemestre;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	
	public String getNomeCursoMatriz(){
		return curso.getNome() + " - " + matriz.getDescricao();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CursoVisao getCursoVisao(){
		return new CursoVisao(curso.getNome() + " - " + matriz.getDescricao());
	}

	@Override
	public java.lang.Object getProperty(int propertyIndex) {
		int count = super.getPropertyCount();
		//!!!!! If you have a compilation error here then you are using old version of ksoap2 library. Please upgrade to the latest version.
		//!!!!! You can find a correct version in Lib folder from generated zip file!!!!!
		if(propertyIndex==count+0)
		{
			return this.anoSemestre!=null?this.anoSemestre:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==count+1)
		{
			return this.campus!=null?this.campus:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==count+2)
		{
			return this.curso!=null?this.curso:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==count+3)
		{
			return this.id!=null?this.id:SoapPrimitive.NullSkip;
		}
		if(propertyIndex==count+4)
		{
			return this.matriz!=null?this.matriz:SoapPrimitive.NullSkip;
		}
		return super.getProperty(propertyIndex);
	}

	@Override
	public int getPropertyCount() {
		return super.getPropertyCount()+5;
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
			info.type = AnoSemestre.class;
			info.name = "anoSemestre";
			info.namespace= "";
		}
		if(propertyIndex==count+1)
		{
			info.type = Campus.class;
			info.name = "campus";
			info.namespace= "";
		}
		if(propertyIndex==count+2)
		{
			info.type = Curso.class;
			info.name = "curso";
			info.namespace= "";
		}
		if(propertyIndex==count+3)
		{
			info.type = PropertyInfo.LONG_CLASS;
			info.name = "id";
			info.namespace= "";
		}
		if(propertyIndex==count+4)
		{
			info.type = Matriz.class;
			info.name = "matriz";
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
	protected boolean loadProperty(PropertyInfo info,SoapObject soapObject,ExtendedSoapSerializationEnvelope __envelope)
	{
		java.lang.Object obj = info.getValue();
		if (info.name.equals("anoSemestre"))
		{
			if(obj!=null)
			{
				java.lang.Object j = obj;
				this.anoSemestre = (AnoSemestre)__envelope.get(j,AnoSemestre.class,false);
			}
			return true;
		}
		if (info.name.equals("campus"))
		{
			if(obj!=null)
			{
				java.lang.Object j = obj;
				this.campus = (Campus)__envelope.get(j,Campus.class,false);
			}
			return true;
		}
		if (info.name.equals("curso"))
		{
			if(obj!=null)
			{
				java.lang.Object j = obj;
				this.curso = (Curso)__envelope.get(j,Curso.class,false);
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
		if (info.name.equals("matriz"))
		{
			if(obj!=null)
			{
				java.lang.Object j = obj;
				this.matriz = (Matriz)__envelope.get(j,Matriz.class,false);
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
