import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


	@Test
	public void testOneLine() {
		
		LayoutDIRF layout = new LayoutDIRF();
		
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
		
		infos.add(new ValoresMensais(RegistroDirf.RTRT));
		infos.add(new ValoresMensais(RegistroDirf.RTPO));
		infos.add(new INFPC());
		infos.add(new INFPA());
		bpfdec.informacoesBPFDEC = infos;
		
		layout.dirf = dirf;
		layout.respo = respo;
		layout.decpj = decpj;
		layout.idrec = idrec;
		layout.bpfdec = Arrays.asList(bpfdec);
		
		
		
		//bpfdec.informacoesBPFDEC = 
		
		DataFlow dataFlow = new DataFlow(layout);
		
		System.out.println(dataFlow.writeToStringBuilder().toString());
		
		assertTrue(true);
		
//		String[] linesProcessed = dataFlow.writeToStringBuilder().toString().split(System.lineSeparator());
//		String[] linesExpected = {"1|Joao da Silva|171235"}; 
//		
//		
//		for(int i=0; i < linesProcessed.length ; i++){
//			assertEquals(linesExpected[i], linesProcessed[i]);
//			//System.out.println(linesExpected[i]);
//			System.out.println(linesProcessed[i]);
//		}
	
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
	
}
