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
import java.util.List;

import br.ueg.mobile.exitus.servico_cliente.ExtendedSoapSerializationEnvelope;
import br.ueg.mobile.exitus.visao.modelo.DisciplinaVisao;

@DatabaseTable(tableName ="oferta_disciplina" )
public class OfertaDisciplina extends Entidade implements KvmSerializable, Serializable {

	@DatabaseField(canBeNull = false, id = true)
	private Long id;


	@DatabaseField(columnName = "disciplina_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Disciplina disciplina;
	
	
	@DatabaseField(columnName = "oferta_curso_id", canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3)
	private OfertaCurso ofertaCurso;

	@DatabaseField(columnName = "aulas_previstas")
	private Integer aulasPrevistas;

	@DatabaseField(columnName = "aulas_lancadas")
	private Integer aulasLancadas;
	
	@DatabaseField(persisted = false)
	private List<OfertaDisciplinaAluno> ofertaDisciplinaAlunos;
	
	public OfertaDisciplina(){}
	
	public OfertaDisciplina(Disciplina disciplina, OfertaCurso ofertaCurso) {
		super();
		this.disciplina = disciplina;
		this.ofertaCurso = ofertaCurso;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public OfertaCurso getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(OfertaCurso ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}

	public List<OfertaDisciplinaAluno> getOfertaDisciplinaAlunos() {
		return ofertaDisciplinaAlunos;
	}

	public void setOfertaDisciplinaAlunos(List<OfertaDisciplinaAluno> ofertaDisciplinaAlunos) {
		this.ofertaDisciplinaAlunos = ofertaDisciplinaAlunos;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Integer getAulasPrevistas() {
        return aulasPrevistas;
    }

    public void setAulasPrevistas(Integer aulasPrevistas) {
        this.aulasPrevistas = aulasPrevistas;
    }

    public Integer getAulasLancadas() {
        return aulasLancadas;
    }

    public void setAulasLancadas(Integer aulasLancadas) {
        this.aulasLancadas = aulasLancadas;
    }

	public DisciplinaVisao getDisciplinaVisao(){
		return new DisciplinaVisao(id, disciplina.getNome(), ofertaCurso.getCursoVisao(), this, aulasPrevistas, aulasLancadas);
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
		if (info.name.equals("disciplina"))
		{
			if(obj!=null)
			{
				java.lang.Object j = obj;
				this.disciplina = (Disciplina)__envelope.get(j,Disciplina.class,false);
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
		if (info.name.equals("ofertaCurso"))
		{
			if(obj!=null)
			{
				java.lang.Object j = obj;
				this.ofertaCurso = (OfertaCurso)__envelope.get(j,OfertaCurso.class,false);
			}
			return true;
		}
        if (info.name.equals("aulasPrevistas"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.aulasPrevistas = Integer.parseInt(j.toString());
                    }
                }
                else if (obj instanceof Integer){
                    this.aulasPrevistas = (Integer)obj;
                }
            }
            return true;
        }
        if (info.name.equals("aulasLancadas"))
        {
            if(obj!=null)
            {
                if (obj.getClass().equals(SoapPrimitive.class))
                {
                    SoapPrimitive j =(SoapPrimitive) obj;
                    if(j.toString()!=null)
                    {
                        this.aulasLancadas = Integer.parseInt(j.toString());
                    }
                }
                else if (obj instanceof Integer){
                    this.aulasLancadas = (Integer)obj;
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
            return this.disciplina!=null?this.disciplina:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+1)
        {
            return this.id!=null?this.id:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+2)
        {
            return this.ofertaCurso!=null?this.ofertaCurso:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+3)
        {
            return this.aulasPrevistas!=null?this.aulasPrevistas:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+4)
        {
            return this.aulasLancadas!=null?this.aulasLancadas:SoapPrimitive.NullSkip;
        }
        return super.getProperty(propertyIndex);
    }

    @Override
    public int getPropertyCount() {
        return super.getPropertyCount()+5;
    }

    @Override
    public void getPropertyInfo(int propertyIndex, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        int count = super.getPropertyCount();
        if(propertyIndex==count+0)
        {
            info.type = Disciplina.class;
            info.name = "disciplina";
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
            info.type = OfertaCurso.class;
            info.name = "ofertaCurso";
            info.namespace= "";
        }
        if(propertyIndex==count+3)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "aulasPrevistas";
            info.namespace= "";
        }
        if(propertyIndex==count+4)
        {
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "aulasLancadas";
            info.namespace= "";
        }
        super.getPropertyInfo(propertyIndex,arg1,info);
    }

    @Override
    public void setProperty(int arg0, java.lang.Object arg1)
    {
    }
}
