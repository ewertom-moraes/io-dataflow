import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.tomcode.iodataflow.api.DataFlow;
import layouts.dirf.BPFDEC;
import layouts.dirf.DECPJ;
import layouts.dirf.Dirf;
import layouts.dirf.IDREC;
import layouts.dirf.INFPA;
import layouts.dirf.INFPC;
import layouts.dirf.InformacoesBPFDEC;
import layouts.dirf.LayoutDIRF;
import layouts.dirf.RESPO;
import layouts.dirf.RegistroDirf;
import layouts.dirf.ValoresMensais;

public class TestDirf {


	LayoutDIRF layout;
	
	@Before
	public void init(){
		
		this.layout = new LayoutDIRF();
		
		Dirf dirf = new Dirf();
		dirf.anoCalendario = "2017";
		dirf.anoReferencia = "2016";
		dirf.indenficadorRetifiadora = "N";
		dirf.itenficadorLeiute = "P49VS72";
		
		RESPO respo = new RESPO();
		respo.cpf= "01568544227";
		respo.nome = "Ewertom Mozart moraes de miranda";
		respo.ddd= "91";
		respo.telefone = "983263526";
		respo.correioEletronico = "ewertom.moraes@gmail.com";
			
		DECPJ decpj = new DECPJ();
		decpj.cnpj = "1234567891245";
		decpj.nomeEmpresarial = "MINISTERIO PUBLIC DO PARA";
		decpj.naturezaDeclarante = "3";
		
		
		IDREC idrec = new IDREC();
		idrec.codigoReceita = "0561";
		
		BPFDEC bpfdec = new BPFDEC();
		bpfdec.cpf = "12345678911";
		bpfdec.nome = "FULANO DE TAL";
		
		List<InformacoesBPFDEC> infos = new ArrayList<InformacoesBPFDEC>();
		
		ValoresMensais rtrt = new ValoresMensais(RegistroDirf.RTRT);
		rtrt.valorM01 = "123";
		rtrt.valorM02 = "123";
		rtrt.valorM03 = "123";
		rtrt.valorM04 = "123";
		rtrt.valorM05 = "123";
		rtrt.valorM06 = "123";
		rtrt.valorM07 = "123";
		rtrt.valorM08 = "123";
		rtrt.valorM09 = "123";
		rtrt.valorM10 = "123";
		rtrt.valorM11 = "123";
		rtrt.valorM12 = "123";
		rtrt.valorM13 = "123";
		
		infos.add(rtrt);
		infos.add(new ValoresMensais(RegistroDirf.RTPO));
		infos.add(new INFPC());
		infos.add(new INFPA());
		
		layout.dirf = dirf;
		layout.respo = respo;
		layout.decpj = decpj;
		layout.idrec = idrec;
		
		bpfdec.informacoesBPFDEC = infos;
		layout.bpfdec = Arrays.asList(bpfdec);
		
		
	}
	
	
	@Test
	public void testaOpcaoCaractereFinalLinha(){
		
		LayoutDIRF layout = new LayoutDIRF();
		
		Dirf dirf = new Dirf();
		dirf.anoCalendario = "2017";
		dirf.anoReferencia = "2016";
		dirf.indenficadorRetifiadora = "N";
		dirf.itenficadorLeiute = "P49VS72";
	
		layout.dirf = dirf;
		
		DataFlow dt = new DataFlow(layout);
		
		assertEquals("DIRF|2016|2017|N||P49VS72|", dt.writeToStringBuilder().toString().split(System.lineSeparator())[0]);
		
	}
	
	
	@Test
	public void testaQueNaoDeveColocarDelimitadorAMais(){
		
		RIO rio = new RIO();
		rio.valor = "1234512";
		InformacoesBPFDEC rio2 = rio;
		layout.bpfdec.get(0).informacoesBPFDEC.add(rio2);
		
		DataFlow dataFlow = new DataFlow(layout);
		
		String out = dataFlow.writeToStringBuilder().toString();
		
//		System.out.println(out);
		
		assertTrue(!out.contains("RIO|1234512|Outros||")); 
		
		
	}


	@Test
	public void testaQueNaoDeveTerQuebraDeLinhaNoMeioDoArquivo(){
		DataFlow dataFlow = new DataFlow(layout);
		
		String out = dataFlow.writeToStringBuilder().toString();
		System.out.println(out);
		boolean contains = out.contains(System.lineSeparator()+System.lineSeparator());
		assertTrue(!contains);
		
	}
}
