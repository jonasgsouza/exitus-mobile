package br.ueg.mobile.exitus.modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.Hashtable;

import br.ueg.mobile.exitus.servico_cliente.ExtendedSoapSerializationEnvelope;


@DatabaseTable(tableName = "oferta_disciplina_professor")
public class OfertaDisciplinaProfessor extends Entidade {

	@DatabaseField(canBeNull = false, id = true)
	private Long id;
	
	@DatabaseField(columnName = "professor_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Professor professor;
	
	@DatabaseField(columnName = "oferta_disciplina_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private OfertaDisciplina ofertaDisciplina;
	
	public OfertaDisciplinaProfessor(){}

	public OfertaDisciplinaProfessor(Professor professor, OfertaDisciplina ofertaDisciplina) {
		super();
		this.professor = professor;
		this.ofertaDisciplina = ofertaDisciplina;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public OfertaDisciplina getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
        if (info.name.equals("ofertaDisciplina"))
        {
            if(obj!=null)
            {
                java.lang.Object j = obj;
                this.ofertaDisciplina = (OfertaDisciplina)__envelope.get(j,OfertaDisciplina.class,false);
            }
            return true;
        }
        if (info.name.equals("professor"))
        {
            if(obj!=null)
            {
                java.lang.Object j = obj;
                this.professor = (Professor)__envelope.get(j,Professor.class,false);
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
            return this.id!=null?this.id:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+1)
        {
            return this.ofertaDisciplina!=null?this.ofertaDisciplina:SoapPrimitive.NullSkip;
        }
        if(propertyIndex==count+2)
        {
            return this.professor!=null?this.professor:SoapPrimitive.NullSkip;
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
            info.type = OfertaDisciplina.class;
            info.name = "ofertaDisciplina";
            info.namespace= "";
        }
        if(propertyIndex==count+2)
        {
            info.type = Professor.class;
            info.name = "professor";
            info.namespace= "";
        }
        super.getPropertyInfo(propertyIndex,arg1,info);
    }

    @Override
    public void setProperty(int arg0, java.lang.Object arg1)
    {
    }
}
