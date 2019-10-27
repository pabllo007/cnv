package br.com.sicoob.cnv.gestao.testes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.com.sicoob.cnv.gestao.testes.ejb.PeriodicidadeAlertaEJBTest;
import br.com.sicoob.cnv.gestao.testes.ejb.SituacaoContratoEJBTest;
import br.com.sicoob.cnv.gestao.testes.factory.ConvenioDB2FactoryDAOTest;
import br.com.sicoob.cnv.gestao.testes.mensagem.EnumMsgProcValidarTest;
import br.com.sicoob.cnv.gestao.testes.util.UtilTest;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({ ConvenioDB2FactoryDAOTest.class, EnumMsgProcValidarTest.class, UtilTest.class, PeriodicidadeAlertaEJBTest.class,
	SituacaoContratoEJBTest.class, })
public class Suite {

}
